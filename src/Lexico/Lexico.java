package Lexico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lexico {
	private BufferedReader stream; //input stream reader
	private Token nextToken; 
	private int nextChar; 
	private int numLinea = 1; //Numero de linea actual
	private int numColumna = 1; //Numero de columna actual

	private final static Map<String, TokenTipo> preservadas; //diccionario de palabras reservadas
	private final static Map<Character, TokenTipo> simbolos; //diccionario de simbolos especiales
	private final static Map<String, TokenTipo> operadores; //diccionario de operadores
	
	private int err; //variable para el numero de err

	static {
		preservadas = new HashMap<String, TokenTipo>();
		preservadas.put("int", TokenTipo.INT);
		preservadas.put("float", TokenTipo.FLOAT);
		preservadas.put("char", TokenTipo.CHAR);
		preservadas.put("boolean", TokenTipo.BOOLEAN);
		preservadas.put("if", TokenTipo.IF);
		preservadas.put("else", TokenTipo.ELSE);
		preservadas.put("while", TokenTipo.WHILE);
		preservadas.put("main", TokenTipo.MAIN);

		simbolos = new HashMap<Character, TokenTipo>();
		simbolos.put('(', TokenTipo.PAR_IN);
		simbolos.put(')', TokenTipo.PAR_FIN);
		simbolos.put('[', TokenTipo.CORCH_IN);
		simbolos.put(']', TokenTipo.CORCH_FIN);
		simbolos.put('{', TokenTipo.LLAVE_IN);
		simbolos.put('}', TokenTipo.LLAVE_FIN);
		simbolos.put(';', TokenTipo.PYC);
		simbolos.put(',', TokenTipo.COMA);
		simbolos.put('=', TokenTipo.ASIG);
		simbolos.put('-', TokenTipo.NEG);
		simbolos.put('!', TokenTipo.NOT);

		operadores = new HashMap<String, TokenTipo>();
		operadores.put("&&", TokenTipo.AND);
		operadores.put("||", TokenTipo.OR);
		operadores.put("==", TokenTipo.EQ);
		operadores.put("!=", TokenTipo.NEQ);
		operadores.put("<", TokenTipo.MENORQ);
		operadores.put(">", TokenTipo.MAYORQ);
		operadores.put("<=", TokenTipo.MENOR_EQ);
		operadores.put(">=", TokenTipo.MAYOR_EQ);
		operadores.put("+", TokenTipo.MAS);
		operadores.put("-", TokenTipo.MENOS);
		operadores.put("*", TokenTipo.POR);
		operadores.put("/", TokenTipo.DIV);
		operadores.put("%", TokenTipo.MOD);
	}
         
	public Lexico(FileReader file) throws FileNotFoundException {
            this.stream = new BufferedReader(file);
            nextChar = getChar();
	}
	
	public int getErr() {
            return err;
	}

	// Entrada/salida
	private int getChar() {
            try {
                return stream.read(); //Retorna un entero entre 0 y 255 si encuentra texto, de lo contrario regresa -1
            } catch (IOException e) {
                System.err.print(e.getMessage());
                System.err.println("IOException ocurrió un error en el lexer::getChar()");
                return -1; 
            }
	}

	//Detecta los saltos de linea y espacios en blanco
	private boolean skipNewline() {
		if (nextChar == '\n') {
			numLinea++;
			numColumna = 1;
			nextChar = getChar();
			return true;
		}
		if (nextChar == '\r') {
			numLinea++;
			numColumna = 1;
			nextChar = getChar();

			// skip over next char if '\n'
			if (nextChar == '\n')
				nextChar = getChar();
			return true;
		}
		// si una nueva linea no se detecta..
		return false;
	}

	// Devuelve el siguiente token sin consumirlo.
	public Token peek() throws IOException {
		// avanzar token solo si ha sido restablecido por getToken ()
		if (nextToken == null)
			nextToken = getToken();

		return nextToken;
	}

	//Devuelve el siguiente token en el flujo de entrada (EOF señala el final de la entrada)
	public Token getToken() throws IOException {
		//Verifica si se llamó el método peek ()
		if (nextToken != null) {
			Token token = nextToken;
			nextToken = null; //permite a peek() llamar al siguiente token
			return token;
		}

		//Omite el carácter de espacio en blanco
		while (Character.isWhitespace(nextChar)) {
			//verifica si el espacio en blanco es una nueva linea
			if (!skipNewline()) {
				numColumna++;
				nextChar = getChar();
			}

			// columna de desplazamiento para caracteres de tabulación
			if (nextChar == '\t')
				numColumna += 3;
		}

		//Identificadores o palabras reservadas ([a-zA-Z][a-zA-Z0-9_]*)
		if (Character.isLetter(nextChar)) {
			//crear un nuevo idVal comenzando con el primer carácter del identificador
			String current = Character.toString((char) nextChar);
			numColumna++;
			nextChar = getChar();

			//incluye la secuencia restante de caracteres que son letras, dígitos o _
			while (Character.isLetterOrDigit(nextChar)) {
				current += (char) nextChar;
				numColumna++;
				nextChar = getChar();
			}

			//Verifica si es una palabra reservadas
			TokenTipo type = preservadas.get(current);
			if (type != null)
                            return new Token(type, new TokenAtributo(), numLinea, numColumna - current.length());
                        //Verifica si es un booleano 
			if(current.equals("true")) 
                            return new Token(TokenTipo.BOOLEAN_CONST, new TokenAtributo(true), numLinea, numColumna - current.length());
			else if(current.equals("false"))
                            return new Token(TokenTipo.BOOLEAN_CONST, new TokenAtributo(false), numLinea, numColumna - current.length());
			//Lo reconoce como un identificador
			return new Token(TokenTipo.ID, new TokenAtributo(current), numLinea, numColumna - current.length());
		}

		// Entero ([0-9]+) o flotante ([0-9]+.[0-9]+)
		if (Character.isDigit(nextChar)) {

			// crea cadena de representacion de un número
			String numString = Character.toString((char) nextChar);
			numColumna++;
			nextChar = getChar();

			// concatena secuencia restante de digitos
			while (Character.isDigit(nextChar)) {
				numString += (char) nextChar;
				numColumna++;
				nextChar = getChar();
			}
			
			if(nextChar == '.'){
				//stream.mark(0); si 
				nextChar = getChar();
				numColumna++;
				
				if(Character.isDigit(nextChar)){
					numString += '.';
					// concatena secuencia restante de digitos
					while (Character.isDigit(nextChar)) {
						numString += (char) nextChar;
						numColumna++;
						nextChar = getChar();
					}
					
					return new Token(TokenTipo.FLOAT_CONST, new TokenAtributo(Float.parseFloat(numString)), numLinea, numColumna - numString.length());
				}
				while(!Character.isWhitespace(nextChar)){
					numColumna++;
					numString += nextChar;
					nextChar = getChar();
				}
				//Error
				return new Token(TokenTipo.DESCONOCIDO, new TokenAtributo(), numLinea, numColumna - numString.length() + 1);
			}

			// returna que es un entero
			return new Token(TokenTipo.INT_CONST, new TokenAtributo(Integer.parseInt(numString)), numLinea, numColumna - numString.length());
		}
                
                //Verifica la entra de un char
		if(nextChar == '\''){
			nextChar = getChar();
			numColumna++;
			if(Character.isAlphabetic(nextChar)){
				char current = (char) nextChar;
				stream.mark(0);
				nextChar = getChar();
				numColumna++;

				if(nextChar == '\''){
					nextChar = getChar();
					numColumna++;
					return new Token(TokenTipo.CHAR_CONST, new TokenAtributo(current), numLinea, numColumna - 1);
				}
				stream.reset();
			}
                        //Error
			return new Token(TokenTipo.DESCONOCIDO, new TokenAtributo(), numLinea, numColumna - 1);
		}

		// alcanza el EOF 
		if (nextChar == -1)
			return new Token(TokenTipo.EOF, new TokenAtributo(), numLinea, numColumna);

		//Verifica operadores binarios
		switch (nextChar) {
		
		case '&':
			numColumna++;
			nextChar = getChar();

			// verifica si el siguiente char es '&' para hacer '&&' operador and
			if (nextChar == '&') {
				nextChar = getChar();
				return new Token(TokenTipo.AND, new TokenAtributo(), numLinea, numColumna - 2);
			} else
				return new Token(TokenTipo.DESCONOCIDO, new TokenAtributo(), numLinea, numColumna - 1);

		case '|':
			numColumna++;
			nextChar = getChar();

			//verifica si el siguiente char es  '|' para hacer '||' operador logico or
			if (nextChar == '|') {
				nextChar = getChar();
				return new Token(TokenTipo.OR, new TokenAtributo(), numLinea, numColumna - 2);
			} else
				return new Token(TokenTipo.DESCONOCIDO, new TokenAtributo(), numLinea, numColumna - 1);

		case '=':
			numColumna++;
			nextChar = getChar();

			// verifica si el siguiente char es '=' para hacer '==' operador logico de comparacion
			if (nextChar == '=') {
				nextChar = getChar();
				return new Token(TokenTipo.EQ, new TokenAtributo(), numLinea, numColumna - 2);
			}
			else 
				return new Token(TokenTipo.ASIG, new TokenAtributo(), numLinea, numColumna - 1);

		case '!':
			numColumna++;
			nextChar = getChar();

			// verifica si el siguiente char es '!' para hacer '!=' operador binario diferente de
			if (nextChar == '=') {
				nextChar = getChar();
				return new Token(TokenTipo.NEQ, new TokenAtributo(), numLinea, numColumna - 2);
			}
			else 
				return new Token(TokenTipo.NOT, new TokenAtributo(), numLinea, numColumna - 1);

		case '<':
			numColumna++;
			nextChar = getChar();

			// verifica si el siguiente char es  '<' para hacer '<=' operador binario menor igual que
			if (nextChar == '=') {
				nextChar = getChar();
				return new Token(TokenTipo.MENOR_EQ, new TokenAtributo(), numLinea, numColumna - 2);
			} else
				return new Token(TokenTipo.MENORQ, new TokenAtributo(), numLinea, numColumna - 1);

		case '>':
			numColumna++;
			nextChar = getChar();

			// verifica si el siguiente char es  '<' para hacer '<=' operador binario mayor igual que
			if (nextChar == '=') {
				nextChar = getChar();
				return new Token(TokenTipo.MAYOR_EQ, new TokenAtributo(), numLinea, numColumna - 2);
			} else
				return new Token(TokenTipo.MAYORQ, new TokenAtributo(), numLinea, numColumna - 1);

		case '+': //MAS
			numColumna++;
			nextChar = getChar();
			return new Token(TokenTipo.MAS, new TokenAtributo(), numLinea, numColumna - 1);

		case '-'://MENOS
			numColumna++;
			nextChar = getChar();
			return new Token(TokenTipo.MENOS, new TokenAtributo(), numLinea, numColumna - 1);

		case '*'://POR
			numColumna++;
			nextChar = getChar();
			return new Token(TokenTipo.POR, new TokenAtributo(), numLinea, numColumna - 1);

		case '/'://ENTRE
			numColumna++;
			nextChar = getChar();
			return new Token(TokenTipo.DIV, new TokenAtributo(), numLinea, numColumna - 1);

		case '%'://MOD
			numColumna++;
			nextChar = getChar();
			return new Token(TokenTipo.MOD, new TokenAtributo(), numLinea, numColumna - 1);
		}

		// verifica si hay simbolos especiales
		TokenTipo type = simbolos.get((char) nextChar);
		numColumna++;
		nextChar = getChar();

		//token de simbolo especial encontrado
		if (type != null)
			return new Token(type, new TokenAtributo(), numLinea, numColumna - 1);

		// si no reconoce el tipo es desconocido
		return new Token(TokenTipo.DESCONOCIDO, new TokenAtributo(), numLinea, numColumna - 1);
	}
}
