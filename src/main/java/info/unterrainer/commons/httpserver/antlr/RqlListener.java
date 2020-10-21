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
	 * Enter a parse tree produced by {@link RqlParser#atomTerm}.
	 * @param ctx the parse tree
	 */
	void enterAtomTerm(RqlParser.AtomTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#atomTerm}.
	 * @param ctx the parse tree
	 */
	void exitAtomTerm(RqlParser.AtomTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#and}.
	 * @param ctx the parse tree
	 */
	void enterAnd(RqlParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#and}.
	 * @param ctx the parse tree
	 */
	void exitAnd(RqlParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#or}.
	 * @param ctx the parse tree
	 */
	void enterOr(RqlParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#or}.
	 * @param ctx the parse tree
	 */
	void exitOr(RqlParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#parOpen}.
	 * @param ctx the parse tree
	 */
	void enterParOpen(RqlParser.ParOpenContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#parOpen}.
	 * @param ctx the parse tree
	 */
	void exitParOpen(RqlParser.ParOpenContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#parClose}.
	 * @param ctx the parse tree
	 */
	void enterParClose(RqlParser.ParCloseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#parClose}.
	 * @param ctx the parse tree
	 */
	void exitParClose(RqlParser.ParCloseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RqlParser#optTerm}.
	 * @param ctx the parse tree
	 */
	void enterOptTerm(RqlParser.OptTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link RqlParser#optTerm}.
	 * @param ctx the parse tree
	 */
	void exitOptTerm(RqlParser.OptTermContext ctx);
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
}