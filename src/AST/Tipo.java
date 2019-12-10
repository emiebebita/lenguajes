package AST;

import visitor.Visitor;

public interface Tipo {
	public void accept(Visitor v);
}
