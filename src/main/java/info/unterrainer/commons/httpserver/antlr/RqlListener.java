// Generated from c:\code\java-http-server\antlr4\Rql.g4 by ANTLR 4.8
package info.unterrainer.commons.httpserver.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RqlParser}.
 */
public interface RqlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RqlParser#eval}.
	 * @param ctx the parse tree
	 */
	void enterEval(RqlParser.EvalContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#eval}.
	 * @param ctx the parse tree
	 */
	void exitEval(RqlParser.EvalContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#orExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(RqlParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#orExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(RqlParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(RqlParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(RqlParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpression(RqlParser.AtomExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpression(RqlParser.AtomExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(RqlParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(RqlParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(RqlParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(RqlParser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(RqlParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(RqlParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#jpqlIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterJpqlIdentifier(RqlParser.JpqlIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#jpqlIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitJpqlIdentifier(RqlParser.JpqlIdentifierContext ctx);
}