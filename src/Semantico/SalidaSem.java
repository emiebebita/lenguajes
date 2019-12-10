package Semantico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextArea;

public class SalidaSem {
	public void execSemantic(File file, JTextArea console, JTextArea errors) throws IOException {
		if (file == null)
			console.append("Dirección del archivo no recibida\n");
		else {
			FileReader FR = null;
			
			// attempt to open file
			try {
				FR = new FileReader(file.toString());
			} catch (FileNotFoundException e) {
				console.append(file.toString() + " no fue encontrado\n!");
			}
			
			// create semantic analyzer
			Semantico semantic = new Semantico(FR);
			console.append("  -----------------------------------ANALISIS SEMANTICO----------------------------------\n\n");
			
			// initiate parse and clock time
			long startTime = System.currentTimeMillis();
			semantic.AnalizadorSemantico(console, errors);
			long endTime = System.currentTimeMillis();
			
			// print out statistics
			console.append("  -----------------------------------------------------------------------------------------------------\n");
			console.append("Tiempo de ejecución: " + (endTime - startTime) + "ms\n");
			console.append(semantic.getErr() + " errores reportados\n");
			
			FR.close();
		}
	}
}