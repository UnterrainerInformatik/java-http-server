package info.unterrainer.commons.httpserver;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import info.unterrainer.commons.httpserver.antlr.RqlBaseVisitor;
import info.unterrainer.commons.httpserver.antlr.RqlLexer;
import info.unterrainer.commons.httpserver.antlr.RqlParser;
import info.unterrainer.commons.httpserver.antlr.RqlParser.EvalContext;
import info.unterrainer.commons.httpserver.antlr.RqlParser.TermContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class HttpServerTests {

	@Getter
	@Setter
	@RequiredArgsConstructor
	public class Data {
		private String selectClause;
		private String whereClause;
		private String joinClause;
		private Map<String, Object> params = new HashMap<>();
		private String queryString;
	}

	@RequiredArgsConstructor
	public static class RqlVisitor extends RqlBaseVisitor<String> {

		private final Data data;

		@Override
		public String visitEval(final RqlParser.EvalContext ctx) {
			return super.visitEval(ctx);
		}

		@Override
		public String visitTerm(final TermContext ctx) {
			data.getParams().put(ctx.children.get(0).getText(), ctx.children.get(2).getText());
			return super.visitTerm(ctx);
		}
	}

	@Test
	public void test() {

		// userId=:userId
		// userId=:userId AND (timeFrom<=:timeFrom OR timeTo>:timeTo)

		CharStream in = CharStreams.fromString("userId=:userId AND (timeFrom<=:timeFrom OR timeTo>:timeTo)");
		RqlLexer lexer = new RqlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RqlParser parser = new RqlParser(tokens);
		EvalContext tree = parser.eval(); // parse
		// show tree in text form
		// System.out.println(tree.toStringTree(parser));

		Data data = new Data();
		RqlVisitor Visitor = new RqlVisitor(data);
		Visitor.visit(tree);
		System.out.println("done.");
	}
}
