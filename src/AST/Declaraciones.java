package AST;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import visitor.Visitor;

public class Declaraciones {
	private List<Declaraciones2> list;

	public Declaraciones() {
		list = new ArrayList<Declaraciones2>();
	}

	public void addElement(Declaraciones2 varDecl) {
		getList().add(varDecl);
	}

	public Declaraciones2 elementAt(int index) {
		return getList().get(index);
	}

	public int size() {
		return getList().size();
	}

	public List<Declaraciones2> getList() {
		return list;
	}

	public void accept(Visitor v, JTextArea console) {
		v.visit(this, console);
	}
}
