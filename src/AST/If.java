package AST;

import visitor.Visitor;

public class If implements Sentencia2 {
	private Exp condExp;
	private Sentencia2 trueStm, falseStm;
	
	public If(Exp condExp, Sentencia2 trueStm, Sentencia2 falseStm) {
		this.condExp = condExp;
		this.trueStm = trueStm;
		this.falseStm = falseStm;
	}
	
	public Exp getCondExp() {
		return condExp;
	}
	
	public Sentencia2 getTrueStm() {
		return trueStm;
	}
	
	public Sentencia2 getFalseStm() {
		return falseStm;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
