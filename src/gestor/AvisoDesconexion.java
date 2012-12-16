/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Alvaro Garcia Tapia
 * @author Pablo Mosquera Diaz
 * @author Victor Navarro Ortego
 * @author Adrian Neila Serrano
 * @author Daniel Sanchez Suarez
 * 
 * @proyecto Gestor de Tareas Online
 * @version 0.7
 */
public class AvisoDesconexion extends JFrame  implements ActionListener{
    
    	
        private JLabel Mensaje;
        
        public AvisoDesconexion(){
                //constructor JFrame
		super("Desconectando"); 
            
                //Opciones de ventana
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                //se añade la opción de preguntar antes de salir
                addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent evt) {
                        setVisible(false);
                        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                    }
                });
                
		setLayout(null);
		setSize(370,100);
                setLocationRelativeTo(null);
		Container contenidoPanel = getContentPane();
		contenidoPanel.setBackground(Color.white);
                
                //Cuadrante de introducción de nombre
		Mensaje = new JLabel("Saliendo. por favor espere.");
                Mensaje.setBounds(50,20,300,10);
                Mensaje.setForeground(Color.black);
                
                
		getContentPane().add(Mensaje);
                
                setVisible(true);
                
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
