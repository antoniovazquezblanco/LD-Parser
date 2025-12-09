grammar LDScript;

@header{ package io.ldparser; }

options {
    caseInsensitive = false;
}

// Lexer
COMMENTS : '/*' .*? '*/' -> skip;
WHITESPACES : [ \t\r\n]+ -> skip;
// https://users.informatik.haw-hamburg.de/~krabat/FH-Labor/gnupro/5_GNUPro_Utilities/c_Using_LD/ldLinker_scripts.html#Symbol_names
SYMBOL_NAME_UNQUOTED : [a-zA-Z_.][a-zA-Z0-9_.\-]*;
SYMBOL_NAME_QUOTED : '"' (~["\\] | '\\' .)* '"';
// https://users.informatik.haw-hamburg.de/~krabat/FH-Labor/gnupro/5_GNUPro_Utilities/c_Using_LD/ldLinker_scripts.html#Constants
CONSTANT_OCT : '0'[0-7]+;
CONSTANT_DEC : [1-9][0-9]*[KM]?;
CONSTANT_HEX : '0x'[0-9a-fA-F]+;

// Parser
script : command* EOF;

command : commandProvide ';';

commandProvide : 'PROVIDE' '(' symbolName '=' expression ')';

expression : constant
           ;

constant : CONSTANT_OCT
         | CONSTANT_DEC
         | CONSTANT_HEX
         ;

symbolName : SYMBOL_NAME_UNQUOTED
           | SYMBOL_NAME_QUOTED
           ;
