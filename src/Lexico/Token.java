package Lexico;

import java.util.HashMap;

public class Token {
	private TokenTipo tipo; //Tipo de Token
	private TokenAtributo atributo; //Atributo del Token
	private int numLinea; //linea del token
	private int numColumna; //columna del token
	private static final HashMap<TokenTipo,String> tokenAlias = new HashMap<>();

	public Token(TokenTipo type, TokenAtributo attribute, int lineNumber, int columnNumber){
		this.tipo = type;
		this.atributo = attribute;
		this.numLinea = lineNumber;
		this.numColumna = columnNumber;
	}

	public TokenTipo getType(){
		return tipo;
	}
	
	public TokenAtributo getAttribute(){
		return atributo;
	}

	public int getLineNumber(){
		return numLinea;
	}

	public int getColumnNumber(){
		return numColumna;
	}
	
	public String getAlias(TokenTipo tokenType) {
                
                //
                tokenAlias.put(TokenTipo.EOF, "FIN DEL ARCHIVO");
		tokenAlias.put(TokenTipo.DESCONOCIDO, "DESCONOCIDO");

                //Tipo de Datos
		tokenAlias.put(TokenTipo.ID, "IDENTIFICADOR");
		tokenAlias.put(TokenTipo.INT_CONST, "TIPO DE DATO ENTERO");
		tokenAlias.put(TokenTipo.FLOAT_CONST, "TIPO DE DATO FLOTANTE");
		tokenAlias.put(TokenTipo.CHAR_CONST, "TIPO DE DATO CARÁCTER");
		tokenAlias.put(TokenTipo.BOOLEAN_CONST, "TIPO DE DATO BOOLEANA");
		
		// Operadores
		tokenAlias.put(TokenTipo.AND, "OPERADOR LÓGICO AND");
		tokenAlias.put(TokenTipo.OR, "OPERADOR LÓGICO OR");
		tokenAlias.put(TokenTipo.EQ, "OPERADOR LÓGICO IGUAL");
		tokenAlias.put(TokenTipo.NEQ, "OPERADOR LÓGICO DIFERENTE A");
		tokenAlias.put(TokenTipo.MENORQ, "OPERADOR LÓGICO MENOS QUE");
		tokenAlias.put(TokenTipo.MAYORQ, "OPERADOR LÓGICO MAYOR QUE");
		tokenAlias.put(TokenTipo.MENOR_EQ, "OPERADOR LÓGICO MENOS O IGUAL A");
		tokenAlias.put(TokenTipo.MAYOR_EQ, "OPERADOR LÓGICO Mayor O IGUAL A");
		tokenAlias.put(TokenTipo.MAS, "OPERADOR ARITMÉTICO SUMA");
		tokenAlias.put(TokenTipo.MENOS, "OPERADOR ARITMÉTICO RESTA");
		tokenAlias.put(TokenTipo.POR, "OPERADOR ARITMÉTICO MULTIPLICACIÓN");
		tokenAlias.put(TokenTipo.DIV, "OPERADOR ARITMÉTICO DIVISIÓN");
		tokenAlias.put(TokenTipo.MOD, "OPERADOR ARITMÉTICO MÓDULO");
		
		// Palabras Reservadas
		tokenAlias.put(TokenTipo.MAIN, "PALABRA RESERVADA MAIN");
		tokenAlias.put(TokenTipo.INT, "PALABRA RESERVADA INT");
		tokenAlias.put(TokenTipo.CHAR, "PALABRA RESERVADA CHAR");
		tokenAlias.put(TokenTipo.FLOAT, "PALABRA RESERVADA FLOAT");
		tokenAlias.put(TokenTipo.BOOLEAN, "PALABRA RESERVADA BOOLEAN");
		tokenAlias.put(TokenTipo.IF, "PALABRA RESERVADA IF");
		tokenAlias.put(TokenTipo.ELSE, "PALABRA RESERVADA ELSE");
		tokenAlias.put(TokenTipo.WHILE, "PALABRA RESERVADA WHILE");
		
		// Simbolos Especiales
		tokenAlias.put(TokenTipo.PAR_IN, "SIMBOLO ESPECIAL PARÉNTESIS");
		tokenAlias.put(TokenTipo.PAR_FIN, "SIMBOLO ESPECIAL PARÉNTESIS");
		tokenAlias.put(TokenTipo.CORCH_IN, "SIMBOLO ESPECIAL CORCHETE");
		tokenAlias.put(TokenTipo.CORCH_FIN, "SIMBOLO ESPECIAL CORCHETE");
		tokenAlias.put(TokenTipo.LLAVE_IN, "SIMBOLO ESPECIAL LLAVE");
		tokenAlias.put(TokenTipo.LLAVE_FIN, "SIMBOLO ESPECIAL LLAVE");
		tokenAlias.put(TokenTipo.PYC, "SIMBOLO ESPECIAL PUNTO Y COMA");
		tokenAlias.put(TokenTipo.COMA, "SIMBOLO ESPECIAL COMA");
		tokenAlias.put(TokenTipo.ASIG, "OPERADOR DE ASIGNACIÓN");
		tokenAlias.put(TokenTipo.NEG, "NEGATIVO");
		tokenAlias.put(TokenTipo.NOT, "NEGADO LÓGICO \n  → Valor: '!'");
		
		// Erroes
		tokenAlias.put(TokenTipo.SENTENCIA, "SENTENCIA");
		tokenAlias.put(TokenTipo.EXPRESION, "EXPRESIÓN");
		tokenAlias.put(TokenTipo.OPERADOR, "OPERADOR");
		tokenAlias.put(TokenTipo.TIPO, "TIPO");
		
		return tokenAlias.get(tokenType);
	}
}