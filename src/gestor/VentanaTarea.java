/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * @author Alvaro Garcia Tapia
 * @author Pablo Mosquera Diaz
 * @author Victor Navarro Ortego
 * @author Adrian Neila Serrano
 * @author Daniel Sanchez Suarez
 * 
 * @proyecto Gestor de Tareas Online
 * @version 1.1
 */
public class VentanaTarea extends JFrameConFondo implements ActionListener{
    
    //Creación de variables
	private JButton botonAceptar;	
        private JLabel labelNombre;
        private JTextField Nombre;	
        private JLabel labelFecha;	
        private JLabel labelDias;
        private String dias[] = {"00","01","02","03","04","05","06","07","08","09","10",
                         "11","12","13","14","15","16","17","18","19","20",
                         "21","22","23","24","25","26","27","28","29","30","31"};
        private JComboBox Dia; 	
        private JLabel labelMes;
        private String meses[] = {"00","01","02","03","04","05","06","07","08","09","10","11","12"};
        private JComboBox Mes;	
        private JLabel labelAño;
        private String años[] = {"0000","2012","2013","2014","2015"};
        private JComboBox Año;	
        private JLabel labelTiempo;
        private JTextField Tiempo;
	private String nombre = "";
	private String tiempo = "";
	private String dia = "";
	private String mes = "";
	private String año = "";
        private JLabel labelInformacion;
        private String lista;
        private String user;
        private Ventana ventana;
        private int numLista = 0;
        
	
	public VentanaTarea(String lista,String user, Ventana ventana, int numLista){
                //constructor JFrame
		super("Nueva Tarea"); 

                //Almacenamiento de usuario, lista y ventana desde la que se llamo
                this.user = user;
                this.lista = lista;
                this.ventana = ventana;
                this.numLista = numLista;
                
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
		setSize(370,370);
                setLocationRelativeTo(null);
		Container contenidoPanel = getContentPane();
		contenidoPanel.setBackground(Color.LIGHT_GRAY);
                setImagen("objetos/fondo.jpg");
                

                
                //Cuadrante de introducción de nombre
		labelNombre = new JLabel("Nombre:(*)");
                labelNombre.setBounds(50,50,300,10);
                labelNombre.setForeground(Color.white);
		this.Nombre = new JTextField();
		this.Nombre.setBounds(50,70,200,20);
		
                //Cuadrante de introducción de la fecha
		labelFecha = new JLabel("Fecha Límite:");
		labelFecha.setBounds(50,100,300,10);
                labelFecha.setForeground(Color.white);
                //Cuadrante de introducción del dia
		labelDias = new JLabel("Dia");
		labelDias.setBounds(50,120,300,10);
                labelDias.setForeground(Color.white);
                Dia = new JComboBox(dias);
                Dia.setBounds(50,132,40,20);
                //Cuadrante de introducción del mes
		labelMes = new JLabel("Mes");
		labelMes.setBounds(95,120,300,10);
                labelMes.setForeground(Color.white);
                Mes = new JComboBox(meses);
                Mes.setBounds(95,132,40,20);
                //Cuadrante de introducción del año
		labelAño = new JLabel("Año");
		labelAño.setBounds(140,120,300,10);
                labelAño.setForeground(Color.white);
                Año = new JComboBox(años);
                Año.setBounds(140,132,70,20);
			
                
                //Cuadrante de introducción del tiempo estimado
		labelTiempo = new JLabel("Tiempo estimado (en dias):");
		labelTiempo.setBounds(50,160,300,10);
                labelTiempo.setForeground(Color.white);
		this.Tiempo = new JTextField();
		this.Tiempo.setBounds(50,180,200,20);
		
                //Constucción del boton Aceptar
		botonAceptar = new JButton("Aceptar"); 
		contenidoPanel.add(botonAceptar);
		botonAceptar.setBounds(180,220,80,25);
                botonAceptar.setActionCommand("NewTarea");
		botonAceptar.addActionListener(this);
                botonAceptar.setIcon(new ImageIcon(getClass().getResource("objetos/Aceptar Tarea.jpg")));
                
                //Nota indicando que campos son obligatorios
		labelInformacion = new JLabel("Los campos (*) son obligatorios");
		labelInformacion.setBounds(150,300,300,25);
                labelInformacion.setForeground(Color.red);
                
                JTextField text = new JTextField() {
		    @Override public void setBorder(Border border) {
		    }
		};

                //Añadiendo cuadrantes
		getContentPane().add(labelNombre);
		getContentPane().add(this.Nombre);
		getContentPane().add(labelFecha);
		getContentPane().add(labelDias);
		getContentPane().add(labelMes);
		getContentPane().add(labelAño);
                add(Dia);
                add(Mes);
                add(Año);
		getContentPane().add(labelTiempo);
		getContentPane().add(this.Tiempo);
		getContentPane().add(labelInformacion);

                //Se indica que la ventana sea visible
		setVisible(true);
		
	}
        
        
            
            @Override
    public void actionPerformed(ActionEvent e) {
        
         //Variable qu contiene que boton se ha pulsado
         String comando = e.getActionCommand();

         //Si se pulso el boton aceptar
            if (comando.equals("NewTarea")){
                //Se crea el objeto que se conectara a la bbdd para tareas
                Tareas tarea = new Tareas();
                boolean salir = false;
                //Si no se ha conectado
                while (!tarea.estado() && !salir){
                    this.setCursor(Cursor.WAIT_CURSOR);
                    String message = "Fallo al conectar con el servidor.\n¿Desea volver a intentar conectar?";
                    String title = "Error de conexión";
                    //Se ofrece la opcion de reconectar
                    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                    //Si rechazo reconectar
                    if (reply == JOptionPane.NO_OPTION){     
                        this.setCursor(Cursor.WAIT_CURSOR);
                        salir=true;
                        new VentanaInicio();
                        this.ventana.setVisible(false);
                        this.ventana.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        setVisible(false);
                        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        this.setCursor(Cursor.DEFAULT_CURSOR);
                    }
                    //Si se acepto reconectar
                    else{
                        this.setCursor(Cursor.WAIT_CURSOR);
                        //Se reconecta
                        tarea = new Tareas();
                        this.setCursor(Cursor.DEFAULT_CURSOR);
                    }    
                }
                //Se obtienen los datos proporcionados
                nombre = this.Nombre.getText();
                dia = (String)Dia.getSelectedItem();
                mes = (String)Mes.getSelectedItem();
                año = (String)Año.getSelectedItem();
                tiempo = this.Tiempo.getText();
                boolean tiempocorrecto = true;
                //Se comprueba si el tiempo estimado es un numero
                if (this.tiempo.equals(""))
                    tiempo = "0";
                if (((this.dia.equals("00")||this.mes.equals("00")||this.año.equals("0000")) &&
                        !(this.dia.equals("00")&&this.mes.equals("00")&&this.año.equals("0000"))))
                    JOptionPane.showMessageDialog (null,"No es una fecha valida",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
                else{
                try {
                    Integer.parseInt(tiempo);
                    tiempocorrecto = true;
                } catch (NumberFormatException nfe){
                    tiempocorrecto = false;
                }
                //Se comprueba si el tiempo estimado es un negativo
                if(tiempocorrecto)
                    if (Integer.parseInt(tiempo)<0) 
                        tiempocorrecto = false;
                
                //Se comprueba que el nombre de la tarea este libre
                if (tarea.gettarea(user, lista, nombre)!=null)
                    JOptionPane.showMessageDialog (null,"El nombre de la tarea ya está en uso.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
                //Se comprueba que se haya indicado un nombre
                else if(!this.nombre.equals("") && tiempocorrecto){
                    //Se comprueba que se haya puesto un valor en tiempo estimado
                    if (this.tiempo.equals(""))
                        tiempo = "0";
                    //Se añade la tarea nueva
                    tarea.addtarea(user+lista, nombre, dia, mes, año, tiempo);
                    //Se actualiza la tabla
                    ventana.ActualizarTablas(numLista);
                    //Se cierra la ventana
                    setVisible(false);
                    setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                }
                //Si el tiempo estimado no es valido
                else if (!tiempocorrecto)
                    //Se muestra un aviso con el error
                    JOptionPane.showMessageDialog (null,"El tiempo estimado debe ser un número válido.",
				"Atención", JOptionPane.INFORMATION_MESSAGE); 
                //Si no se ha puesto un nombre a la tarea
                else
                    //Se avisa que no se ha puesto nombre
                    JOptionPane.showMessageDialog (null,"Debe rellenar los campos obligatorios.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);   
                }
            }
    }
}
