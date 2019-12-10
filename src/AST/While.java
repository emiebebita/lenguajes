package AST;

import visitor.Visitor;

public class While implements Sentencia2 {
	private Exp condExp;
	private Sentencia2 stm;
	
	public While(Exp condExp, Sentencia2 stm) {
		this.condExp = condExp;
		this.stm = stm;
	}
	
	public Exp getCondExp() {
		return condExp;
	}
	
	public Sentencia2 getStm() {
		return stm;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
