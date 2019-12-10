package AST;

import visitor.Visitor;

public class FloatType implements Tipo{
	public void accept(Visitor v) {
		v.visit(this);
	}
}
