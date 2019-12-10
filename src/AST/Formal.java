package AST;

import visitor.Visitor;

public class Formal {
	private Tipo type;
	private Identificadores id;
	
	public Formal(Tipo type, Identificadores id) {
		this.type = type;
		this.id = id;
	}
	
	public Tipo getType() {
		return type;
	}
	
	public Identificadores getId() {
		return id;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
