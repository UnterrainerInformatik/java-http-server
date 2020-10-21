// Generated from c:\code\java-http-server\antlr4\Rql.g4 by ANTLR 4.8
package info.unterrainer.commons.httpserver.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RqlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Operator=1, Like=2, NLike=3, NullOp=4, NotNullOp=5, And=6, Or=7, ParOpen=8, 
		ParClose=9, Identifier=10, OptIdentifier=11, JpqlIdentifier=12, Whitespace=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Operator", "Like", "NLike", "NullOp", "NotNullOp", "And", "Or", "ParOpen", 
			"ParClose", "Identifier", "OptIdentifier", "JpqlIdentifier", "Whitespace", 
			"ST", "GT", "SEQ", "GEQ", "EQ", "NEQ", "Null", "Is", "Not", "UCaseAlpha", 
			"LCaseAlpha", "Alpha", "Num", "AlphaNum", "A", "B", "C", "D", "E", "F", 
			"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", 
			"U", "V", "W", "X", "Y", "Z"
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


	public RqlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Rql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17\u0118\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2"+
		"x\n\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\7\13\u0099"+
		"\n\13\f\13\16\13\u009c\13\13\3\13\3\13\7\13\u00a0\n\13\f\13\16\13\u00a3"+
		"\13\13\3\13\3\13\7\13\u00a7\n\13\f\13\16\13\u00aa\13\13\3\f\3\f\3\f\3"+
		"\r\3\r\3\r\7\r\u00b2\n\r\f\r\16\r\u00b5\13\r\3\16\6\16\u00b8\n\16\r\16"+
		"\16\16\u00b9\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3"+
		"\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3"+
		"\27\3\27\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\5\32\u00df\n\32\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3"+
		"#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3."+
		"\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66"+
		"\3\66\2\2\67\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\2\37\2!\2#\2%\2\'\2)\2+\2-\2/\2\61\2\63\2\65\2\67\29\2;\2=\2"+
		"?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y\2[\2]\2_\2a\2c\2e\2g\2i\2k\2"+
		"\3\2\36\5\2\13\f\17\17\"\"\7\2//\62;C\\aac|\4\2CCcc\4\2DDdd\4\2EEee\4"+
		"\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNn"+
		"n\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2"+
		"WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\2\u00fe\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33"+
		"\3\2\2\2\3w\3\2\2\2\5y\3\2\2\2\7~\3\2\2\2\t\u0084\3\2\2\2\13\u0087\3\2"+
		"\2\2\r\u008b\3\2\2\2\17\u008f\3\2\2\2\21\u0092\3\2\2\2\23\u0094\3\2\2"+
		"\2\25\u00a1\3\2\2\2\27\u00ab\3\2\2\2\31\u00ae\3\2\2\2\33\u00b7\3\2\2\2"+
		"\35\u00bd\3\2\2\2\37\u00bf\3\2\2\2!\u00c1\3\2\2\2#\u00c4\3\2\2\2%\u00c7"+
		"\3\2\2\2\'\u00c9\3\2\2\2)\u00cc\3\2\2\2+\u00d1\3\2\2\2-\u00d4\3\2\2\2"+
		"/\u00d8\3\2\2\2\61\u00da\3\2\2\2\63\u00de\3\2\2\2\65\u00e0\3\2\2\2\67"+
		"\u00e2\3\2\2\29\u00e4\3\2\2\2;\u00e6\3\2\2\2=\u00e8\3\2\2\2?\u00ea\3\2"+
		"\2\2A\u00ec\3\2\2\2C\u00ee\3\2\2\2E\u00f0\3\2\2\2G\u00f2\3\2\2\2I\u00f4"+
		"\3\2\2\2K\u00f6\3\2\2\2M\u00f8\3\2\2\2O\u00fa\3\2\2\2Q\u00fc\3\2\2\2S"+
		"\u00fe\3\2\2\2U\u0100\3\2\2\2W\u0102\3\2\2\2Y\u0104\3\2\2\2[\u0106\3\2"+
		"\2\2]\u0108\3\2\2\2_\u010a\3\2\2\2a\u010c\3\2\2\2c\u010e\3\2\2\2e\u0110"+
		"\3\2\2\2g\u0112\3\2\2\2i\u0114\3\2\2\2k\u0116\3\2\2\2mx\5\35\17\2nx\5"+
		"!\21\2ox\5\37\20\2px\5#\22\2qx\5%\23\2rx\5\'\24\2sx\5\5\3\2tx\5\7\4\2"+
		"ux\5\t\5\2vx\5\13\6\2wm\3\2\2\2wn\3\2\2\2wo\3\2\2\2wp\3\2\2\2wq\3\2\2"+
		"\2wr\3\2\2\2ws\3\2\2\2wt\3\2\2\2wu\3\2\2\2wv\3\2\2\2x\4\3\2\2\2yz\5O("+
		"\2z{\5I%\2{|\5M\'\2|}\5A!\2}\6\3\2\2\2~\177\5-\27\2\177\u0080\5O(\2\u0080"+
		"\u0081\5I%\2\u0081\u0082\5M\'\2\u0082\u0083\5A!\2\u0083\b\3\2\2\2\u0084"+
		"\u0085\5+\26\2\u0085\u0086\5)\25\2\u0086\n\3\2\2\2\u0087\u0088\5+\26\2"+
		"\u0088\u0089\5-\27\2\u0089\u008a\5)\25\2\u008a\f\3\2\2\2\u008b\u008c\5"+
		"9\35\2\u008c\u008d\5S*\2\u008d\u008e\5? \2\u008e\16\3\2\2\2\u008f\u0090"+
		"\5U+\2\u0090\u0091\5[.\2\u0091\20\3\2\2\2\u0092\u0093\7*\2\2\u0093\22"+
		"\3\2\2\2\u0094\u0095\7+\2\2\u0095\24\3\2\2\2\u0096\u009a\5\63\32\2\u0097"+
		"\u0099\5\67\34\2\u0098\u0097\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3"+
		"\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\3\2\2\2\u009c\u009a\3\2\2\2\u009d"+
		"\u009e\7\60\2\2\u009e\u00a0\3\2\2\2\u009f\u0096\3\2\2\2\u00a0\u00a3\3"+
		"\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a4\3\2\2\2\u00a3"+
		"\u00a1\3\2\2\2\u00a4\u00a8\5\63\32\2\u00a5\u00a7\5\67\34\2\u00a6\u00a5"+
		"\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9"+
		"\26\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00ac\7A\2\2\u00ac\u00ad\5\25\13"+
		"\2\u00ad\30\3\2\2\2\u00ae\u00af\7<\2\2\u00af\u00b3\5\63\32\2\u00b0\u00b2"+
		"\5\67\34\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2"+
		"\u00b3\u00b4\3\2\2\2\u00b4\32\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00b8"+
		"\t\2\2\2\u00b7\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9"+
		"\u00ba\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\b\16\2\2\u00bc\34\3\2\2"+
		"\2\u00bd\u00be\7>\2\2\u00be\36\3\2\2\2\u00bf\u00c0\7@\2\2\u00c0 \3\2\2"+
		"\2\u00c1\u00c2\7>\2\2\u00c2\u00c3\7?\2\2\u00c3\"\3\2\2\2\u00c4\u00c5\7"+
		"@\2\2\u00c5\u00c6\7?\2\2\u00c6$\3\2\2\2\u00c7\u00c8\7?\2\2\u00c8&\3\2"+
		"\2\2\u00c9\u00ca\7>\2\2\u00ca\u00cb\7@\2\2\u00cb(\3\2\2\2\u00cc\u00cd"+
		"\5S*\2\u00cd\u00ce\5a\61\2\u00ce\u00cf\5O(\2\u00cf\u00d0\5O(\2\u00d0*"+
		"\3\2\2\2\u00d1\u00d2\5I%\2\u00d2\u00d3\5]/\2\u00d3,\3\2\2\2\u00d4\u00d5"+
		"\5S*\2\u00d5\u00d6\5U+\2\u00d6\u00d7\5_\60\2\u00d7.\3\2\2\2\u00d8\u00d9"+
		"\4C\\\2\u00d9\60\3\2\2\2\u00da\u00db\4c|\2\u00db\62\3\2\2\2\u00dc\u00df"+
		"\5\61\31\2\u00dd\u00df\5/\30\2\u00de\u00dc\3\2\2\2\u00de\u00dd\3\2\2\2"+
		"\u00df\64\3\2\2\2\u00e0\u00e1\4\62;\2\u00e1\66\3\2\2\2\u00e2\u00e3\t\3"+
		"\2\2\u00e38\3\2\2\2\u00e4\u00e5\t\4\2\2\u00e5:\3\2\2\2\u00e6\u00e7\t\5"+
		"\2\2\u00e7<\3\2\2\2\u00e8\u00e9\t\6\2\2\u00e9>\3\2\2\2\u00ea\u00eb\t\7"+
		"\2\2\u00eb@\3\2\2\2\u00ec\u00ed\t\b\2\2\u00edB\3\2\2\2\u00ee\u00ef\t\t"+
		"\2\2\u00efD\3\2\2\2\u00f0\u00f1\t\n\2\2\u00f1F\3\2\2\2\u00f2\u00f3\t\13"+
		"\2\2\u00f3H\3\2\2\2\u00f4\u00f5\t\f\2\2\u00f5J\3\2\2\2\u00f6\u00f7\t\r"+
		"\2\2\u00f7L\3\2\2\2\u00f8\u00f9\t\16\2\2\u00f9N\3\2\2\2\u00fa\u00fb\t"+
		"\17\2\2\u00fbP\3\2\2\2\u00fc\u00fd\t\20\2\2\u00fdR\3\2\2\2\u00fe\u00ff"+
		"\t\21\2\2\u00ffT\3\2\2\2\u0100\u0101\t\22\2\2\u0101V\3\2\2\2\u0102\u0103"+
		"\t\23\2\2\u0103X\3\2\2\2\u0104\u0105\t\24\2\2\u0105Z\3\2\2\2\u0106\u0107"+
		"\t\25\2\2\u0107\\\3\2\2\2\u0108\u0109\t\26\2\2\u0109^\3\2\2\2\u010a\u010b"+
		"\t\27\2\2\u010b`\3\2\2\2\u010c\u010d\t\30\2\2\u010db\3\2\2\2\u010e\u010f"+
		"\t\31\2\2\u010fd\3\2\2\2\u0110\u0111\t\32\2\2\u0111f\3\2\2\2\u0112\u0113"+
		"\t\33\2\2\u0113h\3\2\2\2\u0114\u0115\t\34\2\2\u0115j\3\2\2\2\u0116\u0117"+
		"\t\35\2\2\u0117l\3\2\2\2\n\2w\u009a\u00a1\u00a8\u00b3\u00b9\u00de\3\2"+
		"\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}