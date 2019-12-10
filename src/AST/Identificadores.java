package AST;

import visitor.Visitor;

public class Identificadores {
	private String name;
	
	public Identificadores(String name) {
		this.name = name;
	}
	
	public String getNombre() {
		return name;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
