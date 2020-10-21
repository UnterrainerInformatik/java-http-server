package info.unterrainer.commons.httpserver.rql;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import info.unterrainer.commons.httpserver.HandlerUtils;
import info.unterrainer.commons.httpserver.antlr.RqlLexer;
import info.unterrainer.commons.httpserver.antlr.RqlParser;
import info.unterrainer.commons.httpserver.antlr.RqlParser.EvalContext;
import io.javalin.http.Context;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RqlUtils {

	private final Context ctx;
	private final HandlerUtils hu;

	public RqlData parseRql(final String expression) {
		CharStream in = CharStreams.fromString(expression);
		RqlLexer lexer = new RqlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RqlParser parser = new RqlParser(tokens);
		EvalContext tree = parser.eval(); // parsing happens here
		// show tree in text form
		// System.out.println(tree.toStringTree(parser));

		RqlData data = new RqlData();
		RqlVisitor Visitor = new RqlVisitor(data, hu, ctx);
		Visitor.visit(tree);
		repair(data);
		return data;
	}

	private void repair(final RqlData data) {
		int oldSize = 0;
		int currentSize = data.getParsedCommand().size();
		while (currentSize != oldSize) {
			repairOperators(data);
			repairParentheses(data);

			oldSize = currentSize;
			currentSize = data.getParsedCommand().size();
		}
	}

	private void repairOperators(final RqlData data) {
		List<RqlDataElement> delList = new ArrayList<>();
		RqlDataElement last = null;
		for (RqlDataElement e : data.getParsedCommand()) {
			if (last == null) {
				if (isOperator(e)) {
					// Starts with operator.
					delList.add(e);
					continue;
				}
				last = e;
				continue;
			}
			if (isOperator(last) && isOperator(e))
				// Operator after operator.
				delList.add(last);
			if (last.getType() == RqlDataType.PARENTHESIS_OPEN && isOperator(e)) {
				// Operator after parenthesis-open.
				delList.add(e);
				continue;
			}
			if (isOperator(last) && e.getType() == RqlDataType.PARENTHESIS_CLOSE)
				// Operator before parenthesis-close.
				delList.add(last);
			if (isOperator(e) && e.equals(data.getParsedCommand().get(data.getParsedCommand().size() - 1)))
				// Ends with operator.
				delList.add(e);

			last = e;
		}
		data.getParsedCommand().removeAll(delList);
	}

	private void repairParentheses(final RqlData data) {
		List<RqlDataElement> delList = new ArrayList<>();
		RqlDataElement last = null;
		for (RqlDataElement e : data.getParsedCommand()) {
			if (last == null) {
				last = e;
				continue;
			}
			if (last.getType() == RqlDataType.PARENTHESIS_OPEN && e.getType() == RqlDataType.PARENTHESIS_CLOSE) {
				// Open followed by close.
				delList.add(e);
				delList.add(last);
				last = null;
				continue;
			}
			last = e;
		}
		data.getParsedCommand().removeAll(delList);
	}

	private boolean isOperator(final RqlDataElement e) {
		return e.getType() == RqlDataType.AND || e.getType() == RqlDataType.OR;
	}
}
