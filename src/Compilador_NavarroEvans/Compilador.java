package Compilador_NavarroEvans;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import Lexico.SalidaLex;
import Sintactico.SalidaSin;
import Semantico.SalidaSem;
import EntradaSalida.IO;
import java.awt.Color;
import java.awt.Font;

public class Compilador extends javax.swing.JFrame {
    
    // instantiation
    IO io = new IO();
    JFileChooser fileChooser = new JFileChooser();
	
    public Compilador() {
        
      
        
        // init app
        initComponents();
        // hide generate tree button
        btnArb.setVisible(false);
        // console initialization
	salida.setText("");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        entrada = new javax.swing.JTextArea();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        btnCargar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        errores = new javax.swing.JTextArea();
        btnASint = new javax.swing.JButton();
        btnALex = new javax.swing.JButton();
        btnASem = new javax.swing.JButton();
        btnArb = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        salida = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compilador");
        setBackground(new java.awt.Color(0, 153, 153));
        setFont(new java.awt.Font("Book Antiqua", 1, 12)); // NOI18N
        setForeground(new java.awt.Color(0, 153, 153));

        entrada.setColumns(1);
        entrada.setRows(1000);
        entrada.setWrapStyleWord(true);
        jScrollPane1.setViewportView(entrada);

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Book Antiqua", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(51, 51, 51));
        label1.setText("Compilador Gaby & Emie");

        label2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        label2.setText("Introduzca su código: ");

        btnCargar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCargar.setText("Cargar archivo...");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFile(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Manejo de Errores");

        errores.setColumns(20);
        errores.setRows(5);
        jScrollPane2.setViewportView(errores);

        btnASint.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnASint.setText("Analizador Sintáctico");
        btnASint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    execParser(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnALex.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnALex.setText("Analizador Léxico");
        btnALex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    execLexer(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnASem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnASem.setText("Analizador Semántico");
        btnASem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    execSemantic(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnArb.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnArb.setText("Generar Árbol");
        btnArb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execTree(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimpiar.setText("Limpiar...");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearConsole(evt);
            }
        });

        salida.setColumns(20);
        salida.setRows(5);
        jScrollPane3.setViewportView(salida);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setText("Salida:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(440, 440, 440)
                                            .addComponent(jLabel3))
                                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(338, 338, 338))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(289, 289, 289)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(460, 460, 460)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnASem)
                                            .addComponent(btnASint, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnALex, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnArb, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnCargar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(btnALex, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnASint, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(btnASem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(btnArb, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiar)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCargar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>    
    
    	/**
	 * generate random strings
	 * @param length: integer: length of the string
	 * @return: String: random string generated
	 */
    private String randomString(int length) {
            String output = "";
            String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for(int i=0; i<length; i++) {
                    output = output+""+chars.charAt((int) Math.floor(Math.random()*51));
            }
            return output;
    }
    
    	/**
	 * fill the editor text area with text from a file
	 * @param file: File: the file from where the text will be loaded
	 */
    private void editorWrite(File file) {
    	try {
    		entrada.setText(io.read(file));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
        /**
     * open a file chooser to select a file
     * @return: File: the file chosen by the user
     */
    private File chooseFile() {
    	if(fileChooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
    	return null;
    }
    
    private void loadFile(java.awt.event.ActionEvent evt) {                          
            	// load file into the editor
    	editorWrite(chooseFile());
    }                         

    private void clearConsole(java.awt.event.ActionEvent evt) {                              
        salida.setText("");
        errores.setText("");
    }                             

    private void execLexer(java.awt.event.ActionEvent evt) throws IOException {                           
        //Limpia
        salida.setText("");
        errores.setText("");
        
        File buffer = new File(randomString(32));
    	io.write(buffer, entrada.getText(), false);
    	SalidaLex lexer = new SalidaLex();
    	lexer.execLexer(buffer, salida);
    	io.delete(buffer);
        
        //Muestra el texArea desde la línea 1
        salida.setCaretPosition(0);
        errores.setCaretPosition(0); 
    }                          

    private void execParser(java.awt.event.ActionEvent evt) throws IOException {                            
        //Limpia
        salida.setText("");
        errores.setText("");
        
        
        
        Font font = new Font("Verdana", Font.BOLD, 12); 
        errores.setForeground(new Color(66, 146, 63));
        errores.setFont(font);
        
        File buffer = new File(randomString(32));
    	io.write(buffer, entrada.getText(), false);
    	SalidaSin parser = new SalidaSin();
    	parser.execParser(buffer, salida, errores);
    	io.delete(buffer);
        
        
        
        //Muestra el texArea desde la línea 1
        salida.setCaretPosition(0);
        errores.setCaretPosition(0); 
    }                           

    private void execSemantic(java.awt.event.ActionEvent evt) throws IOException {                              
        //Limpia
        salida.setText("");
        errores.setText("");
        
        File buffer = new File(randomString(32));
    	io.write(buffer, entrada.getText(), false);
    	SalidaSem semantic = new SalidaSem();
    	semantic.execSemantic(buffer, salida, errores);
    	io.delete(buffer);
        
        //Muestra el texArea desde la línea 1
        salida.setCaretPosition(0);
        errores.setCaretPosition(0); 
    }                             

    private void execTree(java.awt.event.ActionEvent evt) {                          
        
    }                         
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnALex;
    private javax.swing.JButton btnASem;
    private javax.swing.JButton btnASint;
    private javax.swing.JButton btnArb;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JTextArea salida;
    private javax.swing.JTextArea entrada;
    private javax.swing.JTextArea errores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private java.awt.Label label1;
    private java.awt.Label label2;
    // End of variables declaration                   

}
