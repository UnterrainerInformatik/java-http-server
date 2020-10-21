package info.unterrainer.commons.httpserver.rql;

import info.unterrainer.commons.httpserver.HandlerUtils;
import info.unterrainer.commons.httpserver.antlr.RqlBaseVisitor;
import info.unterrainer.commons.httpserver.antlr.RqlParser.AndContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.OptTermContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.OrContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.ParCloseContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.ParOpenContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.TermContext;
import io.javalin.http.Context;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RqlVisitor extends RqlBaseVisitor<String> {

	private final RqlData data;
	private final HandlerUtils hu;
	private final Context ctx;

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
		String first = ctx.children.get(0).getText();
		String operator = ctx.children.get(1).getText();
		String second = ctx.children.get(2).getText();
		if (assignTermValues(second, false))
			data.getParsedCommand()
					.add(RqlDataElement.builder()
							.index(data.getParsedCommand().size())
							.type(RqlDataType.TERM)
							.value(first + " " + operator + " " + second)
							.build());
		return super.visitTerm(ctx);
	}

	@Override
	public String visitOptTerm(final OptTermContext ctx) {
		String first = ctx.children.get(0).getText().substring(1);
		String operator = ctx.children.get(1).getText();
		String second = ctx.children.get(2).getText();
		if (assignTermValues(second, true))
			data.getParsedCommand()
					.add(RqlDataElement.builder()
							.index(data.getParsedCommand().size())
							.type(RqlDataType.TERM)
							.value(first + " " + operator + " " + second)
							.build());
		return super.visitOptTerm(ctx);
	}

	private boolean assignTermValues(final String paramName, final boolean isOptional) {
		String name = paramName.substring(1);
		String value;
		if (isOptional)
			value = hu.getQueryParamAsString(ctx, name, null);
		else
			value = hu.getQueryParamAsString(ctx, name);

		if (value != null) {
			data.getParams().put(name, value);
			data.getQueryString().put(name, value);
		} else
			return false;
		return true;
	}
}