package AST;

import visitor.Visitor;

public class IntegerType implements Tipo {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
