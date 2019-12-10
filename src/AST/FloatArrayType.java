package AST;

import visitor.Visitor;

public class FloatArrayType implements Tipo{
	public void accept(Visitor v) {
		v.visit(this);
	}
}
