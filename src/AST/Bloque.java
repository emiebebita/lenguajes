package AST;

import visitor.Visitor;

public class Bloque implements Sentencia2 {
	private Sentencia stms;
	
	public Bloque(Sentencia stms) {
		this.stms = stms;
	}
	
	public Sentencia getStms() {
		return stms;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
