package AST;

import visitor.Visitor;

public class Asignar implements Sentencia2 {
	private Identificadores id;
	private Exp value;
	
	public Asignar(Identificadores id, Exp value) {
		this.id = id;
		this.value = value;
	}
	
	public Identificadores getId() {
		return id;
	}
	
	public Exp getValue() {
		return value;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
