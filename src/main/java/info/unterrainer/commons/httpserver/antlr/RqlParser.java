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
		Operator=1, Like=2, StartsWith=3, EndsWith=4, Null=5, Is=6, Not=7, And=8, 
		Or=9, ParOpen=10, ParClose=11, Identifier=12, OptIdentifier=13, JpqlIdentifier=14, 
		Whitespace=15;
	public static final int
		RULE_eval = 0, RULE_orExpression = 1, RULE_andExpression = 2, RULE_atomExpression = 3, 
		RULE_atomTerm = 4, RULE_and = 5, RULE_or = 6, RULE_parOpen = 7, RULE_parClose = 8, 
		RULE_optTerm = 9, RULE_optTerm1 = 10, RULE_optOperator = 11, RULE_optTerm2 = 12, 
		RULE_term = 13, RULE_term1 = 14, RULE_nullOperator1 = 15, RULE_nullOperator2 = 16, 
		RULE_nullOperator = 17, RULE_nLike = 18, RULE_nStartsWith = 19, RULE_nEndsWith = 20, 
		RULE_negatableOperator = 21, RULE_operator = 22, RULE_term2 = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"eval", "orExpression", "andExpression", "atomExpression", "atomTerm", 
			"and", "or", "parOpen", "parClose", "optTerm", "optTerm1", "optOperator", 
			"optTerm2", "term", "term1", "nullOperator1", "nullOperator2", "nullOperator", 
			"nLike", "nStartsWith", "nEndsWith", "negatableOperator", "operator", 
			"term2"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Operator", "Like", "StartsWith", "EndsWith", "Null", "Is", "Not", 
			"And", "Or", "ParOpen", "ParClose", "Identifier", "OptIdentifier", "JpqlIdentifier", 
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
			setState(48);
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
			setState(50);
			andExpression();
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Or) {
				{
				{
				setState(51);
				or();
				setState(52);
				andExpression();
				}
				}
				setState(58);
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
			setState(59);
			atomExpression();
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==And) {
				{
				{
				setState(60);
				and();
				setState(61);
				atomExpression();
				}
				}
				setState(67);
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
			setState(73);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
			case OptIdentifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				atomTerm();
				}
				break;
			case ParOpen:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(69);
				parOpen();
				setState(70);
				orExpression();
				setState(71);
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
			setState(77);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OptIdentifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				optTerm();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(76);
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
			setState(79);
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
			setState(81);
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
			setState(83);
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
			setState(85);
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
			setState(87);
			optTerm1();
			setState(88);
			optOperator();
			setState(89);
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
			setState(91);
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
		public NullOperatorContext nullOperator() {
			return getRuleContext(NullOperatorContext.class,0);
		}
		public NegatableOperatorContext negatableOperator() {
			return getRuleContext(NegatableOperatorContext.class,0);
		}
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
			setState(96);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Is:
				enterOuterAlt(_localctx, 1);
				{
				setState(93);
				nullOperator();
				}
				break;
			case Like:
			case StartsWith:
			case EndsWith:
			case Not:
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				negatableOperator();
				}
				break;
			case Operator:
				enterOuterAlt(_localctx, 3);
				{
				setState(95);
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
			setState(98);
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
			setState(100);
			term1();
			setState(101);
			operator();
			setState(102);
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
			setState(104);
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
			setState(106);
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
			setState(108);
			match(Is);
			setState(109);
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
			setState(113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(111);
				nullOperator1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(112);
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

	public static class NLikeContext extends ParserRuleContext {
		public TerminalNode Not() { return getToken(RqlParser.Not, 0); }
		public TerminalNode Like() { return getToken(RqlParser.Like, 0); }
		public NLikeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nLike; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterNLike(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitNLike(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitNLike(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NLikeContext nLike() throws RecognitionException {
		NLikeContext _localctx = new NLikeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_nLike);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(Not);
			setState(116);
			match(Like);
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

	public static class NStartsWithContext extends ParserRuleContext {
		public TerminalNode Not() { return getToken(RqlParser.Not, 0); }
		public TerminalNode StartsWith() { return getToken(RqlParser.StartsWith, 0); }
		public NStartsWithContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nStartsWith; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterNStartsWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitNStartsWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitNStartsWith(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NStartsWithContext nStartsWith() throws RecognitionException {
		NStartsWithContext _localctx = new NStartsWithContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_nStartsWith);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(Not);
			setState(119);
			match(StartsWith);
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

	public static class NEndsWithContext extends ParserRuleContext {
		public TerminalNode Not() { return getToken(RqlParser.Not, 0); }
		public TerminalNode EndsWith() { return getToken(RqlParser.EndsWith, 0); }
		public NEndsWithContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nEndsWith; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterNEndsWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitNEndsWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitNEndsWith(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NEndsWithContext nEndsWith() throws RecognitionException {
		NEndsWithContext _localctx = new NEndsWithContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_nEndsWith);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(Not);
			setState(122);
			match(EndsWith);
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

	public static class NegatableOperatorContext extends ParserRuleContext {
		public TerminalNode Like() { return getToken(RqlParser.Like, 0); }
		public NLikeContext nLike() {
			return getRuleContext(NLikeContext.class,0);
		}
		public TerminalNode StartsWith() { return getToken(RqlParser.StartsWith, 0); }
		public NStartsWithContext nStartsWith() {
			return getRuleContext(NStartsWithContext.class,0);
		}
		public TerminalNode EndsWith() { return getToken(RqlParser.EndsWith, 0); }
		public NEndsWithContext nEndsWith() {
			return getRuleContext(NEndsWithContext.class,0);
		}
		public NegatableOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negatableOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterNegatableOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitNegatableOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitNegatableOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegatableOperatorContext negatableOperator() throws RecognitionException {
		NegatableOperatorContext _localctx = new NegatableOperatorContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_negatableOperator);
		try {
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				match(Like);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				nLike();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				match(StartsWith);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(127);
				nStartsWith();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(128);
				match(EndsWith);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(129);
				nEndsWith();
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
		public NegatableOperatorContext negatableOperator() {
			return getRuleContext(NegatableOperatorContext.class,0);
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
		enterRule(_localctx, 44, RULE_operator);
		try {
			setState(135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Is:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				nullOperator();
				}
				break;
			case Like:
			case StartsWith:
			case EndsWith:
			case Not:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				negatableOperator();
				}
				break;
			case Operator:
				enterOuterAlt(_localctx, 3);
				{
				setState(134);
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
		enterRule(_localctx, 46, RULE_term2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\21\u008e\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\3\3\3\3\3\3\3\7\39\n\3\f\3\16\3<\13\3\3\4\3\4\3\4\3\4\7\4B"+
		"\n\4\f\4\16\4E\13\4\3\5\3\5\3\5\3\5\3\5\5\5L\n\5\3\6\3\6\5\6P\n\6\3\7"+
		"\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\5"+
		"\rc\n\r\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3"+
		"\22\3\23\3\23\5\23t\n\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0085\n\27\3\30\3\30\3\30\5\30\u008a"+
		"\n\30\3\31\3\31\3\31\2\2\32\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \""+
		"$&(*,.\60\2\3\4\2\7\7\20\20\2\u0083\2\62\3\2\2\2\4\64\3\2\2\2\6=\3\2\2"+
		"\2\bK\3\2\2\2\nO\3\2\2\2\fQ\3\2\2\2\16S\3\2\2\2\20U\3\2\2\2\22W\3\2\2"+
		"\2\24Y\3\2\2\2\26]\3\2\2\2\30b\3\2\2\2\32d\3\2\2\2\34f\3\2\2\2\36j\3\2"+
		"\2\2 l\3\2\2\2\"n\3\2\2\2$s\3\2\2\2&u\3\2\2\2(x\3\2\2\2*{\3\2\2\2,\u0084"+
		"\3\2\2\2.\u0089\3\2\2\2\60\u008b\3\2\2\2\62\63\5\4\3\2\63\3\3\2\2\2\64"+
		":\5\6\4\2\65\66\5\16\b\2\66\67\5\6\4\2\679\3\2\2\28\65\3\2\2\29<\3\2\2"+
		"\2:8\3\2\2\2:;\3\2\2\2;\5\3\2\2\2<:\3\2\2\2=C\5\b\5\2>?\5\f\7\2?@\5\b"+
		"\5\2@B\3\2\2\2A>\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2D\7\3\2\2\2EC\3"+
		"\2\2\2FL\5\n\6\2GH\5\20\t\2HI\5\4\3\2IJ\5\22\n\2JL\3\2\2\2KF\3\2\2\2K"+
		"G\3\2\2\2L\t\3\2\2\2MP\5\24\13\2NP\5\34\17\2OM\3\2\2\2ON\3\2\2\2P\13\3"+
		"\2\2\2QR\7\n\2\2R\r\3\2\2\2ST\7\13\2\2T\17\3\2\2\2UV\7\f\2\2V\21\3\2\2"+
		"\2WX\7\r\2\2X\23\3\2\2\2YZ\5\26\f\2Z[\5\30\r\2[\\\5\32\16\2\\\25\3\2\2"+
		"\2]^\7\17\2\2^\27\3\2\2\2_c\5$\23\2`c\5,\27\2ac\7\3\2\2b_\3\2\2\2b`\3"+
		"\2\2\2ba\3\2\2\2c\31\3\2\2\2de\7\20\2\2e\33\3\2\2\2fg\5\36\20\2gh\5.\30"+
		"\2hi\5\60\31\2i\35\3\2\2\2jk\7\16\2\2k\37\3\2\2\2lm\7\b\2\2m!\3\2\2\2"+
		"no\7\b\2\2op\7\t\2\2p#\3\2\2\2qt\5 \21\2rt\5\"\22\2sq\3\2\2\2sr\3\2\2"+
		"\2t%\3\2\2\2uv\7\t\2\2vw\7\4\2\2w\'\3\2\2\2xy\7\t\2\2yz\7\5\2\2z)\3\2"+
		"\2\2{|\7\t\2\2|}\7\6\2\2}+\3\2\2\2~\u0085\7\4\2\2\177\u0085\5&\24\2\u0080"+
		"\u0085\7\5\2\2\u0081\u0085\5(\25\2\u0082\u0085\7\6\2\2\u0083\u0085\5*"+
		"\26\2\u0084~\3\2\2\2\u0084\177\3\2\2\2\u0084\u0080\3\2\2\2\u0084\u0081"+
		"\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0083\3\2\2\2\u0085-\3\2\2\2\u0086"+
		"\u008a\5$\23\2\u0087\u008a\5,\27\2\u0088\u008a\7\3\2\2\u0089\u0086\3\2"+
		"\2\2\u0089\u0087\3\2\2\2\u0089\u0088\3\2\2\2\u008a/\3\2\2\2\u008b\u008c"+
		"\t\2\2\2\u008c\61\3\2\2\2\n:CKObs\u0084\u0089";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}