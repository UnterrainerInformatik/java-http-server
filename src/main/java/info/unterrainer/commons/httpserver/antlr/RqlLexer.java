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
			"VarName", "CForType", "Type", "Types", "String", "Str", "Float", "Double", 
			"Dbl", "Long", "Lng", "Integer", "Int", "Boolean", "Bool", "DateTime", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17\u018e\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\u0098\n\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\t\3\t\3\n\3\n\3\13\3\13\3\13\7\13\u00ba\n\13\f\13\16\13\u00bd\13\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\6\16\u00cb\n\16\r"+
		"\16\16\16\u00cc\3\16\3\16\3\17\3\17\7\17\u00d3\n\17\f\17\16\17\u00d6\13"+
		"\17\3\20\3\20\3\20\3\21\3\21\5\21\u00dd\n\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00eb\n\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3 \3"+
		" \3!\3!\3!\3\"\3\"\3\"\3#\3#\3$\3$\3$\3%\3%\3%\3%\3%\3&\3&\3&\3\'\3\'"+
		"\3\'\3\'\3(\3(\3)\3)\3*\3*\5*\u0155\n*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3"+
		"\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3"+
		"\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\3A\3A\3"+
		"B\3B\3C\3C\3D\3D\3E\3E\3F\3F\2\2G\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\2\37\2!\2#\2%\2\'\2)\2+\2-\2/\2\61\2\63\2"+
		"\65\2\67\29\2;\2=\2?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y\2[\2]\2_\2"+
		"a\2c\2e\2g\2i\2k\2m\2o\2q\2s\2u\2w\2y\2{\2}\2\177\2\u0081\2\u0083\2\u0085"+
		"\2\u0087\2\u0089\2\u008b\2\3\2\36\5\2\13\f\17\17\"\"\7\2//\62;C\\aac|"+
		"\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2K"+
		"Kkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4"+
		"\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\"+
		"||\2\u016e\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\3\u0097\3\2\2\2\5\u0099\3\2\2\2\7\u009e"+
		"\3\2\2\2\t\u00a4\3\2\2\2\13\u00a7\3\2\2\2\r\u00ab\3\2\2\2\17\u00af\3\2"+
		"\2\2\21\u00b2\3\2\2\2\23\u00b4\3\2\2\2\25\u00bb\3\2\2\2\27\u00c0\3\2\2"+
		"\2\31\u00c3\3\2\2\2\33\u00ca\3\2\2\2\35\u00d0\3\2\2\2\37\u00d7\3\2\2\2"+
		"!\u00dc\3\2\2\2#\u00ea\3\2\2\2%\u00ec\3\2\2\2\'\u00f3\3\2\2\2)\u00f7\3"+
		"\2\2\2+\u00fd\3\2\2\2-\u0104\3\2\2\2/\u0108\3\2\2\2\61\u010d\3\2\2\2\63"+
		"\u0111\3\2\2\2\65\u0119\3\2\2\2\67\u011d\3\2\2\29\u0125\3\2\2\2;\u012a"+
		"\3\2\2\2=\u0133\3\2\2\2?\u0135\3\2\2\2A\u0137\3\2\2\2C\u013a\3\2\2\2E"+
		"\u013d\3\2\2\2G\u013f\3\2\2\2I\u0142\3\2\2\2K\u0147\3\2\2\2M\u014a\3\2"+
		"\2\2O\u014e\3\2\2\2Q\u0150\3\2\2\2S\u0154\3\2\2\2U\u0156\3\2\2\2W\u0158"+
		"\3\2\2\2Y\u015a\3\2\2\2[\u015c\3\2\2\2]\u015e\3\2\2\2_\u0160\3\2\2\2a"+
		"\u0162\3\2\2\2c\u0164\3\2\2\2e\u0166\3\2\2\2g\u0168\3\2\2\2i\u016a\3\2"+
		"\2\2k\u016c\3\2\2\2m\u016e\3\2\2\2o\u0170\3\2\2\2q\u0172\3\2\2\2s\u0174"+
		"\3\2\2\2u\u0176\3\2\2\2w\u0178\3\2\2\2y\u017a\3\2\2\2{\u017c\3\2\2\2}"+
		"\u017e\3\2\2\2\177\u0180\3\2\2\2\u0081\u0182\3\2\2\2\u0083\u0184\3\2\2"+
		"\2\u0085\u0186\3\2\2\2\u0087\u0188\3\2\2\2\u0089\u018a\3\2\2\2\u008b\u018c"+
		"\3\2\2\2\u008d\u0098\5=\37\2\u008e\u0098\5A!\2\u008f\u0098\5? \2\u0090"+
		"\u0098\5C\"\2\u0091\u0098\5E#\2\u0092\u0098\5G$\2\u0093\u0098\5\5\3\2"+
		"\u0094\u0098\5\7\4\2\u0095\u0098\5\t\5\2\u0096\u0098\5\13\6\2\u0097\u008d"+
		"\3\2\2\2\u0097\u008e\3\2\2\2\u0097\u008f\3\2\2\2\u0097\u0090\3\2\2\2\u0097"+
		"\u0091\3\2\2\2\u0097\u0092\3\2\2\2\u0097\u0093\3\2\2\2\u0097\u0094\3\2"+
		"\2\2\u0097\u0095\3\2\2\2\u0097\u0096\3\2\2\2\u0098\4\3\2\2\2\u0099\u009a"+
		"\5o8\2\u009a\u009b\5i\65\2\u009b\u009c\5m\67\2\u009c\u009d\5a\61\2\u009d"+
		"\6\3\2\2\2\u009e\u009f\5M\'\2\u009f\u00a0\5o8\2\u00a0\u00a1\5i\65\2\u00a1"+
		"\u00a2\5m\67\2\u00a2\u00a3\5a\61\2\u00a3\b\3\2\2\2\u00a4\u00a5\5K&\2\u00a5"+
		"\u00a6\5I%\2\u00a6\n\3\2\2\2\u00a7\u00a8\5K&\2\u00a8\u00a9\5M\'\2\u00a9"+
		"\u00aa\5I%\2\u00aa\f\3\2\2\2\u00ab\u00ac\5Y-\2\u00ac\u00ad\5s:\2\u00ad"+
		"\u00ae\5_\60\2\u00ae\16\3\2\2\2\u00af\u00b0\5u;\2\u00b0\u00b1\5{>\2\u00b1"+
		"\20\3\2\2\2\u00b2\u00b3\7*\2\2\u00b3\22\3\2\2\2\u00b4\u00b5\7+\2\2\u00b5"+
		"\24\3\2\2\2\u00b6\u00b7\5\35\17\2\u00b7\u00b8\7\60\2\2\u00b8\u00ba\3\2"+
		"\2\2\u00b9\u00b6\3\2\2\2\u00ba\u00bd\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb"+
		"\u00bc\3\2\2\2\u00bc\u00be\3\2\2\2\u00bd\u00bb\3\2\2\2\u00be\u00bf\5\35"+
		"\17\2\u00bf\26\3\2\2\2\u00c0\u00c1\7A\2\2\u00c1\u00c2\5\25\13\2\u00c2"+
		"\30\3\2\2\2\u00c3\u00c4\7<\2\2\u00c4\u00c5\5\35\17\2\u00c5\u00c6\7]\2"+
		"\2\u00c6\u00c7\5!\21\2\u00c7\u00c8\7_\2\2\u00c8\32\3\2\2\2\u00c9\u00cb"+
		"\t\2\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc"+
		"\u00cd\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00cf\b\16\2\2\u00cf\34\3\2\2"+
		"\2\u00d0\u00d4\5S*\2\u00d1\u00d3\5W,\2\u00d2\u00d1\3\2\2\2\u00d3\u00d6"+
		"\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\36\3\2\2\2\u00d6"+
		"\u00d4\3\2\2\2\u00d7\u00d8\7\u0080\2\2\u00d8\u00d9\5\35\17\2\u00d9 \3"+
		"\2\2\2\u00da\u00dd\5\37\20\2\u00db\u00dd\5#\22\2\u00dc\u00da\3\2\2\2\u00dc"+
		"\u00db\3\2\2\2\u00dd\"\3\2\2\2\u00de\u00eb\5%\23\2\u00df\u00eb\5\'\24"+
		"\2\u00e0\u00eb\5)\25\2\u00e1\u00eb\5+\26\2\u00e2\u00eb\5-\27\2\u00e3\u00eb"+
		"\5/\30\2\u00e4\u00eb\5\61\31\2\u00e5\u00eb\5\63\32\2\u00e6\u00eb\5\65"+
		"\33\2\u00e7\u00eb\5\67\34\2\u00e8\u00eb\59\35\2\u00e9\u00eb\5;\36\2\u00ea"+
		"\u00de\3\2\2\2\u00ea\u00df\3\2\2\2\u00ea\u00e0\3\2\2\2\u00ea\u00e1\3\2"+
		"\2\2\u00ea\u00e2\3\2\2\2\u00ea\u00e3\3\2\2\2\u00ea\u00e4\3\2\2\2\u00ea"+
		"\u00e5\3\2\2\2\u00ea\u00e6\3\2\2\2\u00ea\u00e7\3\2\2\2\u00ea\u00e8\3\2"+
		"\2\2\u00ea\u00e9\3\2\2\2\u00eb$\3\2\2\2\u00ec\u00ed\5}?\2\u00ed\u00ee"+
		"\5\177@\2\u00ee\u00ef\5{>\2\u00ef\u00f0\5i\65\2\u00f0\u00f1\5s:\2\u00f1"+
		"\u00f2\5e\63\2\u00f2&\3\2\2\2\u00f3\u00f4\5}?\2\u00f4\u00f5\5\177@\2\u00f5"+
		"\u00f6\5{>\2\u00f6(\3\2\2\2\u00f7\u00f8\5c\62\2\u00f8\u00f9\5o8\2\u00f9"+
		"\u00fa\5u;\2\u00fa\u00fb\5Y-\2\u00fb\u00fc\5\177@\2\u00fc*\3\2\2\2\u00fd"+
		"\u00fe\5_\60\2\u00fe\u00ff\5u;\2\u00ff\u0100\5\u0081A\2\u0100\u0101\5"+
		"[.\2\u0101\u0102\5o8\2\u0102\u0103\5a\61\2\u0103,\3\2\2\2\u0104\u0105"+
		"\5_\60\2\u0105\u0106\5[.\2\u0106\u0107\5o8\2\u0107.\3\2\2\2\u0108\u0109"+
		"\5o8\2\u0109\u010a\5u;\2\u010a\u010b\5s:\2\u010b\u010c\5e\63\2\u010c\60"+
		"\3\2\2\2\u010d\u010e\5o8\2\u010e\u010f\5s:\2\u010f\u0110\5e\63\2\u0110"+
		"\62\3\2\2\2\u0111\u0112\5i\65\2\u0112\u0113\5s:\2\u0113\u0114\5\177@\2"+
		"\u0114\u0115\5a\61\2\u0115\u0116\5e\63\2\u0116\u0117\5a\61\2\u0117\u0118"+
		"\5{>\2\u0118\64\3\2\2\2\u0119\u011a\5i\65\2\u011a\u011b\5s:\2\u011b\u011c"+
		"\5\177@\2\u011c\66\3\2\2\2\u011d\u011e\5[.\2\u011e\u011f\5u;\2\u011f\u0120"+
		"\5u;\2\u0120\u0121\5o8\2\u0121\u0122\5a\61\2\u0122\u0123\5Y-\2\u0123\u0124"+
		"\5s:\2\u01248\3\2\2\2\u0125\u0126\5[.\2\u0126\u0127\5u;\2\u0127\u0128"+
		"\5u;\2\u0128\u0129\5o8\2\u0129:\3\2\2\2\u012a\u012b\5_\60\2\u012b\u012c"+
		"\5Y-\2\u012c\u012d\5\177@\2\u012d\u012e\5a\61\2\u012e\u012f\5\177@\2\u012f"+
		"\u0130\5i\65\2\u0130\u0131\5q9\2\u0131\u0132\5a\61\2\u0132<\3\2\2\2\u0133"+
		"\u0134\7>\2\2\u0134>\3\2\2\2\u0135\u0136\7@\2\2\u0136@\3\2\2\2\u0137\u0138"+
		"\7>\2\2\u0138\u0139\7?\2\2\u0139B\3\2\2\2\u013a\u013b\7@\2\2\u013b\u013c"+
		"\7?\2\2\u013cD\3\2\2\2\u013d\u013e\7?\2\2\u013eF\3\2\2\2\u013f\u0140\7"+
		">\2\2\u0140\u0141\7@\2\2\u0141H\3\2\2\2\u0142\u0143\5s:\2\u0143\u0144"+
		"\5\u0081A\2\u0144\u0145\5o8\2\u0145\u0146\5o8\2\u0146J\3\2\2\2\u0147\u0148"+
		"\5i\65\2\u0148\u0149\5}?\2\u0149L\3\2\2\2\u014a\u014b\5s:\2\u014b\u014c"+
		"\5u;\2\u014c\u014d\5\177@\2\u014dN\3\2\2\2\u014e\u014f\4C\\\2\u014fP\3"+
		"\2\2\2\u0150\u0151\4c|\2\u0151R\3\2\2\2\u0152\u0155\5Q)\2\u0153\u0155"+
		"\5O(\2\u0154\u0152\3\2\2\2\u0154\u0153\3\2\2\2\u0155T\3\2\2\2\u0156\u0157"+
		"\4\62;\2\u0157V\3\2\2\2\u0158\u0159\t\3\2\2\u0159X\3\2\2\2\u015a\u015b"+
		"\t\4\2\2\u015bZ\3\2\2\2\u015c\u015d\t\5\2\2\u015d\\\3\2\2\2\u015e\u015f"+
		"\t\6\2\2\u015f^\3\2\2\2\u0160\u0161\t\7\2\2\u0161`\3\2\2\2\u0162\u0163"+
		"\t\b\2\2\u0163b\3\2\2\2\u0164\u0165\t\t\2\2\u0165d\3\2\2\2\u0166\u0167"+
		"\t\n\2\2\u0167f\3\2\2\2\u0168\u0169\t\13\2\2\u0169h\3\2\2\2\u016a\u016b"+
		"\t\f\2\2\u016bj\3\2\2\2\u016c\u016d\t\r\2\2\u016dl\3\2\2\2\u016e\u016f"+
		"\t\16\2\2\u016fn\3\2\2\2\u0170\u0171\t\17\2\2\u0171p\3\2\2\2\u0172\u0173"+
		"\t\20\2\2\u0173r\3\2\2\2\u0174\u0175\t\21\2\2\u0175t\3\2\2\2\u0176\u0177"+
		"\t\22\2\2\u0177v\3\2\2\2\u0178\u0179\t\23\2\2\u0179x\3\2\2\2\u017a\u017b"+
		"\t\24\2\2\u017bz\3\2\2\2\u017c\u017d\t\25\2\2\u017d|\3\2\2\2\u017e\u017f"+
		"\t\26\2\2\u017f~\3\2\2\2\u0180\u0181\t\27\2\2\u0181\u0080\3\2\2\2\u0182"+
		"\u0183\t\30\2\2\u0183\u0082\3\2\2\2\u0184\u0185\t\31\2\2\u0185\u0084\3"+
		"\2\2\2\u0186\u0187\t\32\2\2\u0187\u0086\3\2\2\2\u0188\u0189\t\33\2\2\u0189"+
		"\u0088\3\2\2\2\u018a\u018b\t\34\2\2\u018b\u008a\3\2\2\2\u018c\u018d\t"+
		"\35\2\2\u018d\u008c\3\2\2\2\n\2\u0097\u00bb\u00cc\u00d4\u00dc\u00ea\u0154"+
		"\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}