grammar Rql;

/*
 * Parser Rules start with lower-case characters.
 */
eval            returns [String r]
                : op=orExpression                           {$r = $op.text;}
                ;

orExpression    returns [String r]
                : op=andExpression                          {$r = $op.text;}
                (an=Or                                      {$r += " " + $an.text + " ";}
                ae=andExpression                            {$r += $ae.text;}
                )*;

andExpression   returns [String r]
                : op=atomExpression                         {$r = $op.text;}
                (an=And                                     {$r += " " + $an.text + " ";}
                ae=atomExpression                           {$r += $ae.text;}
                )*;

atomExpression  returns [String r]
                : op=term                                   {$r = $op.text;}
                | '(' or=orExpression                       {$r = $or.text;}
                ')'; 

term            returns [String r]
                : id=identifier                             {$r = $id.text;}
                op=Operator                                 {$r += $op.text;}
                jd=jpqlIdentifier                           {$r += $jd.text;}
                ;

operator        returns [String r]
                : op=Operator                               {$r = " " + $op.text + " ";}
                ;

identifier      returns [String r]
                : op=Identifier                             {$r = $op.text;}
                ;

jpqlIdentifier  returns [String r]
                : op=JpqlIdentifier                         {$r = $op.text;}
                ;
                
/*
 * Lexer Rules start with upper-case characters.
 */
Operator        : ST | SEQ | GT | GEQ | EQ | NEQ | NullOp | NotNullOp;
NullOp          : Is Null;
NotNullOp       : Is Not Null;
And             : A N D;
Or              : O R;
Identifier  : Alpha AlphaNum*;
JpqlIdentifier      : ':' Alpha AlphaNum*;
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