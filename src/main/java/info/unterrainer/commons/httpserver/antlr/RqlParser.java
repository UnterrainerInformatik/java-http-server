// Generated from c:\code\java-http-server\antlr4\Rql.g4 by ANTLR 4.8
package info.unterrainer.commons.httpserver.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RqlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Operator=1, Like=2, NLike=3, Null=4, Is=5, Not=6, And=7, Or=8, ParOpen=9, 
		ParClose=10, Identifier=11, OptIdentifier=12, JpqlIdentifier=13, Whitespace=14;
	public static final int
		RULE_eval = 0, RULE_orExpression = 1, RULE_andExpression = 2, RULE_atomExpression = 3, 
		RULE_atomTerm = 4, RULE_and = 5, RULE_or = 6, RULE_parOpen = 7, RULE_parClose = 8, 
		RULE_optTerm = 9, RULE_optTerm1 = 10, RULE_optOperator = 11, RULE_optTerm2 = 12, 
		RULE_term = 13, RULE_term1 = 14, RULE_nullOperator1 = 15, RULE_nullOperator2 = 16, 
		RULE_nullOperator = 17, RULE_operator = 18, RULE_term2 = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"eval", "orExpression", "andExpression", "atomExpression", "atomTerm", 
			"and", "or", "parOpen", "parClose", "optTerm", "optTerm1", "optOperator", 
			"optTerm2", "term", "term1", "nullOperator1", "nullOperator2", "nullOperator", 
			"operator", "term2"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Operator", "Like", "NLike", "Null", "Is", "Not", "And", "Or", 
			"ParOpen", "ParClose", "Identifier", "OptIdentifier", "JpqlIdentifier", 
			"Whitespace"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Rql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class EvalContext extends ParserRuleContext {
		public OrExpressionContext orExpression() {
			return getRuleContext(OrExpressionContext.class,0);
		}
		public EvalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterEval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitEval(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitEval(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EvalContext eval() throws RecognitionException {
		EvalContext _localctx = new EvalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_eval);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			orExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrExpressionContext extends ParserRuleContext {
		public List<AndExpressionContext> andExpression() {
			return getRuleContexts(AndExpressionContext.class);
		}
		public AndExpressionContext andExpression(int i) {
			return getRuleContext(AndExpressionContext.class,i);
		}
		public List<OrContext> or() {
			return getRuleContexts(OrContext.class);
		}
		public OrContext or(int i) {
			return getRuleContext(OrContext.class,i);
		}
		public OrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrExpressionContext orExpression() throws RecognitionException {
		OrExpressionContext _localctx = new OrExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_orExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			andExpression();
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Or) {
				{
				{
				setState(43);
				or();
				setState(44);
				andExpression();
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndExpressionContext extends ParserRuleContext {
		public List<AtomExpressionContext> atomExpression() {
			return getRuleContexts(AtomExpressionContext.class);
		}
		public AtomExpressionContext atomExpression(int i) {
			return getRuleContext(AtomExpressionContext.class,i);
		}
		public List<AndContext> and() {
			return getRuleContexts(AndContext.class);
		}
		public AndContext and(int i) {
			return getRuleContext(AndContext.class,i);
		}
		public AndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndExpressionContext andExpression() throws RecognitionException {
		AndExpressionContext _localctx = new AndExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_andExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			atomExpression();
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==And) {
				{
				{
				setState(52);
				and();
				setState(53);
				atomExpression();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomExpressionContext extends ParserRuleContext {
		public AtomTermContext atomTerm() {
			return getRuleContext(AtomTermContext.class,0);
		}
		public ParOpenContext parOpen() {
			return getRuleContext(ParOpenContext.class,0);
		}
		public OrExpressionContext orExpression() {
			return getRuleContext(OrExpressionContext.class,0);
		}
		public ParCloseContext parClose() {
			return getRuleContext(ParCloseContext.class,0);
		}
		public AtomExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterAtomExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitAtomExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitAtomExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomExpressionContext atomExpression() throws RecognitionException {
		AtomExpressionContext _localctx = new AtomExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_atomExpression);
		try {
			setState(65);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
			case OptIdentifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				atomTerm();
				}
				break;
			case ParOpen:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(61);
				parOpen();
				setState(62);
				orExpression();
				setState(63);
				parClose();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomTermContext extends ParserRuleContext {
		public OptTermContext optTerm() {
			return getRuleContext(OptTermContext.class,0);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public AtomTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterAtomTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitAtomTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitAtomTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomTermContext atomTerm() throws RecognitionException {
		AtomTermContext _localctx = new AtomTermContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atomTerm);
		try {
			setState(69);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OptIdentifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				optTerm();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				term();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndContext extends ParserRuleContext {
		public TerminalNode And() { return getToken(RqlParser.And, 0); }
		public AndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndContext and() throws RecognitionException {
		AndContext _localctx = new AndContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(And);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrContext extends ParserRuleContext {
		public TerminalNode Or() { return getToken(RqlParser.Or, 0); }
		public OrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrContext or() throws RecognitionException {
		OrContext _localctx = new OrContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(Or);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParOpenContext extends ParserRuleContext {
		public TerminalNode ParOpen() { return getToken(RqlParser.ParOpen, 0); }
		public ParOpenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parOpen; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterParOpen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitParOpen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitParOpen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParOpenContext parOpen() throws RecognitionException {
		ParOpenContext _localctx = new ParOpenContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_parOpen);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(ParOpen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParCloseContext extends ParserRuleContext {
		public TerminalNode ParClose() { return getToken(RqlParser.ParClose, 0); }
		public ParCloseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parClose; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterParClose(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitParClose(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitParClose(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParCloseContext parClose() throws RecognitionException {
		ParCloseContext _localctx = new ParCloseContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_parClose);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(ParClose);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptTermContext extends ParserRuleContext {
		public OptTerm1Context optTerm1() {
			return getRuleContext(OptTerm1Context.class,0);
		}
		public OptOperatorContext optOperator() {
			return getRuleContext(OptOperatorContext.class,0);
		}
		public OptTerm2Context optTerm2() {
			return getRuleContext(OptTerm2Context.class,0);
		}
		public OptTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterOptTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitOptTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitOptTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptTermContext optTerm() throws RecognitionException {
		OptTermContext _localctx = new OptTermContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_optTerm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			optTerm1();
			setState(80);
			optOperator();
			setState(81);
			optTerm2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptTerm1Context extends ParserRuleContext {
		public TerminalNode OptIdentifier() { return getToken(RqlParser.OptIdentifier, 0); }
		public OptTerm1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optTerm1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterOptTerm1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitOptTerm1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitOptTerm1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptTerm1Context optTerm1() throws RecognitionException {
		OptTerm1Context _localctx = new OptTerm1Context(_ctx, getState());
		enterRule(_localctx, 20, RULE_optTerm1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(OptIdentifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptOperatorContext extends ParserRuleContext {
		public TerminalNode Operator() { return getToken(RqlParser.Operator, 0); }
		public OptOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterOptOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitOptOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitOptOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptOperatorContext optOperator() throws RecognitionException {
		OptOperatorContext _localctx = new OptOperatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_optOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(Operator);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptTerm2Context extends ParserRuleContext {
		public TerminalNode JpqlIdentifier() { return getToken(RqlParser.JpqlIdentifier, 0); }
		public OptTerm2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optTerm2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterOptTerm2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitOptTerm2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitOptTerm2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptTerm2Context optTerm2() throws RecognitionException {
		OptTerm2Context _localctx = new OptTerm2Context(_ctx, getState());
		enterRule(_localctx, 24, RULE_optTerm2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(JpqlIdentifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public Term1Context term1() {
			return getRuleContext(Term1Context.class,0);
		}
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public Term2Context term2() {
			return getRuleContext(Term2Context.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_term);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			term1();
			setState(90);
			operator();
			setState(91);
			term2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Term1Context extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(RqlParser.Identifier, 0); }
		public Term1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterTerm1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitTerm1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitTerm1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Term1Context term1() throws RecognitionException {
		Term1Context _localctx = new Term1Context(_ctx, getState());
		enterRule(_localctx, 28, RULE_term1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NullOperator1Context extends ParserRuleContext {
		public TerminalNode Is() { return getToken(RqlParser.Is, 0); }
		public NullOperator1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nullOperator1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterNullOperator1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitNullOperator1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitNullOperator1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NullOperator1Context nullOperator1() throws RecognitionException {
		NullOperator1Context _localctx = new NullOperator1Context(_ctx, getState());
		enterRule(_localctx, 30, RULE_nullOperator1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(Is);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NullOperator2Context extends ParserRuleContext {
		public TerminalNode Is() { return getToken(RqlParser.Is, 0); }
		public TerminalNode Not() { return getToken(RqlParser.Not, 0); }
		public NullOperator2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nullOperator2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterNullOperator2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitNullOperator2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitNullOperator2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NullOperator2Context nullOperator2() throws RecognitionException {
		NullOperator2Context _localctx = new NullOperator2Context(_ctx, getState());
		enterRule(_localctx, 32, RULE_nullOperator2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(Is);
			setState(98);
			match(Not);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NullOperatorContext extends ParserRuleContext {
		public NullOperator1Context nullOperator1() {
			return getRuleContext(NullOperator1Context.class,0);
		}
		public NullOperator2Context nullOperator2() {
			return getRuleContext(NullOperator2Context.class,0);
		}
		public NullOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nullOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterNullOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitNullOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitNullOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NullOperatorContext nullOperator() throws RecognitionException {
		NullOperatorContext _localctx = new NullOperatorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_nullOperator);
		try {
			setState(102);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(100);
				nullOperator1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				nullOperator2();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorContext extends ParserRuleContext {
		public NullOperatorContext nullOperator() {
			return getRuleContext(NullOperatorContext.class,0);
		}
		public TerminalNode Operator() { return getToken(RqlParser.Operator, 0); }
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_operator);
		try {
			setState(106);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Is:
				enterOuterAlt(_localctx, 1);
				{
				setState(104);
				nullOperator();
				}
				break;
			case Operator:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				match(Operator);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Term2Context extends ParserRuleContext {
		public TerminalNode Null() { return getToken(RqlParser.Null, 0); }
		public TerminalNode JpqlIdentifier() { return getToken(RqlParser.JpqlIdentifier, 0); }
		public Term2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterTerm2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitTerm2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitTerm2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Term2Context term2() throws RecognitionException {
		Term2Context _localctx = new Term2Context(_ctx, getState());
		enterRule(_localctx, 38, RULE_term2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			_la = _input.LA(1);
			if ( !(_la==Null || _la==JpqlIdentifier) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\20q\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22\4\23"+
		"\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\3\3\3\3\3\3\3\7\3\61\n\3\f\3\16\3"+
		"\64\13\3\3\4\3\4\3\4\3\4\7\4:\n\4\f\4\16\4=\13\4\3\5\3\5\3\5\3\5\3\5\5"+
		"\5D\n\5\3\6\3\6\5\6H\n\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\22\3\23\3\23\5\23i\n\23\3\24\3\24\5\24m\n\24\3\25\3\25\3"+
		"\25\2\2\26\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(\2\3\4\2\6\6\17"+
		"\17\2b\2*\3\2\2\2\4,\3\2\2\2\6\65\3\2\2\2\bC\3\2\2\2\nG\3\2\2\2\fI\3\2"+
		"\2\2\16K\3\2\2\2\20M\3\2\2\2\22O\3\2\2\2\24Q\3\2\2\2\26U\3\2\2\2\30W\3"+
		"\2\2\2\32Y\3\2\2\2\34[\3\2\2\2\36_\3\2\2\2 a\3\2\2\2\"c\3\2\2\2$h\3\2"+
		"\2\2&l\3\2\2\2(n\3\2\2\2*+\5\4\3\2+\3\3\2\2\2,\62\5\6\4\2-.\5\16\b\2."+
		"/\5\6\4\2/\61\3\2\2\2\60-\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3"+
		"\2\2\2\63\5\3\2\2\2\64\62\3\2\2\2\65;\5\b\5\2\66\67\5\f\7\2\678\5\b\5"+
		"\28:\3\2\2\29\66\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<\7\3\2\2\2=;\3"+
		"\2\2\2>D\5\n\6\2?@\5\20\t\2@A\5\4\3\2AB\5\22\n\2BD\3\2\2\2C>\3\2\2\2C"+
		"?\3\2\2\2D\t\3\2\2\2EH\5\24\13\2FH\5\34\17\2GE\3\2\2\2GF\3\2\2\2H\13\3"+
		"\2\2\2IJ\7\t\2\2J\r\3\2\2\2KL\7\n\2\2L\17\3\2\2\2MN\7\13\2\2N\21\3\2\2"+
		"\2OP\7\f\2\2P\23\3\2\2\2QR\5\26\f\2RS\5\30\r\2ST\5\32\16\2T\25\3\2\2\2"+
		"UV\7\16\2\2V\27\3\2\2\2WX\7\3\2\2X\31\3\2\2\2YZ\7\17\2\2Z\33\3\2\2\2["+
		"\\\5\36\20\2\\]\5&\24\2]^\5(\25\2^\35\3\2\2\2_`\7\r\2\2`\37\3\2\2\2ab"+
		"\7\7\2\2b!\3\2\2\2cd\7\7\2\2de\7\b\2\2e#\3\2\2\2fi\5 \21\2gi\5\"\22\2"+
		"hf\3\2\2\2hg\3\2\2\2i%\3\2\2\2jm\5$\23\2km\7\3\2\2lj\3\2\2\2lk\3\2\2\2"+
		"m\'\3\2\2\2no\t\2\2\2o)\3\2\2\2\b\62;CGhl";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}