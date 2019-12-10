package AST;

import java.util.ArrayList;
import java.util.List;

public class Sentencia {
	private List<Sentencia2> list;
	
	public Sentencia() {
		list = new ArrayList<Sentencia2>();
	}
	
	public void addElement(Sentencia2 stm) {
		list.add(stm);
	}
	
	public Sentencia2 elementAt(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
}
