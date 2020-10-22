// Generated from c:\code\java-http-server\antlr4\Rql.g4 by ANTLR 4.8
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
			"Type", "String", "Long", "Integer", "Boolean", "DateTime", "ST", "GT", 
			"SEQ", "GEQ", "EQ", "NEQ", "Null", "Is", "Not", "UCaseAlpha", "LCaseAlpha", 
			"Alpha", "Num", "AlphaNum", "A", "B", "C", "D", "E", "F", "G", "H", "I", 
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", 
			"X", "Y", "Z"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17\u0150\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\u0084\n\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3"+
		"\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\7\13\u00a5\n\13\f\13\16\13\u00a8"+
		"\13\13\3\13\3\13\7\13\u00ac\n\13\f\13\16\13\u00af\13\13\3\13\3\13\7\13"+
		"\u00b3\n\13\f\13\16\13\u00b6\13\13\3\f\3\f\3\f\3\r\3\r\3\r\7\r\u00be\n"+
		"\r\f\r\16\r\u00c1\13\r\3\r\3\r\3\r\3\r\3\16\6\16\u00c8\n\16\r\16\16\16"+
		"\u00c9\3\16\3\16\3\17\3\17\3\17\3\17\3\17\5\17\u00d3\n\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3"+
		"\31\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3"+
		"\35\3\35\3\36\3\36\3\37\3\37\3 \3 \5 \u0117\n \3!\3!\3\"\3\"\3#\3#\3$"+
		"\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3"+
		"/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66"+
		"\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\2\2=\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\2\37\2!\2#\2%\2\'\2)\2+\2-\2"+
		"/\2\61\2\63\2\65\2\67\29\2;\2=\2?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W"+
		"\2Y\2[\2]\2_\2a\2c\2e\2g\2i\2k\2m\2o\2q\2s\2u\2w\2\3\2\36\5\2\13\f\17"+
		"\17\"\"\7\2//\62;C\\aac|\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2H"+
		"Hhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4"+
		"\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYy"+
		"y\4\2ZZzz\4\2[[{{\4\2\\\\||\2\u0134\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\3\u0083\3"+
		"\2\2\2\5\u0085\3\2\2\2\7\u008a\3\2\2\2\t\u0090\3\2\2\2\13\u0093\3\2\2"+
		"\2\r\u0097\3\2\2\2\17\u009b\3\2\2\2\21\u009e\3\2\2\2\23\u00a0\3\2\2\2"+
		"\25\u00ad\3\2\2\2\27\u00b7\3\2\2\2\31\u00ba\3\2\2\2\33\u00c7\3\2\2\2\35"+
		"\u00d2\3\2\2\2\37\u00d4\3\2\2\2!\u00db\3\2\2\2#\u00e0\3\2\2\2%\u00e4\3"+
		"\2\2\2\'\u00ec\3\2\2\2)\u00f5\3\2\2\2+\u00f7\3\2\2\2-\u00f9\3\2\2\2/\u00fc"+
		"\3\2\2\2\61\u00ff\3\2\2\2\63\u0101\3\2\2\2\65\u0104\3\2\2\2\67\u0109\3"+
		"\2\2\29\u010c\3\2\2\2;\u0110\3\2\2\2=\u0112\3\2\2\2?\u0116\3\2\2\2A\u0118"+
		"\3\2\2\2C\u011a\3\2\2\2E\u011c\3\2\2\2G\u011e\3\2\2\2I\u0120\3\2\2\2K"+
		"\u0122\3\2\2\2M\u0124\3\2\2\2O\u0126\3\2\2\2Q\u0128\3\2\2\2S\u012a\3\2"+
		"\2\2U\u012c\3\2\2\2W\u012e\3\2\2\2Y\u0130\3\2\2\2[\u0132\3\2\2\2]\u0134"+
		"\3\2\2\2_\u0136\3\2\2\2a\u0138\3\2\2\2c\u013a\3\2\2\2e\u013c\3\2\2\2g"+
		"\u013e\3\2\2\2i\u0140\3\2\2\2k\u0142\3\2\2\2m\u0144\3\2\2\2o\u0146\3\2"+
		"\2\2q\u0148\3\2\2\2s\u014a\3\2\2\2u\u014c\3\2\2\2w\u014e\3\2\2\2y\u0084"+
		"\5)\25\2z\u0084\5-\27\2{\u0084\5+\26\2|\u0084\5/\30\2}\u0084\5\61\31\2"+
		"~\u0084\5\63\32\2\177\u0084\5\5\3\2\u0080\u0084\5\7\4\2\u0081\u0084\5"+
		"\t\5\2\u0082\u0084\5\13\6\2\u0083y\3\2\2\2\u0083z\3\2\2\2\u0083{\3\2\2"+
		"\2\u0083|\3\2\2\2\u0083}\3\2\2\2\u0083~\3\2\2\2\u0083\177\3\2\2\2\u0083"+
		"\u0080\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0082\3\2\2\2\u0084\4\3\2\2\2"+
		"\u0085\u0086\5[.\2\u0086\u0087\5U+\2\u0087\u0088\5Y-\2\u0088\u0089\5M"+
		"\'\2\u0089\6\3\2\2\2\u008a\u008b\59\35\2\u008b\u008c\5[.\2\u008c\u008d"+
		"\5U+\2\u008d\u008e\5Y-\2\u008e\u008f\5M\'\2\u008f\b\3\2\2\2\u0090\u0091"+
		"\5\67\34\2\u0091\u0092\5\65\33\2\u0092\n\3\2\2\2\u0093\u0094\5\67\34\2"+
		"\u0094\u0095\59\35\2\u0095\u0096\5\65\33\2\u0096\f\3\2\2\2\u0097\u0098"+
		"\5E#\2\u0098\u0099\5_\60\2\u0099\u009a\5K&\2\u009a\16\3\2\2\2\u009b\u009c"+
		"\5a\61\2\u009c\u009d\5g\64\2\u009d\20\3\2\2\2\u009e\u009f\7*\2\2\u009f"+
		"\22\3\2\2\2\u00a0\u00a1\7+\2\2\u00a1\24\3\2\2\2\u00a2\u00a6\5? \2\u00a3"+
		"\u00a5\5C\"\2\u00a4\u00a3\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2"+
		"\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a9\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9"+
		"\u00aa\7\60\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a2\3\2\2\2\u00ac\u00af\3"+
		"\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af"+
		"\u00ad\3\2\2\2\u00b0\u00b4\5? \2\u00b1\u00b3\5C\"\2\u00b2\u00b1\3\2\2"+
		"\2\u00b3\u00b6\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\26"+
		"\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b8\7A\2\2\u00b8\u00b9\5\25\13\2"+
		"\u00b9\30\3\2\2\2\u00ba\u00bb\7<\2\2\u00bb\u00bf\5? \2\u00bc\u00be\5C"+
		"\"\2\u00bd\u00bc\3\2\2\2\u00be\u00c1\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf"+
		"\u00c0\3\2\2\2\u00c0\u00c2\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\u00c3\7]"+
		"\2\2\u00c3\u00c4\5\35\17\2\u00c4\u00c5\7_\2\2\u00c5\32\3\2\2\2\u00c6\u00c8"+
		"\t\2\2\2\u00c7\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00c7\3\2\2\2\u00c9"+
		"\u00ca\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\b\16\2\2\u00cc\34\3\2\2"+
		"\2\u00cd\u00d3\5\37\20\2\u00ce\u00d3\5!\21\2\u00cf\u00d3\5#\22\2\u00d0"+
		"\u00d3\5%\23\2\u00d1\u00d3\5\'\24\2\u00d2\u00cd\3\2\2\2\u00d2\u00ce\3"+
		"\2\2\2\u00d2\u00cf\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d1\3\2\2\2\u00d3"+
		"\36\3\2\2\2\u00d4\u00d5\5i\65\2\u00d5\u00d6\5k\66\2\u00d6\u00d7\5g\64"+
		"\2\u00d7\u00d8\5U+\2\u00d8\u00d9\5_\60\2\u00d9\u00da\5Q)\2\u00da \3\2"+
		"\2\2\u00db\u00dc\5[.\2\u00dc\u00dd\5a\61\2\u00dd\u00de\5_\60\2\u00de\u00df"+
		"\5Q)\2\u00df\"\3\2\2\2\u00e0\u00e1\5U+\2\u00e1\u00e2\5_\60\2\u00e2\u00e3"+
		"\5k\66\2\u00e3$\3\2\2\2\u00e4\u00e5\5G$\2\u00e5\u00e6\5a\61\2\u00e6\u00e7"+
		"\5a\61\2\u00e7\u00e8\5[.\2\u00e8\u00e9\5M\'\2\u00e9\u00ea\5E#\2\u00ea"+
		"\u00eb\5_\60\2\u00eb&\3\2\2\2\u00ec\u00ed\5K&\2\u00ed\u00ee\5E#\2\u00ee"+
		"\u00ef\5k\66\2\u00ef\u00f0\5M\'\2\u00f0\u00f1\5k\66\2\u00f1\u00f2\5U+"+
		"\2\u00f2\u00f3\5]/\2\u00f3\u00f4\5M\'\2\u00f4(\3\2\2\2\u00f5\u00f6\7>"+
		"\2\2\u00f6*\3\2\2\2\u00f7\u00f8\7@\2\2\u00f8,\3\2\2\2\u00f9\u00fa\7>\2"+
		"\2\u00fa\u00fb\7?\2\2\u00fb.\3\2\2\2\u00fc\u00fd\7@\2\2\u00fd\u00fe\7"+
		"?\2\2\u00fe\60\3\2\2\2\u00ff\u0100\7?\2\2\u0100\62\3\2\2\2\u0101\u0102"+
		"\7>\2\2\u0102\u0103\7@\2\2\u0103\64\3\2\2\2\u0104\u0105\5_\60\2\u0105"+
		"\u0106\5m\67\2\u0106\u0107\5[.\2\u0107\u0108\5[.\2\u0108\66\3\2\2\2\u0109"+
		"\u010a\5U+\2\u010a\u010b\5i\65\2\u010b8\3\2\2\2\u010c\u010d\5_\60\2\u010d"+
		"\u010e\5a\61\2\u010e\u010f\5k\66\2\u010f:\3\2\2\2\u0110\u0111\4C\\\2\u0111"+
		"<\3\2\2\2\u0112\u0113\4c|\2\u0113>\3\2\2\2\u0114\u0117\5=\37\2\u0115\u0117"+
		"\5;\36\2\u0116\u0114\3\2\2\2\u0116\u0115\3\2\2\2\u0117@\3\2\2\2\u0118"+
		"\u0119\4\62;\2\u0119B\3\2\2\2\u011a\u011b\t\3\2\2\u011bD\3\2\2\2\u011c"+
		"\u011d\t\4\2\2\u011dF\3\2\2\2\u011e\u011f\t\5\2\2\u011fH\3\2\2\2\u0120"+
		"\u0121\t\6\2\2\u0121J\3\2\2\2\u0122\u0123\t\7\2\2\u0123L\3\2\2\2\u0124"+
		"\u0125\t\b\2\2\u0125N\3\2\2\2\u0126\u0127\t\t\2\2\u0127P\3\2\2\2\u0128"+
		"\u0129\t\n\2\2\u0129R\3\2\2\2\u012a\u012b\t\13\2\2\u012bT\3\2\2\2\u012c"+
		"\u012d\t\f\2\2\u012dV\3\2\2\2\u012e\u012f\t\r\2\2\u012fX\3\2\2\2\u0130"+
		"\u0131\t\16\2\2\u0131Z\3\2\2\2\u0132\u0133\t\17\2\2\u0133\\\3\2\2\2\u0134"+
		"\u0135\t\20\2\2\u0135^\3\2\2\2\u0136\u0137\t\21\2\2\u0137`\3\2\2\2\u0138"+
		"\u0139\t\22\2\2\u0139b\3\2\2\2\u013a\u013b\t\23\2\2\u013bd\3\2\2\2\u013c"+
		"\u013d\t\24\2\2\u013df\3\2\2\2\u013e\u013f\t\25\2\2\u013fh\3\2\2\2\u0140"+
		"\u0141\t\26\2\2\u0141j\3\2\2\2\u0142\u0143\t\27\2\2\u0143l\3\2\2\2\u0144"+
		"\u0145\t\30\2\2\u0145n\3\2\2\2\u0146\u0147\t\31\2\2\u0147p\3\2\2\2\u0148"+
		"\u0149\t\32\2\2\u0149r\3\2\2\2\u014a\u014b\t\33\2\2\u014bt\3\2\2\2\u014c"+
		"\u014d\t\34\2\2\u014dv\3\2\2\2\u014e\u014f\t\35\2\2\u014fx\3\2\2\2\13"+
		"\2\u0083\u00a6\u00ad\u00b4\u00bf\u00c9\u00d2\u0116\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}