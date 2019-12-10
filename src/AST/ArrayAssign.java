package AST;

import visitor.Visitor;

public class ArrayAssign implements Sentencia2 {
	private Identificadores id;
	private Exp index, value;
	
	public ArrayAssign(Identificadores id, Exp index, Exp value) {
		this.id = id;
		this.index = index;
		this.value = value;
	}
	
	public Identificadores getId() {
		return id;
	}
	
	public Exp getIndex() {
		return index;
	}
	
	public Exp getValue() {
		return value;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
