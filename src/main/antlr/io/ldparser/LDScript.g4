grammar LDScript;

options {
    caseInsensitive = false;
}

// Lexer
COMMENTS : '/*' .*? '*/' -> skip;
WHITESPACES : [ \t\r\n]+ -> skip;
SYMBOL_NAME : [a-zA-Z_][a-zA-Z0-9_]*;
IMMEDIATE_STRING : '"' (~["\\] | '\\' .)* '"';
IMMEDIATE_NUMBER_DEC : [0-9]+;
IMMEDIATE_NUMBER_HEX : '0x'[0-9a-fA-F]+;

// Parser
script : command* EOF;

command : command_provide ';';

command_provide : 'PROVIDE' '(' SYMBOL_NAME '=' expression ')';

expression : '.'
           | immediate_number
           | SYMBOL_NAME
           | IMMEDIATE_STRING
           ;

immediate_number : IMMEDIATE_NUMBER_DEC
                 | IMMEDIATE_NUMBER_HEX
                 ;
