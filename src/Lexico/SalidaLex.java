package Lexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextArea;

public class SalidaLex {
    public void execLexer(File file, JTextArea salida) throws IOException {
        if (file == null)
            salida.append("Dirección del archivo no recibida\n");
        else {
            FileReader FR = null;
            // intenta abrir archivo
            try {
                    FR = new FileReader(file.toString());
            } catch (FileNotFoundException e) {
                    salida.append(file.toString() + " no fue encontrado!\n");
            }

            // crea objeto del analizador lexico
            Lexico lexer = new Lexico(FR);

            // Analisis Lexico
            salida.append("  --------------------------------------ANALISIS LEXICO--------------------------------------\n\n");
            long startTime = System.currentTimeMillis();
            int numTokens = 0;
            Token token;
            do {
                token = lexer.getToken();
                numTokens++;
                
                //Salida al encontrar errores
                if(token.getType() == TokenTipo.DESCONOCIDO){
                    //imprime el tipo de token y la posición
                    salida.append("  Tipo: " + token.getAlias(token.getType()));
                    salida.append("  \n  → Posición: (línea: " + token.getLineNumber() + ", columna: " + token.getColumnNumber() + ")");
                    salida.append("\n\n");
                    continue;
                }
                
                //Salida al encontrar fin del archivo
                if(token.getType() == TokenTipo.EOF){
                    //imprime el tipo de token y la posición
//                    salida.append("  " + token.getAlias(token.getType()));
//                    salida.append("\n");
                    continue;
                }
                
                //Salida de Tokens
                salida.append("  Tipo: " + token.getAlias(token.getType()));
                if (null == token.getType())
                    salida.append("\n");
                else {  // salida semantica de valores de tokens ID y  INT_CONST 
                    switch (token.getType()) {
                        //Tipo de Dato
                        case ID:
                            salida.append(" \n  → Valor: " + token.getAttribute().getIdVal() );
                            break;
                        case INT_CONST:
                            salida.append(" \n  → Valor: " + token.getAttribute().getIntVal() );
                            break;
                        case FLOAT_CONST:
                            salida.append(" \n  → Valor: " + token.getAttribute().getFloatVal() );
                            break;
                        case CHAR_CONST:
                            salida.append(" \n  → Valor: " + token.getAttribute().getCharVal() );
                            break;
                        case BOOLEAN_CONST:
                            salida.append(" \n  → Valor: " + token.getAttribute().getBooleanVal() );
                            break;
                            
                        //Operadores
                        case AND:
                            salida.append(" \n  → Valor: &&"); 
                            break;
                        case OR:
                            salida.append(" \n  → Valor: ||");
                            break;
                        case EQ:
                            salida.append(" \n  → Valor: ==");
                            break;
                        case NEQ:
                            salida.append(" \n  → Valor: !=");
                            break;
                        case MENORQ:
                            salida.append(" \n  → Valor: <");
                            break;
                        case MAYORQ:
                            salida.append(" \n  → Valor: >");
                            break;
                        case MENOR_EQ:
                            salida.append(" \n  → Valor: <=");
                            break;
                        case MAYOR_EQ:
                            salida.append(" MAYOR_EQ");
                            break;
                        case MAS:
                            salida.append(" \n  → Valor: +");
                            break;
                        case MENOS:
                            salida.append(" \n  → Valor: -");
                            break;
                        case POR:
                            salida.append(" \n  → Valor: *");
                            break;
                        case DIV:
                            salida.append(" \n  → Valor: /");
                            break;
                        case MOD:
                            salida.append(" \n  → Valor: %");
                            break;
                            
                        // Palabras Reservadas
                        case MAIN:
                            salida.append(" \n  → Valor: main");
                            break;
                        case INT:
                            salida.append(" \n  → Valor: int");
                            break;
                        case CHAR:
                            salida.append(" \n  → Valor: char");
                            break;
                        case FLOAT:
                            salida.append(" \n  → Valor: float");
                            break;
                        case BOOLEAN:
                            salida.append(" \n  → Valor: boolean");
                            break;
                        case IF:
                            salida.append(" \n  → Valor: if");
                            break;
                        case ELSE:
                            salida.append(" \n  → Valor: else");
                            break;
                        case WHILE:
                            salida.append(" \n  → Valor: while");
                            break;
                        
                        // Simbolos Especiales
                        case PAR_IN:
                            salida.append(" \n  → Valor: ( ");
                            break;
                        case PAR_FIN:
                            salida.append(" \n  → Valor: ) ");
                            break;
                        case CORCH_IN:
                            salida.append(" \n  → Valor: [ ");
                            break;
                        case CORCH_FIN:
                            salida.append(" \n  → Valor: ]");
                            break;
                            case LLAVE_IN:
                        salida.append(" \n  → Valor: { ");
                            break;
                        case LLAVE_FIN:
                            salida.append(" \n  → Valor: } ");
                            break;
                        case PYC:
                            salida.append(" \n  → Valor: ; ");
                            break;
                        case COMA:
                            salida.append(" \n  → Valor: , ");
                            break;
                        case ASIG:
                            salida.append(" \n  → Valor: = ");
                            break;
                        case NEG:
                            salida.append(" \n  → Valor: - ");
                            break;
                        case NOT:
                            salida.append(" \n  → Valor: ! ");
                            break;
                            
                        default:
                            break;
                    }
                    salida.append("  \n  → Posición: (línea: " + token.getLineNumber() + ", columna:" + token.getColumnNumber() + ")\n\n");
                }
            } while (token.getType() != TokenTipo.EOF);

            FR.close();

            long endTime = System.currentTimeMillis();

            // imprime estadisticas, total tokens, tiempo de ejecución
            salida.append("  -----------------------------------------------------------------------------------------------------\n");
            salida.append("  Número de tokens generados: " + numTokens + "\n");
            salida.append("  Tiempo de ejecución: " + (endTime - startTime) + "ms\n");
            salida.append("\n\n");
        }
    }
}