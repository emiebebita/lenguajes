package AST;

import visitor.Visitor;

public class CharType implements Tipo {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
