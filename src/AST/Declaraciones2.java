package AST;

import java.util.ArrayList;
import java.util.List;

import visitor.Visitor;

public class Declaraciones2 {
	private List<Declaraciones3> list;
	
	public Declaraciones2() {
		list = new ArrayList<Declaraciones3>();
	}
	
	public void addElement(Declaraciones3 varDecl) {
		getList().add(varDecl);
	}
	
	public Declaraciones3 elementAt(int index) {
		return getList().get(index);
	}
	
	public int size() {
		return getList().size();
	}
	
	public List<Declaraciones3> getList() {
		return list;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}

	
}
