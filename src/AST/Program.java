package AST;

import javax.swing.JTextArea;
import visitor.Visitor;

public class Program {
	private Sentencia stm;
	private Declaraciones list;
	
	public Program(Sentencia stm, Declaraciones list) {
		this.stm = stm;
		this.setList(list);
	}
	
	public Sentencia getStm() {
		return stm;
	}
	
	public void accept(Visitor v, JTextArea console) {
		v.visit(this, console);
	}

	public Declaraciones getList() {
		return list;
	}

	public void setList(Declaraciones list) {
		this.list = list;
	}
}
