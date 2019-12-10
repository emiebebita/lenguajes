package AST;

import visitor.Visitor;

public class CharArrayType implements Tipo {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
