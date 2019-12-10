package Semantico;

import AST.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;
import Sintactico.Sintactico;
import AST.Tipo;

public class Semantico {
    private static final HashMap<Error,String> errorAlias = new HashMap<>();

    private JTextArea salida;
    private JTextArea errores;

    private Sintactico anasem;
    private ArrayList<Declaraciones3> declaraciones;
    private ArrayList<Identificadores> identificadores;
    private ArrayList<Asignar> asignaciones;
    private ArrayList<Exp> condiciones;

    private int err;

    public Semantico(FileReader file) throws IOException{
            this.anasem = new Sintactico(file);
    }

    //Devuelve los errores 
    public int getErr() {
            return err;
    }

    //Analizador Semantico
    public void AnalizadorSemantico(JTextArea salida, JTextArea errores) throws IOException{
            this.salida = salida;
            this.errores = errores;
            this.anasem.AnalisisSintactico(salida, errores);
            this.declaraciones = this.anasem.getDeclaraciones();
            VerificarDec();
            this.identificadores = this.anasem.getIdentificadores();
            VerificarID();
            this.asignaciones = this.anasem.getAsignaciones();
            VerificarAsignaciones();
            this.condiciones = this.anasem.getCondiciones();
            VerificarCondiciones();	
    }

    //Verifica las declaraciones
    private void VerificarDec(){
            for(int i = 0; i < declaraciones.size(); i++){
                    Declaraciones3 varDecl = declaraciones.get(i);
                    String nombID = varDecl.getId().getNombre();

                    for(int j = i + 1; j < declaraciones.size(); j ++){
                            Declaraciones3 _varDecl = declaraciones.get(j);
                            String nombID2 = _varDecl.getId().getNombre();

                            if(nombID.equals(nombID2))
                                    error(Error.REPETIDO, nombID2);
                    }
            }
    }

    //Verifica si los identificadores fueron declarados
    private void VerificarID(){
            for (Identificadores identifier : identificadores) {
                    if(!VerificarExistenciaID(identifier.getNombre()))
                            error(Error.SIN_DECLARAR, identifier.getNombre());
            }
    }
	
    //Verificar Condiciones
    private void VerificarCondiciones(){
            for (Exp exp : condiciones) {
                    if((exp instanceof MoreThan || exp instanceof MoreThanEqual ||exp instanceof LessThan ||
                                    exp instanceof LessThanEqual || exp instanceof NotEqual || exp instanceof Equal))
                            error(Error.COND_INV, null);
            }

    }

    //Verifica si un identificador existe
    private boolean VerificarExistenciaID(String name){
            for (Declaraciones3 varDecl : declaraciones) {
                    String idName = varDecl.getId().getNombre();

                    if(idName.equals(name))
                            return true;
            }
            return false;
    }

    //Verifica las Asignaciones
    private void VerificarAsignaciones(){
        for (Asignar asg : asignaciones) {
            // id = exp;
            Exp tipo = asg.getValue(); //Se le asigna el valor de la asignacion a tipo
            String nombID = asg.getId().getNombre(); 
            Tipo tipoID = getTipoID(nombID);

            //Si la declaracion fue int
            if(tipoID != null && (tipoID instanceof IntegerType || tipoID instanceof IntegerArrayType)){

                // Se le asigno un float
                if(tipo instanceof FloatLiteral)
                        error(Error.FLOAT_INT, nombID);
                // Se le asigno un booleano
                if(tipo instanceof BooleanLiteral)
                        error(Error.BOOLEAN_INT, nombID);
                // Se le asigno tipo id
                if(tipo instanceof IdentifierExp){
                    String nombID2 = ((IdentifierExp) tipo).getName();
                    Tipo tipoID2 = getTipoID(nombID2);

                    if(tipoID2 != null){
                        // Se le asigno un float
                        if (tipoID2 instanceof FloatType)
                                error(Error.FLOAT_INT, nombID);
                        // Se le asigno un booleano
                        else if( tipoID2 instanceof BooleanType)
                                error(Error.BOOLEAN_INT, nombID);

                        if(tipoID instanceof IntegerType)
                                // Se le asigno tipo arreglo id
                                if (tipoID2 instanceof FloatArrayType || tipoID2 instanceof BooleanArrayType
                                                || tipoID2 instanceof IntegerArrayType || tipoID2 instanceof CharArrayType)
                                        error(Error.ARR_VAR, nombID);
                        
                        if(tipoID instanceof IntegerArrayType)
                                // Se le asigno un id (asig simple)
                                if (tipoID2 instanceof FloatType || tipoID2 instanceof BooleanType
                                                || tipoID2 instanceof IntegerType || tipoID2 instanceof CharType)
                                        error(Error.VAR_ARR, nombID);
                    }
                }
            }

            // Si la declaracion fue float
            if(tipoID != null && (tipoID instanceof FloatType || tipoID instanceof FloatArrayType)){

                // Se le asigno un booleano
                if(tipo instanceof BooleanLiteral)
                        error(Error.BOOLEAN_FLOAT, nombID);
                // Se le asigno un char
                if(tipo instanceof CharLiteral)
                        error(Error.CHAR_FLOAT, nombID);
                //Se le asigno un id
                if(tipo instanceof IdentifierExp){
                    String nombID3 = ((IdentifierExp) tipo).getName();
                    Tipo tipoID3 = getTipoID(nombID3);

                    if(tipoID3 != null){
                        // Se le asigno un booleano
                        if (tipoID3 instanceof BooleanType)
                                error(Error.BOOLEAN_FLOAT, nombID);
                        // Se le asigno un char
                        else if( tipoID3 instanceof CharType)
                                error(Error.CHAR_FLOAT, nombID);

                        if(tipoID instanceof FloatType)
                                // Se le asigno un arreglo id
                                if (tipoID3 instanceof FloatArrayType || tipoID3 instanceof BooleanArrayType
                                                || tipoID3 instanceof IntegerArrayType || tipoID3 instanceof CharArrayType)
                                        error(Error.ARR_VAR, nombID);

                        if(tipoID instanceof FloatArrayType)
                                // Se le asigno un id (asig simple)
                                if (tipoID3 instanceof FloatType || tipoID3 instanceof BooleanType
                                                || tipoID3 instanceof IntegerType || tipoID3 instanceof CharType)
                                        error(Error.VAR_ARR, nombID);
                        }
                }
            }

            // Si la declaracion fue char
            if(tipoID != null && (tipoID instanceof CharType || tipoID instanceof CharArrayType)){

                // Se le asigno un int
                if(tipo instanceof IntegerLiteral)
                        error(Error.INT_CHAR, nombID);
                // Se le asigno un float
                if(tipo instanceof FloatLiteral)
                        error(Error.FLOAT_CHAR, nombID);
                // Se le asigno un boolean
                if(tipo instanceof BooleanLiteral)
                        error(Error.BOOLEAN_CHAR, nombID);
                // Se le asigno un id
                if(tipo instanceof IdentifierExp){
                    String _idName = ((IdentifierExp) tipo).getName();
                    Tipo _idType = getTipoID(_idName);

                    if(_idType != null){

                        // Se le asigno un float
                        if (_idType instanceof FloatType)
                                error(Error.FLOAT_CHAR, nombID);
                        // Se le asigno un int
                        else if (_idType instanceof IntegerType)
                                error(Error.INT_CHAR, nombID);
                        // Se le asigno un boolean
                        else if( _idType instanceof BooleanType)
                                error(Error.BOOLEAN_CHAR, nombID);

                        if(tipoID instanceof CharType)
                                // Se le asigno un arreglo id
                                if (_idType instanceof FloatArrayType || _idType instanceof BooleanArrayType
                                                || _idType instanceof IntegerArrayType || _idType instanceof CharArrayType)
                                        error(Error.ARR_VAR, nombID);

                        if(tipoID instanceof CharArrayType)
                                // Se le asigno un id
                                if (_idType instanceof FloatType || _idType instanceof BooleanType
                                                || _idType instanceof IntegerType || _idType instanceof CharType)
                                        error(Error.VAR_ARR, nombID);
                    }

                }
            }

            // Si la declaración fue boolean
            if(tipoID != null && (tipoID instanceof BooleanType || tipoID instanceof BooleanArrayType)){
                    //Se le asigno un booleano
                    if(tipo instanceof IntegerLiteral)
                            error(Error.INT_BOOLEAN, nombID);
                    // Se le asigno un  float
                    if(tipo instanceof FloatLiteral)
                            error(Error.FLOAT_BOOLEAN, nombID);
                    // Se le asigno un char
                    if(tipo instanceof CharLiteral)
                            error(Error.CHAR_BOOLEAN, nombID);
                    // Se le asigno un id
                    if(tipo instanceof IdentifierExp){
                        String _idName = ((IdentifierExp) tipo).getName();
                        Tipo _idType = getTipoID(_idName);

                        if(_idType != null){

                            // Se le asigno un float
                            if (_idType instanceof FloatType)
                                    error(Error.FLOAT_CHAR, nombID);
                            // Se le asigno un int
                            else if (_idType instanceof IntegerType)
                                    error(Error.INT_CHAR, nombID);
                            // Se le asigno un char
                            else if( _idType instanceof CharType)
                                    error(Error.CHAR_BOOLEAN, nombID);

                            if(tipoID instanceof BooleanType)
                                    // Se le asigno un arreglo id
                                    if (_idType instanceof FloatArrayType || _idType instanceof BooleanArrayType
                                                    || _idType instanceof IntegerArrayType || _idType instanceof CharArrayType)
                                            error(Error.ARR_VAR, nombID);

                            if(_idType instanceof BooleanArrayType)
                                    // Se le asigno un id (simple)
                                    if (_idType instanceof FloatType || _idType instanceof BooleanType
                                                    || _idType instanceof IntegerType || _idType instanceof CharType)
                                            error(Error.VAR_ARR, nombID);
                        }

                    }
            }

        }
    }

    //Obtener id (IntegerType | IntegerArrayType | FloatType | FloatArrayType | CharType | CharArrayType)
    private Tipo getTipoID(String name){
        for (Declaraciones3 dec : declaraciones) {
            Identificadores id = dec.getId();
            if(id.getNombre().equals(name))
                    return dec.getType();
        }

        return null;
    }

    // Impresión de Errores
    private void error(Error errorType, Object parm){
            err++;
            switch (errorType) {
            case REPETIDO:
                    errores.append(" → Error de Declaración: "+this.getErrorAlias(Error.REPETIDO)+", variable (" + (String) parm + ")\n");
                    break;
            case SIN_DECLARAR:
                    errores.append(" → Error de Declaración: "+this.getErrorAlias(Error.SIN_DECLARAR)+", variable (" + (String) parm + ")\n");
                    break;
            case FLOAT_INT:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.FLOAT_INT)+", variable (" + parm + ")\n");
                    break;
            case BOOLEAN_INT:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.BOOLEAN_INT)+", variable (" + parm + ")\n");
                    break;
            case INT_BOOLEAN:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.INT_BOOLEAN)+", variable (" + parm + ")\n");
                    break;
            case BOOLEAN_FLOAT:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.BOOLEAN_FLOAT)+", variable (" + parm + ")\n");
                    break;
            case FLOAT_BOOLEAN:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.FLOAT_BOOLEAN)+", variable (" + parm + ")\n");
                    break;
            case CHAR_FLOAT:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.CHAR_FLOAT)+", variable (" + parm + ")\n");
                    break;
            case CHAR_BOOLEAN:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.CHAR_BOOLEAN)+", variable (" + parm + ")\n");
                    break;
            case FLOAT_CHAR:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.FLOAT_CHAR)+", variable (" + parm + ")\n");
                    break;
            case BOOLEAN_CHAR:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.BOOLEAN_CHAR)+", variable (" + parm + ")\n");
                    break;
            case INT_CHAR:
                    errores.append(" → Error de Conversión: "+this.getErrorAlias(Error.INT_CHAR)+", variable (" + parm + ")\n");
                    break;
            case ARR_VAR:
                    errores.append(" → Asignación Inválida: "+this.getErrorAlias(Error.ARR_VAR)+", variable (" + parm + ")\n");
                    break;
            case VAR_ARR:
                    errores.append(" → Asignación Inválida: "+this.getErrorAlias(Error.VAR_ARR)+", variable (" + parm + ")\n");
                    break;
            case COND_INV:
                    errores.append(" → Condición Inválida: "+this.getErrorAlias(Error.COND_INV)+"\n");
                    break;
            default:
                    break;
            }
    }

    private String getErrorAlias(Error errorType) {

            errorAlias.put(Error.SIN_DECLARAR, "VARIABLE NO DECLARADA");

            errorAlias.put(Error.REPETIDO, "MÚLTIPLES DECLARACIONES");

            errorAlias.put(Error.FLOAT_INT, "CONVERSIÓN DE FLOTANTE A ENTERO");
            errorAlias.put(Error.FLOAT_CHAR, "CONVERSIÓN DE FLOTANTE A CARÁCTER");
            errorAlias.put(Error.FLOAT_BOOLEAN, "CONVERSIÓN DE FLOTANTE A BOOLEANO");
            errorAlias.put(Error.BOOLEAN_INT, "CONVERSIÓN DE BOOLEANO A ENTERO");
            errorAlias.put(Error.BOOLEAN_FLOAT, "CONVERSIÓN DE BOOLEANO A FLOTANTE");
            errorAlias.put(Error.BOOLEAN_CHAR, "CONVERSIÓN DE BOOLEANO A CARÁCTER");
            errorAlias.put(Error.INT_BOOLEAN, "CONVERSIÓN DE ENTERO A BOOLEANO");
            errorAlias.put(Error.INT_CHAR, "CONVERSIÓN DE ENTERO A FLOTANTE");
            errorAlias.put(Error.CHAR_FLOAT, "CONVERSIÓN DE CARÁCTER A FLOTANTE");
            errorAlias.put(Error.CHAR_BOOLEAN, "CONVERSIÓN DE CARÁCTER A BOOLEANO");

            errorAlias.put(Error.VAR_ARR, "VARIABLE A ARREGLO");
            errorAlias.put(Error.ARR_VAR, "ARREGLO A VARIABLE");
            errorAlias.put(Error.COND_INV, "CONDICIÓN INVÁLIDA");

            return errorAlias.get(errorType);
    }

}