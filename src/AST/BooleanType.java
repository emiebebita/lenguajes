package AST;

import visitor.Visitor;

public class BooleanType implements Tipo {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
