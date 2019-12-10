package Semantico;

public enum Error {
	//Error: Variable no Declarada
	SIN_DECLARAR,
	
	//Error: Multiples delcaraciones
	REPETIDO,
	
	//Error: Conversiones
	FLOAT_INT, 
	FLOAT_CHAR, 
	FLOAT_BOOLEAN, 
	BOOLEAN_INT, 
	BOOLEAN_FLOAT, 
	BOOLEAN_CHAR, 
	INT_BOOLEAN, 
	INT_CHAR, 
	CHAR_FLOAT, 
	CHAR_BOOLEAN, 
	
	VAR_ARR, 
	ARR_VAR, 
	COND_INV 
}
