package Sintactico;

import AST.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextArea;
import Lexico.Lexico;
import Lexico.Token;
import Lexico.TokenTipo;
import java.awt.Color;
import java.awt.Font;

public class Sintactico {
	private JTextArea salida;
	private JTextArea errores;
        
	private Lexico analex;
	private Token token;
	private Token err;

	// hash table for operator precedence levels
	private final static Map<TokenTipo, Integer> op_jerarquia;
	
	private ArrayList <Declaraciones3> declaraciones; //declarations symbol table
	private ArrayList <Identificadores> identificadores; //identifiers symbol table
	private ArrayList <Asignar> asignaciones; //assigns symbol table
	private ArrayList <Exp> condiciones; //conditions symbol table
	
	private int errors;
	
	static {
		op_jerarquia = new HashMap<TokenTipo, Integer>();
		op_jerarquia.put(TokenTipo.AND, 10);
		op_jerarquia.put(TokenTipo.OR, 10);
		op_jerarquia.put(TokenTipo.MENORQ, 20);
		op_jerarquia.put(TokenTipo.MAYORQ, 20);
		op_jerarquia.put(TokenTipo.MENOR_EQ, 20);
		op_jerarquia.put(TokenTipo.MAYOR_EQ, 20);
		op_jerarquia.put(TokenTipo.EQ, 20);
		op_jerarquia.put(TokenTipo.NEQ, 20);
		op_jerarquia.put(TokenTipo.MAS, 30);
		op_jerarquia.put(TokenTipo.MENOS, 30);
		op_jerarquia.put(TokenTipo.POR, 40);
		op_jerarquia.put(TokenTipo.DIV, 40);
		op_jerarquia.put(TokenTipo.MOD, 40);
		op_jerarquia.put(TokenTipo.CORCH_IN, 50);
	}

	public Sintactico(FileReader file) throws IOException {
		this.analex = new Lexico(file);
		this.token = analex.getToken();
		this.declaraciones = new ArrayList<Declaraciones3>();
		this.identificadores = new ArrayList<Identificadores>();
		this.asignaciones = new ArrayList<Asignar>();
		this.condiciones = new ArrayList<Exp>();
	}

	//Verifica el Token actual y obtiene el siguiente
	private boolean Lee(TokenTipo type) throws IOException {
		if (token.getType() == type) {
			token = analex.getToken();
			return true;
		} else {
			error(type);
			return false;
		}
	}

	//Reporta un error
	private void error(TokenTipo type) {
		//Reporta un error de token 
		if (token == err){
			return;
                }
                
		errores.append(" ERROR → " + token.getAlias(token.getType()));
		errores.append(" en la línea " + token.getLineNumber() + ", columna " + token.getColumnNumber());
		errores.append(" → Esperado " + token.getAlias(type) + "\n");
				
		err = token; 
		errors++; 
	}

	//Omite los tokens hasta encontrar un error 
	private void skipTo(TokenTipo... follow) throws IOException {
		while (token.getType() != TokenTipo.EOF) {
			for (TokenTipo skip : follow) {
				if (token.getType() == skip)
					return;
			}
			token = analex.getToken();
		}
	}

	//Cantidad de errores de sintaxis 
	public int getErrors() {
		return errors;
	}
        
        //Devolver ArrayList
	public ArrayList <Declaraciones3> getDeclaraciones() {
		return declaraciones;
	}
	public ArrayList <Identificadores> getIdentificadores() {
		return identificadores;
	}
	public ArrayList <Asignar> getAsignaciones() {
		return asignaciones;
	}
	public ArrayList <Exp> getCondiciones() {
		return condiciones;
	}

	//Estructura del Programa: int main( ) { Declaraciones y Sentencias }
	public Program AnalisisSintactico(JTextArea console, JTextArea errors) throws IOException {
		this.salida = console;
                this.errores = errors;
		
		Lee(TokenTipo.INT);
		Lee(TokenTipo.MAIN);
		Lee(TokenTipo.PAR_IN);
		Lee(TokenTipo.PAR_FIN);
		Lee(TokenTipo.LLAVE_IN);

		Declaraciones declaraciones = estrucDeclaracionesTPD();
		Sentencia statementList = estrucSentencia();

		Lee(TokenTipo.LLAVE_FIN);
		Lee(TokenTipo.EOF);
		return new Program(statementList, declaraciones);
	}

	//Estructura de Declaraciones parte 1: Tipo de dato
	private Declaraciones estrucDeclaracionesTPD() throws IOException{
		Declaraciones declaraciones = new Declaraciones();

		while(token.getType() == TokenTipo.INT || token.getType() == TokenTipo.FLOAT
				|| token.getType() == TokenTipo.BOOLEAN || token.getType() == TokenTipo.CHAR)
			declaraciones.addElement(estrucDeclaracionesID());

		return declaraciones;
	}

	//Estructura de Declaraciones part 2: Identificador y punto y coma 
	private Declaraciones2 estrucDeclaracionesID() throws IOException{
            
		Declaraciones2 declaraciones2 = new Declaraciones2();
		Declaraciones3 varDecl = anaNuevoID();
                
		declaraciones2.addElement(varDecl);
		getDeclaraciones().add(varDecl);

		//Verifica si hay más de un identificador 
		while (token.getType() == TokenTipo.COMA) {
			Lee(TokenTipo.COMA);
			Declaraciones3 nuevaVariable = new Declaraciones3(varDecl.getType(), anaID());
			declaraciones2.addElement(nuevaVariable);
			getDeclaraciones().add(nuevaVariable);
		}
		Lee(TokenTipo.PYC);

		return declaraciones2;
	}

	//Estructura de Declaraciones parte 3
	private Declaraciones3 anaNuevoID() throws IOException {
		Tipo tipo = anaTipo();
		Identificadores id = anaID();
		return new Declaraciones3(tipo, id);
	}

    /*
     * Tipo ::= int | int '['integer']' | float | float'['integer']' | boolean | boolean'['integer']' | char | char'['integer']'
     * int (IntegerType)
     * int [integer] (IntArrayType)
     * float (FloatType)
     * float[integer] (FloatArrayType)
     * boolean (BooleanType)
     * boolean[integer] (BooleanArrayType)
     */
	private Tipo anaTipo() throws IOException {
            //Verifica si es un arreglo
            switch (token.getType()) {
		case INT:
                        Lee(TokenTipo.INT);

                        //Verifica si es un arreglo
                        if (token.getType() == TokenTipo.CORCH_IN) {
                                Lee(TokenTipo.CORCH_IN);

                                //Verifica el tamaño del arreglo
                                if(Lee(TokenTipo.INT_CONST)){
                                        if (token.getType() == TokenTipo.CORCH_FIN) {
                                                Lee(TokenTipo.CORCH_FIN);
                                                return new IntegerArrayType();
                                        }
                                }

                                // invalid integer tipo declaration
                                Lee(TokenTipo.TIPO);
                                return null;
                        }
                        return new IntegerType();

		case FLOAT:
			Lee(TokenTipo.FLOAT);

			//Verifica si es un arreglo
			if (token.getType() == TokenTipo.CORCH_IN) {
				Lee(TokenTipo.CORCH_IN);

				//Verifica el tamaño del arreglo
				if(Lee(TokenTipo.INT_CONST)){
					if (token.getType() == TokenTipo.CORCH_FIN) {
						Lee(TokenTipo.CORCH_FIN);
						return new FloatArrayType();
					}
				}

				// invalid integer tipo declaration
				Lee(TokenTipo.TIPO);
				return null;
			}
			return new FloatType();

		case BOOLEAN:
			Lee(TokenTipo.BOOLEAN);

			//Verifica si es un arreglo
			if (token.getType() == TokenTipo.CORCH_IN) {
				Lee(TokenTipo.CORCH_IN);

				//Verifica el tamaño del arreglo
				if(Lee(TokenTipo.INT_CONST)){
					if (token.getType() == TokenTipo.CORCH_FIN) {
						Lee(TokenTipo.CORCH_FIN);
						return new BooleanArrayType();
					}
				}

				// invalid integer tipo declaration
				Lee(TokenTipo.TIPO);
				return null;
			}
			return new BooleanType();

		case CHAR:
			Lee(TokenTipo.CHAR);

			//Verifica si es un arreglo
			if (token.getType() == TokenTipo.CORCH_IN) {
				Lee(TokenTipo.CORCH_IN);

				//Verifica el tamaño del arreglo
				if(Lee(TokenTipo.INT_CONST)){
					if (token.getType() == TokenTipo.CORCH_FIN) {
						Lee(TokenTipo.CORCH_FIN);
						return new CharArrayType();
					}
				}

				// invalid integer tipo declaration
				Lee(TokenTipo.TIPO);
				return null;
			}
			return new CharType();

		default:
			//Tipo Desconocido
			Lee(TokenTipo.TIPO);
			return null;

		}
	}

	//Los identificadores son letras o digitos 
	private Identificadores anaID() throws IOException {
		Identificadores identificadores = null;

		//Guarda el valor de ID si el token es ID
		if (token.getType() == TokenTipo.ID)
			identificadores = new Identificadores(token.getAttribute().getIdVal());
		
		Lee(TokenTipo.ID);
		return identificadores;
	}

	//Estructura de una Sentencia 
	private Sentencia estrucSentencia() throws IOException{
		Sentencia sentencia = new Sentencia();
		while (inSentencia())
			sentencia.addElement(anaSentencia());
		return sentencia;
	}

	//Verifica el comienzo de una nueva sentencia 
	private boolean inSentencia() {
		switch(token.getType()){
		case PYC :
		case IF :
		case WHILE :
		case PAR_IN :
		case LLAVE_IN:
		case ID :
			return  true;
		default:
			return false;
		}
	}

	//Estructura de la Sentencia: asignaciones y condiciones 
	private Sentencia2 anaSentencia() throws IOException {

		//Condicion IF: if (Exp) sentencia
		if (token.getType() == TokenTipo.IF) {
			Lee(TokenTipo.IF);

			//Analiza la expresion 
			if (!Lee(TokenTipo.PAR_IN))
				skipTo(TokenTipo.PAR_FIN, TokenTipo.LLAVE_IN, TokenTipo.LLAVE_FIN);

			Exp condExp = anaExp();
			condiciones.add(condExp);

			if (!Lee(TokenTipo.PAR_FIN))
				skipTo(TokenTipo.LLAVE_IN, TokenTipo.PYC, TokenTipo.LLAVE_FIN);

			Sentencia2 ver1;

			//Verificar Sentencia entre llaves
			if (token.getType() == TokenTipo.LLAVE_IN)
				ver1 = anaBloque(); //verificaremos el bloque dentro de las llaves

			else
				ver1 = anaSentencia();

			if (token.getType() == TokenTipo.ELSE){
				if (!Lee(TokenTipo.ELSE))
					skipTo(TokenTipo.LLAVE_IN, TokenTipo.PYC, TokenTipo.LLAVE_FIN);

				Sentencia2 ver2;

				if (token.getType() == TokenTipo.LLAVE_IN)
					ver2 = anaBloque();

				else
                                        // parse false statement
                                        ver2 = anaSentencia();

				return new If(condExp, ver1, ver2);
			}
			return new If(condExp, ver1, null);
		}

		//Condicion WHILE:  while ( Exp ) Sentencia
		if (token.getType() == TokenTipo.WHILE) {
			Lee(TokenTipo.WHILE);

			if (!Lee(TokenTipo.PAR_IN))
				skipTo(TokenTipo.PAR_FIN, TokenTipo.LLAVE_IN, TokenTipo.LLAVE_FIN);

			Exp condExp = anaExp();
			condiciones.add(condExp);

			if (!Lee(TokenTipo.PAR_FIN))
				skipTo(TokenTipo.LLAVE_IN, TokenTipo.PYC, TokenTipo.LLAVE_FIN);

			Sentencia2 ver3;
 
			if (token.getType() == TokenTipo.LLAVE_IN)
				ver3 = anaBloque();

			else
				ver3 = anaSentencia();

			return new While(condExp, ver3);
		}

		// Identificadores 
		if (token.getType() == TokenTipo.ID) {

			Identificadores id = new Identificadores(token.getAttribute().getIdVal());
			identificadores.add(id);
			Lee(TokenTipo.ID);


			//Verifica Asignacion: id = Exp ;
			if (token.getType() == TokenTipo.ASIG) {
				Lee(TokenTipo.ASIG);
				Exp value = anaExp();
				
				Lee(TokenTipo.PYC);

				Asignar asg = new Asignar(id, value);
				asignaciones.add(asg);
				return asg;
			}

			//Verifica la asignacion de un arreglo: id [ Exp ] = Exp ;
			if (token.getType() == TokenTipo.CORCH_IN) {
				Lee(TokenTipo.CORCH_IN);
				Exp index = anaExp();
				
				if(!(index instanceof IntegerLiteral)){
					//Sentencia desconocida
					Lee(TokenTipo.TIPO);
					token = analex.getToken();
					return null;
				}

				if (!Lee(TokenTipo.CORCH_FIN))
					skipTo(TokenTipo.ASIG, TokenTipo.PYC);

				if (!Lee(TokenTipo.ASIG))
					skipTo(TokenTipo.PYC);

				Exp value = anaExp();
				
				Lee(TokenTipo.PYC);
				
				Asignar assign = new Asignar(id, value);
				asignaciones.add(assign);
				return new ArrayAssign(id, index, value);
			}
		}

		// statement tipo unknown
		Lee(TokenTipo.SENTENCIA);
		token = analex.getToken();
		return null;
	}

        //Verifica las sentencias dentro de las llaves de una condicion
	private Bloque anaBloque() throws IOException{
		Lee(TokenTipo.LLAVE_IN);
                
                //Llama recursivamente a anaSentencia() hasta que se cierre la llave
		Sentencia sentencia = new Sentencia();
		while (token.getType() != TokenTipo.LLAVE_FIN && token.getType() != TokenTipo.EOF)
			sentencia.addElement(anaSentencia());

		if (!Lee(TokenTipo.LLAVE_FIN)) 
			skipTo(TokenTipo.LLAVE_FIN, TokenTipo.PYC);

		return new Bloque(sentencia);
	}

	// Jerarquia de la expresion: PrimaryExp | BinopRHS
	private Exp anaExp() throws IOException {
		Exp lhs = anaPrimerExp();
		return anaJerarquia(0, lhs); //Verifica que primero sea una exp y despues un op 
	}

	//Analisa primero INT_CONST | FLOAT_CONST | CHAR_CONST | BOOLEAN_CONST | NEGATIVE | NOT | Identificadores
	//antes de un operador 
	private Exp anaPrimerExp() throws IOException {
		switch (token.getType()) {

		case INT_CONST:
			int intValue = token.getAttribute().getIntVal();
			Lee(TokenTipo.INT_CONST);
			return new IntegerLiteral(intValue);

		case FLOAT_CONST:
			float floatValue = token.getAttribute().getFloatVal();
			Lee(TokenTipo.FLOAT_CONST);
			return new FloatLiteral(floatValue);

		case BOOLEAN_CONST:
			boolean booleanVal = token.getAttribute().getBooleanVal();
			Lee(TokenTipo.BOOLEAN_CONST);
			return new BooleanLiteral(booleanVal);

		case CHAR_CONST:
			char charVal = token.getAttribute().getCharVal();
			Lee(TokenTipo.CHAR_CONST);
			return new CharLiteral(charVal);

		case ID:
			Identificadores id = anaID();
			identificadores.add(id);
			return new IdentifierExp(id.getNombre());

		case NOT:
			Lee(TokenTipo.NOT);
			return new Not(anaExp());

		case NEG:
			Lee(TokenTipo.NEG);
			return new Negative(anaExp());

		case PAR_IN:
			Lee(TokenTipo.PAR_IN);
			Exp exp = anaExp();
			Lee(TokenTipo.PAR_FIN);
			return exp;

		default:
			// unrecognizable expression
			Lee(TokenTipo.EXPRESION);
			token = analex.getToken();
			return null;
		}
	}

	//Analiza las expresiones de acuardo a su jerarquia
	private Exp anaJerarquia(int level, Exp lhs) throws IOException {
		//Analiza las expresiones hasta que llegue un operador de menor jeraquia 
		while (true) {
			//Obtiene el operador precedente 
			Integer val = op_jerarquia.get(token.getType());
			int tokenLevel = (val != null) ? val.intValue() : -1;

			//Si el operador es menor que el anterior o no es un op 
			if (tokenLevel < level)
				return lhs;

			//Guarda el op 
			TokenTipo binop = token.getType();
			Lee(binop);

			Exp rhs = anaPrimerExp(); // parse rhs of exp

			//Obtiene el op precedente)
			val = op_jerarquia.get(token.getType());
			int nextLevel = (val != null) ? val.intValue() : -1;

			//Si el siguiente op es de un nivel mayor se realiza un llamado recursivo
			if (tokenLevel < nextLevel)
				rhs = anaJerarquia(tokenLevel + 1, rhs);

			//Crea AST  abstract syntax tree por expresion
			switch (binop) {
			case AND:
				lhs = new And(lhs, rhs);
				break;
			case OR:
				lhs = new Or(lhs, rhs);
				break;
			case EQ:
				lhs = new Equal(lhs, rhs);
				break;
			case NEQ:
				lhs = new NotEqual(lhs, rhs);
				break;
			case MENORQ:
				lhs = new LessThan(lhs, rhs);
				break;
			case MAYORQ:
				lhs = new MoreThan(lhs, rhs);
				break;
			case MENOR_EQ:
				lhs = new LessThanEqual(lhs, rhs);
				break;
			case MAYOR_EQ:
				lhs = new MoreThanEqual(lhs, rhs);
				break;
			case MAS:
				lhs = new Plus(lhs, rhs);
				break;
			case MENOS:
				lhs = new Minus(lhs, rhs);
				break;
			case POR:
				lhs = new Times(lhs, rhs);
				break;
			case DIV:
				lhs = new Divide(lhs, rhs);
				break;
			case MOD:
				lhs = new Modules(lhs, rhs);
				break;
			case CORCH_IN:
				lhs = new ArrayLookup(lhs, rhs);
				Lee(TokenTipo.CORCH_FIN);
				break;
			default:
				Lee(TokenTipo.OPERADOR);
				break;
			}
		}
	}

}