package Sintactico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextArea;
import AST.Program;
import visitor.PrintVisitor;

public class SalidaSin {
	public void execParser(File file, JTextArea salida, JTextArea errores) throws IOException {
		if (file == null)
			salida.append("Dirección del archivo no recibida\n");
		else {
			FileReader FR = null;
			
			//Abriendo el archivo 
			try {
                                FR = new FileReader(file.toString());
			} catch (FileNotFoundException e) {
				salida.append(file.toString() + " no fue encontrado!\n");
			}
			
			// Inicia el Analisis Sintactico
			Sintactico parser = new Sintactico(FR);
			salida.append("  ----------------------------------ANALISIS SINTACTICO----------------------------------\n\n");
			
			// Obtiene el tiempo de ejecución
			long startTime = System.currentTimeMillis();
			Program program = parser.AnalisisSintactico(salida, errores);
			long endTime = System.currentTimeMillis();
			
			// Fin del analisis
			salida.append("  -----------------------------------------------------------------------------------------------------\n");
			salida.append("Tiempo de ejecución: " + (endTime - startTime) + "ms\n");
			salida.append(parser.getErrors() + " errores reportados\n");
			
			// ASTs
			PrintVisitor printer = new PrintVisitor();  //crea objeto
			printer.visit(program, salida);
			
			FR.close();
		}
	}
}