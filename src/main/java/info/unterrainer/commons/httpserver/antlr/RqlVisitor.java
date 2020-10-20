// Generated from c:\code\java-http-server\antlr4\Rql.g4 by ANTLR 4.8
package info.unterrainer.commons.httpserver.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RqlVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RqlParser#eval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEval(RqlParser.EvalContext ctx);
	/**
	 * Visit a parse tree produced by {@link RqlParser#orExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(RqlParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RqlParser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(RqlParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RqlParser#atomExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpression(RqlParser.AtomExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RqlParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(RqlParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link RqlParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(RqlParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link RqlParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(RqlParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link RqlParser#jpqlIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJpqlIdentifier(RqlParser.JpqlIdentifierContext ctx);
}