// Generated from c:\code\java-http-server\antlr4\Rql.g4 by ANTLR 4.8
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
		Operator=1, Like=2, NLike=3, NullOp=4, NotNullOp=5, And=6, Or=7, ParOpen=8, 
		ParClose=9, Identifier=10, OptIdentifier=11, JpqlIdentifier=12, Whitespace=13;
	public static final int
		RULE_eval = 0, RULE_orExpression = 1, RULE_andExpression = 2, RULE_atomExpression = 3, 
		RULE_atomTerm = 4, RULE_and = 5, RULE_or = 6, RULE_parOpen = 7, RULE_parClose = 8, 
		RULE_optTerm = 9, RULE_term = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"eval", "orExpression", "andExpression", "atomExpression", "atomTerm", 
			"and", "or", "parOpen", "parClose", "optTerm", "term"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Operator", "Like", "NLike", "NullOp", "NotNullOp", "And", "Or", 
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
	}

	public final EvalContext eval() throws RecognitionException {
		EvalContext _localctx = new EvalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_eval);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
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
	}

	public final OrExpressionContext orExpression() throws RecognitionException {
		OrExpressionContext _localctx = new OrExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_orExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			andExpression();
			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Or) {
				{
				{
				setState(25);
				or();
				setState(26);
				andExpression();
				}
				}
				setState(32);
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
	}

	public final AndExpressionContext andExpression() throws RecognitionException {
		AndExpressionContext _localctx = new AndExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_andExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			atomExpression();
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==And) {
				{
				{
				setState(34);
				and();
				setState(35);
				atomExpression();
				}
				}
				setState(41);
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
	}

	public final AtomExpressionContext atomExpression() throws RecognitionException {
		AtomExpressionContext _localctx = new AtomExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_atomExpression);
		try {
			setState(47);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
			case OptIdentifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				atomTerm();
				}
				break;
			case ParOpen:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(43);
				parOpen();
				setState(44);
				orExpression();
				setState(45);
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
	}

	public final AtomTermContext atomTerm() throws RecognitionException {
		AtomTermContext _localctx = new AtomTermContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atomTerm);
		try {
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OptIdentifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				optTerm();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
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
	}

	public final AndContext and() throws RecognitionException {
		AndContext _localctx = new AndContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
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
	}

	public final OrContext or() throws RecognitionException {
		OrContext _localctx = new OrContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
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
	}

	public final ParOpenContext parOpen() throws RecognitionException {
		ParOpenContext _localctx = new ParOpenContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_parOpen);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
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
	}

	public final ParCloseContext parClose() throws RecognitionException {
		ParCloseContext _localctx = new ParCloseContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_parClose);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
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
		public TerminalNode OptIdentifier() { return getToken(RqlParser.OptIdentifier, 0); }
		public TerminalNode Operator() { return getToken(RqlParser.Operator, 0); }
		public TerminalNode JpqlIdentifier() { return getToken(RqlParser.JpqlIdentifier, 0); }
		public OptTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optTerm; }
	}

	public final OptTermContext optTerm() throws RecognitionException {
		OptTermContext _localctx = new OptTermContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_optTerm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(OptIdentifier);
			setState(62);
			match(Operator);
			setState(63);
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
		public TerminalNode Identifier() { return getToken(RqlParser.Identifier, 0); }
		public TerminalNode Operator() { return getToken(RqlParser.Operator, 0); }
		public TerminalNode JpqlIdentifier() { return getToken(RqlParser.JpqlIdentifier, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_term);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(Identifier);
			setState(66);
			match(Operator);
			setState(67);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\17H\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\3\3\3\3\3\3\3\3\7\3\37\n\3\f\3\16\3\"\13\3\3\4\3\4\3\4"+
		"\3\4\7\4(\n\4\f\4\16\4+\13\4\3\5\3\5\3\5\3\5\3\5\5\5\62\n\5\3\6\3\6\5"+
		"\6\66\n\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f"+
		"\3\f\3\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\2\2@\2\30\3\2\2\2\4\32"+
		"\3\2\2\2\6#\3\2\2\2\b\61\3\2\2\2\n\65\3\2\2\2\f\67\3\2\2\2\169\3\2\2\2"+
		"\20;\3\2\2\2\22=\3\2\2\2\24?\3\2\2\2\26C\3\2\2\2\30\31\5\4\3\2\31\3\3"+
		"\2\2\2\32 \5\6\4\2\33\34\5\16\b\2\34\35\5\6\4\2\35\37\3\2\2\2\36\33\3"+
		"\2\2\2\37\"\3\2\2\2 \36\3\2\2\2 !\3\2\2\2!\5\3\2\2\2\" \3\2\2\2#)\5\b"+
		"\5\2$%\5\f\7\2%&\5\b\5\2&(\3\2\2\2\'$\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3"+
		"\2\2\2*\7\3\2\2\2+)\3\2\2\2,\62\5\n\6\2-.\5\20\t\2./\5\4\3\2/\60\5\22"+
		"\n\2\60\62\3\2\2\2\61,\3\2\2\2\61-\3\2\2\2\62\t\3\2\2\2\63\66\5\24\13"+
		"\2\64\66\5\26\f\2\65\63\3\2\2\2\65\64\3\2\2\2\66\13\3\2\2\2\678\7\b\2"+
		"\28\r\3\2\2\29:\7\t\2\2:\17\3\2\2\2;<\7\n\2\2<\21\3\2\2\2=>\7\13\2\2>"+
		"\23\3\2\2\2?@\7\r\2\2@A\7\3\2\2AB\7\16\2\2B\25\3\2\2\2CD\7\f\2\2DE\7\3"+
		"\2\2EF\7\16\2\2F\27\3\2\2\2\6 )\61\65";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}