package AST;

import visitor.Visitor;

public class BooleanArrayType implements Tipo {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
