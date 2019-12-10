package EntradaSalida;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class IO {
	
	public String read(File FP) {
		FileReader FR;
		BufferedReader BR;
		int buffer;
		String outputBuffer = "";
		
		try {
			// Open file
			FR = new FileReader(FP.toString());
			BR = new BufferedReader(FR);
			
			// Read file
			while((buffer = BR.read()) != -1){
				outputBuffer = outputBuffer+""+(char) buffer;
			}
			BR.close();
			FR.close();
			return outputBuffer;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void write(File FP, String Data, boolean ATF) {
		FileWriter FW;
		PrintWriter PW;
		
		try{
			// Open file
			FW = new FileWriter(FP.toString(), ATF);
			PW = new PrintWriter(FW);
			
			// Write file
            PW.print(Data);
			FW.close();
			PW.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	public void delete(File FP) {
		try {
			FP.delete();
		} catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
}
