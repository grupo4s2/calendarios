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
public class VentanaMTarea extends JFrameConFondo implements ActionListener{
    
    
    
    //Creación de variables
	private JButton botonGuardar;	
        private JLabel labelNombre;
        private JTextField Nombre;
        private String nombreantiguo;
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
        private String lista;
        private String user;
        private String name;
        private Ventana ventana;
        private Tareas tarea;
        private String [] datos = new String[3];
        private int numLista = 0;
        
	

	public VentanaMTarea(String user,String lista,String name, Ventana ventana, int numLista){
                //constructor JFrame
		super("Modificar Tarea"); 

                //Almacenamient de usuario
                this.user = user;
                //almacenamiento de nombre de tarea,lista y ventana llamante
                this.name = name;
                this.lista = lista;
                this.ventana = ventana;
                this.numLista = numLista;
                
                //Se crea el objeto que se conectara a la bbdd para tareas
                tarea = new Tareas();
                boolean salir = false;
                //Si no se consigue la conexion
                while (!tarea.estado() && !salir){
                    this.setCursor(Cursor.WAIT_CURSOR);
                    //Si se pulso el boton Logout
                    String message = "Fallo al conectar con el servidor.\n¿Desea volver a intentar conectar?";
                    String title = "Error de conexión";
                    //Se ofrece la posibilidad de reconectar con la bbdd
                    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                    //Si se rechaza la opcion de reintentar la conexion
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
                    //Si se acepta reintentar la conexion a la bbdd
                    else{
                        this.setCursor(Cursor.WAIT_CURSOR);
                        //Se reconecta
                        tarea = new Tareas();
                        this.setCursor(Cursor.DEFAULT_CURSOR);
                    }    
                }
                //Se obtinen los datos de la tarea antes de modificarla
                datos = tarea.gettarea(user, lista, name);
                
                
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
		labelNombre = new JLabel("Nombre:");
                labelNombre.setBounds(50,50,300,10);
                labelNombre.setForeground(Color.white);
		this.Nombre = new JTextField();
                //Se rellena el campo Nombre con el nombre actual
                this.Nombre.setText(name);
                //Se guarda el nombre antiguo
                nombreantiguo = name;
		this.Nombre.setBounds(50,70,200,20);
		
                //Cuadrante de introducción de pass
		labelFecha = new JLabel("Fecha Límite:");
		labelFecha.setBounds(50,100,300,10);
                labelFecha.setForeground(Color.white);
		labelDias = new JLabel("Dia");
		labelDias.setBounds(50,120,300,10);
                labelDias.setForeground(Color.white);
                Dia = new JComboBox(dias);
                //Se rellena el campo Dia con el dia actual
                Dia.setSelectedItem(datos[0].substring(8,10));
                Dia.setBounds(50,132,40,20);
		labelMes = new JLabel("Mes");
		labelMes.setBounds(95,120,300,10);
                labelMes.setForeground(Color.white);
                Mes = new JComboBox(meses);
                //Se rellena el campo Mes con el mes actual
                Mes.setSelectedItem(datos[0].substring(5,7));
                Mes.setBounds(95,132,40,20);
		labelAño = new JLabel("Año");
		labelAño.setBounds(140,120,300,10);
                labelAño.setForeground(Color.white);
                Año = new JComboBox(años);
                //Se rellena el campo Año con el año actual
                Año.setSelectedItem(datos[0].substring(0,4));
                Año.setBounds(140,132,60,20);
			
                //Cuadrante de introducción de pass
		labelTiempo = new JLabel("Tiempo estimado (en dias):");
		labelTiempo.setBounds(50,160,300,10);
                labelTiempo.setForeground(Color.white);
		this.Tiempo = new JTextField();
                //Se rellena el campo Tiempo con el timpo estima actual
                this.Tiempo.setText(datos[1]);
		this.Tiempo.setBounds(50,180,200,20);
		
                //Constucción del boton Aceptar
		botonGuardar = new JButton("Guardar"); 
		contenidoPanel.add(botonGuardar);
		botonGuardar.setBounds(180,220,80,25);
                botonGuardar.setActionCommand("Modificar");
		botonGuardar.addActionListener(this);
                botonGuardar.setIcon(new ImageIcon(getClass().getResource("objetos/Guardar Tarea.jpg")));
                
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

                //Se hace la ventana visible
		setVisible(true);
		
	}
        
        
   
            
    @Override
    public void actionPerformed(ActionEvent e) {
        //Variable que contiene el boton pulsado
         String comando = e.getActionCommand();

         //Si se pulso el boton Modificar
            if (comando.equals("Modificar")){
                //Se obtienen los datos de los campos
                nombre = this.Nombre.getText();
                dia = (String)Dia.getSelectedItem();
                mes = (String)Mes.getSelectedItem();
                año = (String)Año.getSelectedItem();
                tiempo = this.Tiempo.getText();
                boolean tiempocorrecto = true;
                //Se comprueba que el campo tiempo tenga un entero
                if (this.tiempo.equals(""))
                    tiempo = "0";
                try {
                    Integer.parseInt(tiempo);
                    tiempocorrecto = true;
                } catch (NumberFormatException nfe){
                    tiempocorrecto = false;
                }
                //Se comprueba que el campo tiempo sea positivo
                if(tiempocorrecto)
                    if (Integer.parseInt(tiempo)<0) 
                        tiempocorrecto = false;
                                
                //Se comprueba que el nombre no este en uso
                if ((!nombre.equals(nombreantiguo))&&(tarea.gettarea(user, lista, nombre)!=null))
                    JOptionPane.showMessageDialog (null,"El nombre de la tarea ya está en uso.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
                
                else if (((this.dia.equals("00")||this.mes.equals("00")||this.año.equals("0000")) &&
                        !(this.dia.equals("00")&&this.mes.equals("00")&&this.año.equals("0000"))))
                    JOptionPane.showMessageDialog (null,"No es una fecha valida",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
                //Se comprueba que haya nombre
                else if(!this.nombre.equals("") && tiempocorrecto){
                    //Se comprueba si se indico un tiempo estimado
                    if (this.tiempo.equals(""))
                        tiempo = "0";
                    //Se modifica la tarea
                    tarea.modificartarea(datos[2],user+lista+nombre, nombre, dia, mes, año, tiempo);
                    //Se actualizala tabla
                    ventana.ActualizarTablas(numLista);
                    //Se cierra la ventana
                    setVisible(false);
                    setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                }
                //Si el valor tiempo no es valido
                else if (!tiempocorrecto)
                    //Se muestra un aviso con el problema
                    JOptionPane.showMessageDialog (null,"El tiempo estimado debe ser un número válido.",
				"Atención", JOptionPane.INFORMATION_MESSAGE); 
                //Si no se ha indicado nombre
                else
                    //Se muestra un aviso con el problema
                    JOptionPane.showMessageDialog (null,"La tarea debe tener un nombre obligatoriamente.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);   
            }
            
    }
    
}
