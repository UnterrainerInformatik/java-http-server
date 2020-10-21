grammar Rql;

/*
 * Parser Rules start with lower-case characters.
 */
eval            : orExpression ;

orExpression    : andExpression (or andExpression)* ;

andExpression   : atomExpression (and atomExpression)* ;

atomExpression  : atomTerm | (parOpen orExpression parClose) ;

atomTerm        : optTerm | term ;

and             : And ;

or              : Or ;

parOpen         : ParOpen ;

parClose        : ParClose ;

optTerm         : OptIdentifier Operator JpqlIdentifier ;
term            : Identifier Operator JpqlIdentifier ;
                
/*
 * Lexer Rules start with upper-case characters.
 */
Operator        : ST | SEQ | GT | GEQ | EQ | NEQ | Like | NLike | NullOp | NotNullOp;
Like            : L I K E;
NLike           : Not L I K E;
NullOp          : Is Null;
NotNullOp       : Is Not Null;
And             : A N D;
Or              : O R;
ParOpen         : '(';
ParClose        : ')';
Identifier      : (Alpha AlphaNum* '.')* Alpha AlphaNum*;
OptIdentifier   : '?' Identifier;
JpqlIdentifier  : ':' Alpha AlphaNum*;
Whitespace      : [ \t\r\n]+ -> channel(HIDDEN);

// a fragment rule can't be used inside parser rules, only in lexer rules
fragment ST          : '<';
fragment GT          : '>';
fragment SEQ        : '<=';
fragment GEQ        : '>=';
fragment EQ         : '=';
fragment NEQ        : '<>';
fragment Null       : N U L L;
fragment Is         : I S;
fragment Not        : N O T;
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