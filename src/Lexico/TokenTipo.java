package Lexico;

public enum TokenTipo {
	//Tipo de Datos
        ID, // [a-zA-Z][a-zA-Z0-9_]*
	INT_CONST, // [0-9]+
	FLOAT_CONST, //[0-9]+.[0-9]+
	CHAR_CONST, //'ASCII Char'
	BOOLEAN_CONST,
	EOF, //final de entrada
	DESCONOCIDO, // character/token could not be processed
	
	// Operadores binarios
	AND, // &&
	OR, // ||
	EQ, // ==
	NEQ, // !=
	MENORQ, // <
	MAYORQ, // >
	MENOR_EQ, // <=
	MAYOR_EQ, // >=
	MAS, // +
	MENOS, // -
	POR, // *
	DIV, // /
	MOD, // %

	// Palabras reservadas
	MAIN, // main - relegate as ID
	INT, // int
	CHAR, // char
	FLOAT, // float
	BOOLEAN, // boolean
	IF, // if
	ELSE, // else
	WHILE, // while

	//simbolos especiales
	PAR_IN, // (
	PAR_FIN, // )
	CORCH_IN, // [
	CORCH_FIN, // ]
	LLAVE_IN, // {
	LLAVE_FIN, // }
	PYC, // ;
	COMA, // ,
	ASIG, // =
	NEG, // -
	NOT, // !

	//reporte de errores
	SENTENCIA,
	EXPRESION,
	OPERADOR,
	TIPO
}