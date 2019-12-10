package AST;

import visitor.Visitor;

public class IntegerArrayType implements Tipo {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
