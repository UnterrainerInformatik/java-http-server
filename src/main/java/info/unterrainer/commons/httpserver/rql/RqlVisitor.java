package info.unterrainer.commons.httpserver.rql;

import org.antlr.v4.runtime.ParserRuleContext;

import info.unterrainer.commons.httpserver.HandlerUtils;
import info.unterrainer.commons.httpserver.antlr.RqlBaseVisitor;
import info.unterrainer.commons.httpserver.antlr.RqlParser.AndContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.OptTermContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.OrContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.ParCloseContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.ParOpenContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.TermContext;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import info.unterrainer.commons.httpserver.exceptions.InternalServerErrorException;
import io.javalin.http.Context;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RqlVisitor extends RqlBaseVisitor<String> {

	private final RqlData data;
	private final HandlerUtils hu;
	private final Context ctx;
	private final String enumFqn;

	@Override
	public String visitAnd(final AndContext ctx) {
		data.getParsedCommand()
				.add(RqlDataElement.builder()
						.index(data.getParsedCommand().size())
						.type(RqlDataType.AND)
						.value(" " + ctx.getText() + " ")
						.build());
		return super.visitAnd(ctx);
	}

	@Override
	public String visitOr(final OrContext ctx) {
		data.getParsedCommand()
				.add(RqlDataElement.builder()
						.index(data.getParsedCommand().size())
						.type(RqlDataType.OR)
						.value(" " + ctx.getText() + " ")
						.build());
		return super.visitOr(ctx);
	}

	@Override
	public String visitParOpen(final ParOpenContext ctx) {
		data.getParsedCommand()
				.add(RqlDataElement.builder()
						.index(data.getParsedCommand().size())
						.type(RqlDataType.PARENTHESIS_OPEN)
						.value(ctx.getText())
						.build());
		return super.visitParOpen(ctx);
	}

	@Override
	public String visitParClose(final ParCloseContext ctx) {
		data.getParsedCommand()
				.add(RqlDataElement.builder()
						.index(data.getParsedCommand().size())
						.type(RqlDataType.PARENTHESIS_CLOSE)
						.value(ctx.getText())
						.build());
		return super.visitParClose(ctx);
	}

	@Override
	public String visitTerm(final TermContext ctx) {
		calculateTerm(ctx, ctx.children.get(0).getText(), false);
		return super.visitTerm(ctx);
	}

	@Override
	public String visitOptTerm(final OptTermContext ctx) {
		calculateTerm(ctx, ctx.children.get(0).getText().substring(1), true);
		return super.visitOptTerm(ctx);
	}

	private void calculateTerm(final ParserRuleContext ctx, final String first, final boolean isOptional) {
		String operator = ctx.children.get(1).getText().toUpperCase();
		String second = ctx.children.get(2).getText();

		String type = null;
		boolean persist = true;
		if (operator.equalsIgnoreCase("IS") || operator.equalsIgnoreCase("ISNOT"))
			second = "NULL";
		else {
			type = second.substring(second.indexOf("[") + 1, second.indexOf("]"));
			if (second.indexOf("[") >= 0)
				second = second.substring(0, second.indexOf("["));
			persist = assignTermValues(second, isOptional, type, operator);
		}
		if (persist)
			data.getParsedCommand()
					.add(RqlDataElement.builder()
							.index(data.getParsedCommand().size())
							.type(RqlDataType.TERM)
							.value(first + " " + fixOperator(operator) + " " + second)
							.build());
	}

	private String fixOperator(final String op) {
		switch (op) {
		case "!=":
			return "<>";
		case "==":
			return "=";
		case "ISNOT":
			return "IS NOT";
		case "NOTLIKE":
			return "NOT LIKE";
		case "STARTSWITH":
			return "LIKE";
		case "NOTSTARTSWITH":
			return "NOT LIKE";
		case "ENDSWITH":
			return "LIKE";
		case "NOTENDSWITH":
			return "NOT LIKE";
		default:
			return op;
		}
	}

	private boolean assignTermValues(final String paramName, final boolean isOptional, final String type,
			final String oldOperator) {
		String name = paramName.substring(1);
		String value;
		if (isOptional)
			value = hu.getQueryParamAsString(ctx, name, null);
		else
			value = hu.getQueryParamAsString(ctx, name);

		if (value != null) {
			switch (oldOperator.toUpperCase()) {
			case "LIKE":
			case "NOTLIKE":
				value = "%" + value + "%";
				break;
			case "STARTSWITH":
			case "NOTSTARTSWITH":
				value = value + "%";
				break;
			case "ENDSWITH":
			case "NOTENDSWITH":
				value = "%" + value;
				break;
			}
			data.getParams().put(name, castToType(value, type, paramName));
			data.getQueryString().put(name, value);
		} else
			return false;
		return true;
	}

	private Object castToType(final String value, final String type, final String field) {
		if (type.startsWith("~"))
			return castToEnum(value, type.substring(1), field);
		return castToPrimitive(value, type.toLowerCase(), field);
	}

	private Object castToPrimitive(final String value, final String type, final String field) {
		try {
			switch (type) {
			case "boolean":
			case "bool":
				return hu.convertToBoolean(value);
			case "integer":
			case "int":
				return hu.convertToInt(value);
			case "long":
			case "lng":
				return hu.convertToLong(value);
			case "float":
				return hu.convertToFloat(value);
			case "double":
			case "dbl":
				return hu.convertToDouble(value);
			case "datetime":
				return hu.convertToLocalDateTime(value);
			default:
				return value;
			}
		} catch (Exception e) {
			throw new BadRequestException(
					String.format("RQL-Parser: Value [%s] of field [%s] has to be of type [%s] according to your RQL",
							value, field, type));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Enum castToEnum(final String value, final String type, final String field) {
		try {
			final Class<Enum> cl = (Class<Enum>) Class.forName(enumFqn + "." + type);
			return Enum.valueOf(cl, value);
		} catch (ClassCastException e) {
			throw new InternalServerErrorException();
		} catch (ClassNotFoundException e) {
			throw new InternalServerErrorException(
					String.format("The Enum type [%s] you want to cast to is not available", type));
		} catch (IllegalArgumentException e) {
			throw new BadRequestException(
					String.format("Value [%s] of field [%s] has to be of type [%s]", value, field, type));
		}
	}
}
