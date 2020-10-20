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
		T__0=1, T__1=2, Operator=3, NullOp=4, NotNullOp=5, And=6, Or=7, Identifier=8, 
		JpqlIdentifier=9, Whitespace=10;
	public static final int
		RULE_eval = 0, RULE_orExpression = 1, RULE_andExpression = 2, RULE_atomExpression = 3, 
		RULE_term = 4, RULE_operator = 5, RULE_identifier = 6, RULE_jpqlIdentifier = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"eval", "orExpression", "andExpression", "atomExpression", "term", "operator", 
			"identifier", "jpqlIdentifier"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "Operator", "NullOp", "NotNullOp", "And", "Or", "Identifier", 
			"JpqlIdentifier", "Whitespace"
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
		public String r;
		public OrExpressionContext op;
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
			setState(16);
			((EvalContext)_localctx).op = orExpression();
			((EvalContext)_localctx).r =  (((EvalContext)_localctx).op!=null?_input.getText(((EvalContext)_localctx).op.start,((EvalContext)_localctx).op.stop):null);
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
		public String r;
		public AndExpressionContext op;
		public Token an;
		public AndExpressionContext ae;
		public List<AndExpressionContext> andExpression() {
			return getRuleContexts(AndExpressionContext.class);
		}
		public AndExpressionContext andExpression(int i) {
			return getRuleContext(AndExpressionContext.class,i);
		}
		public List<TerminalNode> Or() { return getTokens(RqlParser.Or); }
		public TerminalNode Or(int i) {
			return getToken(RqlParser.Or, i);
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
			setState(19);
			((OrExpressionContext)_localctx).op = andExpression();
			((OrExpressionContext)_localctx).r =  (((OrExpressionContext)_localctx).op!=null?_input.getText(((OrExpressionContext)_localctx).op.start,((OrExpressionContext)_localctx).op.stop):null);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Or) {
				{
				{
				setState(21);
				((OrExpressionContext)_localctx).an = match(Or);
				_localctx.r += " " + (((OrExpressionContext)_localctx).an!=null?((OrExpressionContext)_localctx).an.getText():null) + " ";
				setState(23);
				((OrExpressionContext)_localctx).ae = andExpression();
				_localctx.r += (((OrExpressionContext)_localctx).ae!=null?_input.getText(((OrExpressionContext)_localctx).ae.start,((OrExpressionContext)_localctx).ae.stop):null);
				}
				}
				setState(30);
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
		public String r;
		public AtomExpressionContext op;
		public Token an;
		public AtomExpressionContext ae;
		public List<AtomExpressionContext> atomExpression() {
			return getRuleContexts(AtomExpressionContext.class);
		}
		public AtomExpressionContext atomExpression(int i) {
			return getRuleContext(AtomExpressionContext.class,i);
		}
		public List<TerminalNode> And() { return getTokens(RqlParser.And); }
		public TerminalNode And(int i) {
			return getToken(RqlParser.And, i);
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
			setState(31);
			((AndExpressionContext)_localctx).op = atomExpression();
			((AndExpressionContext)_localctx).r =  (((AndExpressionContext)_localctx).op!=null?_input.getText(((AndExpressionContext)_localctx).op.start,((AndExpressionContext)_localctx).op.stop):null);
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==And) {
				{
				{
				setState(33);
				((AndExpressionContext)_localctx).an = match(And);
				_localctx.r += " " + (((AndExpressionContext)_localctx).an!=null?((AndExpressionContext)_localctx).an.getText():null) + " ";
				setState(35);
				((AndExpressionContext)_localctx).ae = atomExpression();
				_localctx.r += (((AndExpressionContext)_localctx).ae!=null?_input.getText(((AndExpressionContext)_localctx).ae.start,((AndExpressionContext)_localctx).ae.stop):null);
				}
				}
				setState(42);
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
		public String r;
		public TermContext op;
		public OrExpressionContext or;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public OrExpressionContext orExpression() {
			return getRuleContext(OrExpressionContext.class,0);
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
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				((AtomExpressionContext)_localctx).op = term();
				((AtomExpressionContext)_localctx).r =  (((AtomExpressionContext)_localctx).op!=null?_input.getText(((AtomExpressionContext)_localctx).op.start,((AtomExpressionContext)_localctx).op.stop):null);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				match(T__0);
				setState(47);
				((AtomExpressionContext)_localctx).or = orExpression();
				((AtomExpressionContext)_localctx).r =  (((AtomExpressionContext)_localctx).or!=null?_input.getText(((AtomExpressionContext)_localctx).or.start,((AtomExpressionContext)_localctx).or.stop):null);
				setState(49);
				match(T__1);
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

	public static class TermContext extends ParserRuleContext {
		public String r;
		public IdentifierContext id;
		public Token op;
		public JpqlIdentifierContext jd;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode Operator() { return getToken(RqlParser.Operator, 0); }
		public JpqlIdentifierContext jpqlIdentifier() {
			return getRuleContext(JpqlIdentifierContext.class,0);
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
		enterRule(_localctx, 8, RULE_term);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			((TermContext)_localctx).id = identifier();
			((TermContext)_localctx).r =  (((TermContext)_localctx).id!=null?_input.getText(((TermContext)_localctx).id.start,((TermContext)_localctx).id.stop):null);
			setState(55);
			((TermContext)_localctx).op = match(Operator);
			_localctx.r += (((TermContext)_localctx).op!=null?((TermContext)_localctx).op.getText():null);
			setState(57);
			((TermContext)_localctx).jd = jpqlIdentifier();
			_localctx.r += (((TermContext)_localctx).jd!=null?_input.getText(((TermContext)_localctx).jd.start,((TermContext)_localctx).jd.stop):null);
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
		public String r;
		public Token op;
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
		enterRule(_localctx, 10, RULE_operator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			((OperatorContext)_localctx).op = match(Operator);
			((OperatorContext)_localctx).r =  " " + (((OperatorContext)_localctx).op!=null?((OperatorContext)_localctx).op.getText():null) + " ";
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

	public static class IdentifierContext extends ParserRuleContext {
		public String r;
		public Token op;
		public TerminalNode Identifier() { return getToken(RqlParser.Identifier, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			((IdentifierContext)_localctx).op = match(Identifier);
			((IdentifierContext)_localctx).r =  (((IdentifierContext)_localctx).op!=null?((IdentifierContext)_localctx).op.getText():null);
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

	public static class JpqlIdentifierContext extends ParserRuleContext {
		public String r;
		public Token op;
		public TerminalNode JpqlIdentifier() { return getToken(RqlParser.JpqlIdentifier, 0); }
		public JpqlIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jpqlIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).enterJpqlIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RqlListener ) ((RqlListener)listener).exitJpqlIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RqlVisitor ) return ((RqlVisitor<? extends T>)visitor).visitJpqlIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JpqlIdentifierContext jpqlIdentifier() throws RecognitionException {
		JpqlIdentifierContext _localctx = new JpqlIdentifierContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_jpqlIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			((JpqlIdentifierContext)_localctx).op = match(JpqlIdentifier);
			((JpqlIdentifierContext)_localctx).r =  (((JpqlIdentifierContext)_localctx).op!=null?((JpqlIdentifierContext)_localctx).op.getText():null);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\fH\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\7\3\35\n\3\f\3\16\3 \13\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\7\4)\n\4\f\4\16\4,\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\66\n"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t"+
		"\2\2\n\2\4\6\b\n\f\16\20\2\2\2B\2\22\3\2\2\2\4\25\3\2\2\2\6!\3\2\2\2\b"+
		"\65\3\2\2\2\n\67\3\2\2\2\f>\3\2\2\2\16A\3\2\2\2\20D\3\2\2\2\22\23\5\4"+
		"\3\2\23\24\b\2\1\2\24\3\3\2\2\2\25\26\5\6\4\2\26\36\b\3\1\2\27\30\7\t"+
		"\2\2\30\31\b\3\1\2\31\32\5\6\4\2\32\33\b\3\1\2\33\35\3\2\2\2\34\27\3\2"+
		"\2\2\35 \3\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37\5\3\2\2\2 \36\3\2\2\2"+
		"!\"\5\b\5\2\"*\b\4\1\2#$\7\b\2\2$%\b\4\1\2%&\5\b\5\2&\'\b\4\1\2\')\3\2"+
		"\2\2(#\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\7\3\2\2\2,*\3\2\2\2-.\5"+
		"\n\6\2./\b\5\1\2/\66\3\2\2\2\60\61\7\3\2\2\61\62\5\4\3\2\62\63\b\5\1\2"+
		"\63\64\7\4\2\2\64\66\3\2\2\2\65-\3\2\2\2\65\60\3\2\2\2\66\t\3\2\2\2\67"+
		"8\5\16\b\289\b\6\1\29:\7\5\2\2:;\b\6\1\2;<\5\20\t\2<=\b\6\1\2=\13\3\2"+
		"\2\2>?\7\5\2\2?@\b\7\1\2@\r\3\2\2\2AB\7\n\2\2BC\b\b\1\2C\17\3\2\2\2DE"+
		"\7\13\2\2EF\b\t\1\2F\21\3\2\2\2\5\36*\65";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}