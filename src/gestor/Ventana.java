/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
public class Ventana extends JFrameConFondo implements ActionListener{
    
    //Creación de variables
    private JButton botonLogout;
    private JButton botonNewTarea;
    private JButton botonModificar;
    private JButton botonEliminarTarea;
    private JButton botonNuevaLista;
    private JButton botonCargar;
    private int x;
    private int y;
    private String name;
    private String[] listas = new String[100];
    private String[] tareas = new String[1000];
    private DefaultTableModel dtm = new DefaultTableModel(); 
    private JTable table = new JTable(dtm);
    private JScrollPane jsp;
    private int xlogout;
    private int numLista = 0;
    
    
    public Ventana(String name, int numLista){
        //constructor JFrame
	super("Gestor de Tareas Online"); 
        
        //Almacenamiento del user 
        this.name = name;
        this.numLista = numLista;
        
        //Generación del objeto que interactua con la bbdd para listas
        Listas lista = new Listas();
                boolean salir = false;
        //Si la conexion con la bbdd no fue posible
        while (!lista.estado() && !salir){
            this.setCursor(Cursor.WAIT_CURSOR);
            String message = "Fallo al conectar con el servidor.\n¿Desea volver a intentar conectar?";
            String title = "Error de conexión";
            //Se da la opcion de reintentar la conexion
            int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
            this.setCursor(Cursor.DEFAULT_CURSOR);
            //Si no se quiere reintentar la conexion
            if (reply == JOptionPane.NO_OPTION){     
                this.setCursor(Cursor.WAIT_CURSOR);
                salir=true;
                new VentanaInicio();
                setVisible(false);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                this.setCursor(Cursor.DEFAULT_CURSOR);
            }
            else{
                //Se reintenta la conexion
                lista = new Listas();
            }    
         }
        //Se obtienen las listas del usuario
        listas = lista.getListas(name);
        
        //Dimensiones de la pantalla
        Dimension dim=super.getToolkit().getScreenSize(); 
        x = (int)dim.width;
        y = (int)dim.height;
        
        //Opciones de ventana
	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //se añade la opción de preguntar antes de salir
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                close();
            }
        });
        setLayout(null);
	setSize(350,350);
	setExtendedState(MAXIMIZED_BOTH);
	Container contenidoPanel = getContentPane();
        contenidoPanel.setBackground(Color.LIGHT_GRAY);
  
        //Se pinta la tabla de la Lista Principal
        PaintTable(numLista);
        
        //Se genera un panel para opciones
        JPanelConFondo opciones = new JPanelConFondo();
        opciones.setLayout(null);
        opciones.setBackground(Color.LIGHT_GRAY);
        opciones.setImagen("objetos/fondo1.jpg");
        //Se calcula el ancho del panel de opciones
        xlogout = x/2;
       
        //Constucción del boton Logout
	botonLogout = new JButton("Logout"); 
	opciones.add(botonLogout);
	botonLogout.setBounds((int)(xlogout*0.79),(int)(y*0.02),100,30);
        botonLogout.setFont(new java.awt.Font("Arial", 0, 20)); 
        botonLogout.setActionCommand("Logout");
        botonLogout.addActionListener(this);
        botonLogout.setIcon(new ImageIcon(getClass().getResource("objetos/Logout.jpg")));
        
        //Constucción del boton Nueva tarea
	botonNewTarea = new JButton("Nueva Tarea"); 
	opciones.add(botonNewTarea);
	botonNewTarea.setBounds((int)(xlogout*0.3),(int)(y*0.1),225,40);
        botonNewTarea.setFont(new java.awt.Font("Arial", 0, 25)); 
        botonNewTarea.setActionCommand("NewTarea");
        botonNewTarea.addActionListener(this);
        botonNewTarea.setIcon(new ImageIcon(getClass().getResource("objetos/Nueva Tarea.jpg")));
        
                
        //Constucción del boton Modificar tarea
	botonModificar = new JButton("Modificar Tarea"); 
	opciones.add(botonModificar);
	botonModificar.setBounds((int)(xlogout*0.3),(int)(y*0.2),225,40);
        botonModificar.setFont(new java.awt.Font("Arial", 0, 25)); 
        botonModificar.setActionCommand("Modificar");
        botonModificar.addActionListener(this);
        botonModificar.setIcon(new ImageIcon(getClass().getResource("objetos/Modificar Tarea.jpg")));
        
        
        //Constucción del boton Eliminar tarea
	botonEliminarTarea = new JButton("Eliminar Tarea"); 
	opciones.add(botonEliminarTarea);
	botonEliminarTarea.setBounds((int)(xlogout*0.3),(int)(y*0.3),225,40);
        botonEliminarTarea.setFont(new java.awt.Font("Arial", 0, 25)); 
        botonEliminarTarea.setActionCommand("Eliminar");
        botonEliminarTarea.addActionListener(this);
        botonEliminarTarea.setIcon(new ImageIcon(getClass().getResource("objetos/Eliminar Tarea.jpg")));
        
        
        //Se añade el panel de opciones
        add(opciones);
        
        //Se hace visible la ventana
        setVisible(true);
    }

    
    /**
     * Metodo que pinta la tabla de la lista de tareas
     */
    public void PaintTable(int numLista){
        //Se crea el titulo de la tabla
        JLabel titulo;
        titulo = new JLabel(listas[numLista]);
        titulo.setBounds((int)(x*0.18),(int)(y*0.05), 300, 30);
        titulo.setForeground(Color.white);
        titulo.setFont(new java.awt.Font("Tahoma", 0, 25)); 

        
        //Se crea el panel donde se ubicara la tabla
        JPanelConFondo tabla = new JPanelConFondo();
        tabla.add(titulo);
	tabla.setBackground(Color.LIGHT_GRAY);
        tabla.setImagen("objetos/fondo2.jpg");
        //Se obliga que las celdas no sean editables
        dtm = new DefaultTableModel(){
            public boolean isCellEditable (int fila, int columna) {
                return false;
            }
        };
        //Se crea la tabla, estara compuesta por la tabla por defecto dtm
        table = new JTable(dtm);
   
        //Se crea el objeto que interactuara con la bbdd para tareas    
        Tareas tarea = new Tareas();
        //Se obtienen las tareas de la lista
        tareas = tarea.gettareas(name, listas[numLista]);
        
        //Se calculan el numero de tareas(para pintar la tabla lo más ajustada posibe
        int numtareas=0;
        for(int j=0;j<tareas.length && tareas[j]!=null;j++){numtareas++;}
        //Si hay menos de 19 tareas se indica que se pinten 19 tareas 
        if (numtareas<19)
            numtareas=19;
        
        //Variable que contendra las 3 columnas (Nombre, Fecha, Tiempo)
        String [][] contenidoTabla = new String[3][numtareas];        
        for(int j=0;j<tareas.length && tareas[j]!=null;j++){
            //Se piden los datos de la tarea
            String [] fila = tarea.gettarea(name, listas[numLista], tareas[j]);
            //Se almacenan en sus respectiva fila
            contenidoTabla[0][j] = tareas[j];
            contenidoTabla[1][j] =fila[0];
            contenidoTabla[2][j] =fila[1]; 
        }    
        
        dtm = new DefaultTableModel(){
            public boolean isCellEditable (int fila, int columna) {
                return false;
            }
        };
                
        //Creacion de las cabeceras de la tabla
        dtm.addColumn("Nombre",contenidoTabla[0]);
        dtm.addColumn("Fecha de Finalización",contenidoTabla[1]);
        dtm.addColumn("Tiempo Límite",contenidoTabla[2]);


        
        //Creación de la tabla
        table = new JTable(dtm);
        //Se elimina la posibilidad de posicionar columnas
        table.getTableHeader().setReorderingAllowed(false);
        //Se limita la posibilidad del numero de filas a seleccionar
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        //Opciones de la tabla
        table.setRowHeight(30);
        table.getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 15)); 
        table.setFont(new java.awt.Font("Tahoma", 0, 14));
        table.setVisible(true); 
        
        //Se introduce un scroll para la tabla
        jsp = new JScrollPane(table);
        //Opciones del scroll
        jsp.setLayout(null);
        jsp.setBounds((int)(x*0.06),(int)(y*0.1), 450, 599);
        jsp.setVisible(true);
        jsp.setLayout(new ScrollPaneLayout()); 
           
        //Opciones del panel que contiene la tabla
        tabla.setLayout(null);
        tabla.add(jsp);  
        
        
        //Opcion para que se muestre la tabla
        setLayout(new GridLayout()); 
        //Se añade la tabla
        add(tabla);        
    }

    /**
    * Función que se encarga de cerrar el programa
    */
    public void close(){
        this.setCursor(Cursor.WAIT_CURSOR);
        String message = "¿Está seguro de que quiere salir?";
        String title = "Logout";
        //Se pregunta si se desea desconectar
        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        this.setCursor(Cursor.DEFAULT_CURSOR);
        //Si acepta 
        if (reply == JOptionPane.YES_OPTION)
        {
            this.setCursor(Cursor.WAIT_CURSOR);
            //Se muestra el aviso de que se cierra
            new AvisoDesconexion();
            //Se cierra el programa
            System.exit(0);
            //Se cierra la ventana de login
            setVisible(false);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
            this.setCursor(Cursor.DEFAULT_CURSOR);
        }
   }
    
    
    /**
     * Metodo que actualiza la tabla de tareas
     */
    public void ActualizarTablas(int numLista){
        //Se crea una nueva ventana
        new Ventana(name,numLista);
        //Se cierra la actual ventana
        setVisible(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
    }
            
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Variable que contiene el boton pulsado
         String comando = e.getActionCommand();
            
            //Si se pulso el boton logout
            if (comando.equals("Logout")){
                this.setCursor(Cursor.WAIT_CURSOR);
                String message = "¿Está seguro de que quiere cerrar sesión?";
                String title = "Logout";
                //Se pregunta si se desea desconectar
                int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                this.setCursor(Cursor.DEFAULT_CURSOR);
                //Si acepta 
                if (reply == JOptionPane.YES_OPTION){
                    this.setCursor(Cursor.WAIT_CURSOR);    
                    new VentanaInicio();
                    setVisible(false);
                    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                } 
            }
            //Si se pulso el boton nueva tarea
            else if (comando.equals("NewTarea")){
                new VentanaTarea(this.listas[numLista],this.name, this,numLista);
            }
            //Si se pulso el boton modificar tarea
            else if (comando.equals("Modificar")){
                //Se comprueba si se ha seleccionado una tarea
                if ((table.getSelectedRow()== -1)||(tareas[table.getSelectedRow()]== null))
                JOptionPane.showMessageDialog (null,"Debe seleccionar una tarea valida para poder ser modificada.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
                else
                    new VentanaMTarea(this.name,this.listas[numLista],tareas[table.getSelectedRow()], this,numLista);
                    
            }
            //Si se pulso el boton eliminar tarea
            else if (comando.equals("Eliminar")){
                //Se comprueba si seselecciono una tarea
                if ((table.getSelectedRow()== -1)||(tareas[table.getSelectedRow()]== null))
                JOptionPane.showMessageDialog (null,"Debe seleccionar una tarea valida para poder ser eliminada.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
                else{
                    this.setCursor(Cursor.WAIT_CURSOR);
                    String message = "¿Está seguro de que quiere Eliminar la tarea "+tareas[table.getSelectedRow()]+"?";
                    String title = "Eliminar";
                    //Se pide confirmacion para eliminar la tarea
                    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                    //Si se confirma
                    if (reply == JOptionPane.YES_OPTION){
                        this.setCursor(Cursor.WAIT_CURSOR);
                        //Se crea el objeto para actuar contra la bbdd
                        Tareas tarea = new Tareas();
                        boolean salir = false;
                //Si no se ha conectado
                while (!tarea.estado() && !salir){
                    this.setCursor(Cursor.WAIT_CURSOR);
                    message = "Fallo al conectar con el servidor.\n¿Desea volver a intentar conectar?";
                    title = "Error de conexión";
                    //Se ofrece la opcion de reconectar
                    reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                    //Si rechazo reconectar
                    if (reply == JOptionPane.NO_OPTION){     
                        this.setCursor(Cursor.WAIT_CURSOR);
                        salir=true;
                        new VentanaInicio();
                        //Se cierra la ventana de login
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
                        //Si se consigue la conexion
                        if(tarea!=null){
                            tarea.eliminartarea(name+listas[0]+tareas[table.getSelectedRow()]);
                            ActualizarTablas(0);
                        }
                        //Si no se consigue la conexión
                        else
                            JOptionPane.showMessageDialog (null,"No se pudo realizar la acción.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
                        this.setCursor(Cursor.DEFAULT_CURSOR);
                    }
                }
            }
            
    }
    
    
}
