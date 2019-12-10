package Lexico;

public class TokenAtributo {
	private int valorInt; // valor int
	private float valorFloat; // valor float
	private char valorChar; // valor char
	private boolean valorBoolean; // valor boolean
	private String valorId; // valor id

	public TokenAtributo() {}

	// Constructor
	public TokenAtributo(int intVal){
		this.valorInt = intVal;
	}
	public TokenAtributo(float floatVal){
		this.valorFloat = floatVal;
	}
	public TokenAtributo(char charVal){
		this.valorChar = charVal;
	}
	public TokenAtributo(boolean booleanVal){
		this.valorBoolean = booleanVal;
	}
	public TokenAtributo(String idVal){
		this.valorId = idVal;
	}
        
        //Asignar
        public void setIntVal(int intVal) {
		this.valorInt = intVal;
	}
        public void setFloatVal(float floatVal) {
		this.valorFloat = floatVal;
	}
        public void setCharVal(char charVal) {
		this.valorChar = charVal;
	}
        public void setBooleanVal(boolean booleanVal) {
		this.valorBoolean = booleanVal;
	}
        public void setIdVal(String idVal) {
		this.valorId = idVal;
	}
        
        //Devolver
	public int getIntVal() {
		return valorInt;
	}
	public float getFloatVal() {
		return valorFloat;
	}
	public char getCharVal() {
		return valorChar;
	}
	public boolean getBooleanVal() {
		return valorBoolean;
	}
	public String getIdVal() {
		return valorId;
	}
}
