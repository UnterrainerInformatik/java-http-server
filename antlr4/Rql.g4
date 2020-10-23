grammar Rql;

/*
 * Parser Rules start with lower-case characters.
 */
eval            : orExpression;

orExpression    : andExpression (or andExpression)*;

andExpression   : atomExpression (and atomExpression)*;

atomExpression  : atomTerm | (parOpen orExpression parClose);

atomTerm        : optTerm | term;

and             : And;

or              : Or;

parOpen         : ParOpen;

parClose        : ParClose;

optTerm         : optTerm1 optOperator optTerm2;
optTerm1        : OptIdentifier;
optOperator     : Operator;
optTerm2        : JpqlIdentifier;

term            : term1 operator term2;
term1           : Identifier;
nullOperator1   : Is;
nullOperator2   : Is Not;
nullOperator    : nullOperator1 | nullOperator2;
operator        : nullOperator | Operator;
term2           : Null | JpqlIdentifier;
                
/*
 * Lexer Rules start with upper-case characters.
 */
Operator        : ST | SEQ | GT | GEQ | EQ | EQAlt | NEQ | NEQAlt | Like | NLike;
Like            : L I K E;
NLike           : Not L I K E;
Null            : N U L L;
Is              : I S;
Not             : N O T;
And             : A N D;
Or              : O R;
ParOpen         : '(';
ParClose        : ')';
Identifier      : (VarName '.')* VarName;
OptIdentifier   : '?' Identifier;
JpqlIdentifier  : ':' VarName '[' Type ']';
Whitespace      : [ \t\r\n]+ -> channel(HIDDEN);

// a fragment rule can't be used inside parser rules, only in lexer rules
fragment VarName    : Alpha AlphaNum*;
fragment CForType   : '~' VarName;
fragment Type       : CForType | Types;
fragment Types      : String | Str | Float | Double | Dbl | Long | Lng | Integer | Int | Boolean | Bool | DateTime;
fragment String     : S T R I N G;
fragment Str        : S T R;
fragment Float      : F L O A T;
fragment Double     : D O U B L E;
fragment Dbl        : D B L;
fragment Long       : L O N G;
fragment Lng        : L N G;
fragment Integer    : I N T E G E R;
fragment Int        : I N T;
fragment Boolean    : B O O L E A N;
fragment Bool       : B O O L;
fragment DateTime   : D A T E T I M E;
fragment ST         : '<';
fragment GT         : '>';
fragment SEQ        : '<=';
fragment GEQ        : '>=';
fragment EQ         : '=';
fragment EQAlt      : '==';
fragment NEQ        : '<>';
fragment NEQAlt     : '!=';
fragment UCaseAlpha : 'A'..'Z'; 
fragment LCaseAlpha : 'a'..'z';
fragment Alpha      : LCaseAlpha | UCaseAlpha;
fragment Num        : '0'..'9';
fragment AlphaNum   : 'a'..'z' | 'A' .. 'Z' | '-' | '_' | '0'..'9';

fragment A : [aA]; // match either an 'a' or 'A'
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];