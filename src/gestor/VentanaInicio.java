package gestor;


import java.awt.*;
import java.awt.event.*;
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
 * @version 0.7
 */
public class VentanaInicio extends JFrameConFondo implements ActionListener{  
	
        //Creación de variables
	private JButton botonAceptar;
	private JButton botonCancelar;	
        private JButton NuevoUsuario;
        private JLabel labelName;
        private JTextField Name;
        private JLabel labelPass;
	private JPasswordField Pass;
	private String name;
	private String pass;
        private Inicio inicio;
        private int x;
        private int y;

	
	public VentanaInicio(){
                //constructor JFrame
		super("Gestor de Tareas Online"); 
                //Inicialización de user y pass
		this.name = "";
		this.pass = "";
                
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
                setImagen("objetos/fondo.jpg");
                
                
                //Cuadrante de introducción de nombre
		labelName = new JLabel("Nombre de Usuario:");
                labelName.setBounds((int)(x*0.4),(int)(y*0.3),300,15);
                labelName.setForeground(Color.white);
                labelName.setFont(new java.awt.Font("Arial", 1, 16)); 
		this.Name = new JTextField();
                this.Name.setFont(new java.awt.Font("Arial", 0, 16)); 
		this.Name.setBounds((int)(x*0.4),(int)(y*0.325),200,25);
                Name.setActionCommand("Login");
		Name.addActionListener(this);
                
                //Cuadrante de introducción de pass
		labelPass = new JLabel("Contraseña:");
		labelPass.setBounds((int)(x*0.4),(int)(y*0.376),300,15);
                labelPass.setForeground(Color.white);
                labelPass.setFont(new java.awt.Font("Arial", 1, 16)); 
		this.Pass = new JPasswordField();
		this.Pass.setBounds((int)(x*0.4),(int)(y*0.4),200,25);
                Pass.setFont(new java.awt.Font("Arial", 0, 16)); 
                Pass.setActionCommand("Login");
		Pass.addActionListener(this);
		
                //Constucción del boton Aceptar
		botonAceptar = new JButton("Aceptar"); 
		contenidoPanel.add(botonAceptar);
		botonAceptar.setBounds((int)(x*0.52),(int)(y*0.45),100,30);
                botonAceptar.setFont(new java.awt.Font("Arial", 1, 16)); 
                botonAceptar.setActionCommand("Login");
		botonAceptar.addActionListener(this);
                botonAceptar.setIcon(new ImageIcon(getClass().getResource("objetos/Aceptar Inicio.jpg")));
                
                //Constucción del boton Nuevo Usuario
		NuevoUsuario = new JButton("Nuevo usuario"); 
		contenidoPanel.add(NuevoUsuario);
		NuevoUsuario.setBounds((int)(x*0.83),(int)(y*0.03),146,30);
                NuevoUsuario.setFont(new java.awt.Font("Arial", 1, 16)); 
                NuevoUsuario.setActionCommand("New");
		NuevoUsuario.addActionListener(this);
                NuevoUsuario.setIcon(new ImageIcon(getClass().getResource("objetos/Nuevo Usuario.jpg")));
		
                //Se indica el tipo de borde para las cajas de texto
		JTextField text = new JTextField() {
		    @Override public void setBorder(Border border) {
		    }
		};

                //Añadiendo cuadrantes
		getContentPane().add(labelName);
		getContentPane().add(this.Name);
		getContentPane().add(labelPass);
		getContentPane().add(this.Pass);
                

                //Se indica que la ventana sea visible
		setVisible(true);
		
	}
        
        /**
         * Función que se encarga de cerrar el programa
         */
        public void close(){
                this.setCursor(Cursor.WAIT_CURSOR);
                //Si se pulso el boton Logout
                String message = "¿Está seguro de que quiere salir?";
                String title = "Logout";
                //Se da la opcion de cerrar o no
                int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                this.setCursor(Cursor.DEFAULT_CURSOR);
                //Si se decide cerrar
                if (reply == JOptionPane.YES_OPTION)
                {     
                    this.setCursor(Cursor.WAIT_CURSOR);
                    //Aviso de desconexion
                    new AvisoDesconexion();
                    //Cierre del programa
                    System.exit(0);
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                }
        }
        
        
        /**
         * Metodo que borra el interfaz grafico 
         */
        public void borrado(){
            this.remove(labelName);
            this.remove(Name);
            this.remove(labelPass);
            this.remove(Pass);
            this.remove(botonAceptar);
            this.remove(NuevoUsuario);
            this.repaint();
        }
	
        
         
        /**
         * 
         * @param e
         * Metodo que hace las acciones necesarias al pulsar algun boton
         */
	public void actionPerformed(ActionEvent e) {
            this.setCursor(Cursor.WAIT_CURSOR);
            //Variable para verificar si el login fue correcto
            boolean LoginCorrecto = false;
            //Variable que indica que boton se ha pulsado
            String comando = e.getActionCommand();
            //Si se pulso el boton aceptar
            if (comando.equals("Login")){
                //Si se han rellenado los campos necesarios
		if (!this.Name.getText().equals("") && !this.Pass.getText().equals("")){
                    //name y pass adquieren los valores indicados
                    name = this.Name.getText();
                    pass = this.Pass.getText();
                    //Creación de la clase encargada del login y NewUser
                inicio = new Inicio();
                
                boolean noconectar = false;
                //Si fallo la conexión
                while (!inicio.estado() && !noconectar){
                    this.setCursor(Cursor.WAIT_CURSOR);
                    //Si se pulso el boton Logout
                    String message = "Fallo al conectar con el servidor.\n¿Desea volver a intentar conectar?";
                    String title = "Error de conexión";
                    //Se ofrece la posibilidad de volver a conectar
                    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                    //Si no se quiere volver a conectar se cierra el programa
                    if (reply == JOptionPane.NO_OPTION){     
                        this.setCursor(Cursor.WAIT_CURSOR);
                        //System.exit(0);
                        new VentanaInicio();
                        noconectar = true;
                        //Se cierra la ventana de login
                        setVisible(false);
                        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                        this.setCursor(Cursor.DEFAULT_CURSOR);
                    }
                    //Si se dijo que si, se vuelve a intentar la conexion
                    else{
                        inicio = new Inicio();
                    }
                
                }
                        //Se comprueba el user y pass
			LoginCorrecto = inicio.Login(name, pass);
                        //Si login correcto se avisa de ello
                        if (LoginCorrecto){
                            JOptionPane.showMessageDialog (null,"Conectado.",
				"Conectado", JOptionPane.INFORMATION_MESSAGE);
                            borrado();
                            //Genera la ventana donde se muestran las lista
                            new Ventana(name,0);
                            //Se cierra la ventana de login
                            setVisible(false);
                            setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                        }
                        //Si login incorrecto se avisa que el user o pass son incorrectos
                        else
                            JOptionPane.showMessageDialog (null,"Usuario o contraseña incorrectos.",
				"Fallo al conectar", JOptionPane.INFORMATION_MESSAGE);
		}
                // Si falta algun campo que rellenar se avisa de ello
		else
		JOptionPane.showMessageDialog (null,"Debe rellenar todos los campos.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
            }
            //Si se pulso Nuevo Usuario
            else if (comando.equals("New")){
                borrado();
                //Cuadrante de introducción de nombre
		labelName = new JLabel("Nombre del nuevo Usuario:");
		labelName.setBounds((int)(x*0.4),(int)(y*0.3),300,15);
                labelName.setForeground(Color.white);
                labelName.setFont(new java.awt.Font("Arial", 1, 16)); 
		this.Name = new JTextField();
		this.Name.setBounds((int)(x*0.4),(int)(y*0.325),300,25);
                Name.setFont(new java.awt.Font("Arial", 0, 16)); 
		
                //Cuadrante de introducción de pass
		labelPass = new JLabel("Contraseña:");
		labelPass.setBounds((int)(x*0.4),(int)(y*0.375),300,15);
                labelPass.setForeground(Color.white);
                labelPass.setFont(new java.awt.Font("Arial", 1, 16)); 
		this.Pass = new JPasswordField();
		this.Pass.setBounds((int)(x*0.4),(int)(y*0.4),300,25);
                Pass.setFont(new java.awt.Font("Arial", 0, 16)); 
		
                //Constucción del boton Aceptar
		botonAceptar = new JButton("Aceptar"); 
		botonAceptar.setBounds((int)(x*0.4),(int)(y*0.45),150,30);
                botonAceptar.setFont(new java.awt.Font("Arial", 1, 16)); 
                botonAceptar.setActionCommand("NewAceptar");
		botonAceptar.addActionListener(this);
                botonAceptar.setIcon(new ImageIcon(getClass().getResource("objetos/Aceptar Nuevo Usuario.jpg")));
                		
                //Constucción del boton Cancelar
		botonCancelar = new JButton("Cancelar"); 
		botonCancelar.setBounds((int)(x*0.52),(int)(y*0.45),150,30);
                botonCancelar.setFont(new java.awt.Font("Arial", 1, 16)); 
                botonCancelar.setActionCommand("Cancelar");
		botonCancelar.addActionListener(this);
                botonCancelar.setIcon(new ImageIcon(getClass().getResource("objetos/Cancelar Nuevo Usuario.jpg")));
                
                //Añadiendo cuadrantes
		getContentPane().add(labelName);
		getContentPane().add(this.Name);
		getContentPane().add(labelPass);
		getContentPane().add(this.Pass);
		this.add(botonAceptar);
		this.add(botonCancelar);
                
                //Se vuelve a pintar la pantalla
                this.repaint();
            }
            //Si se pulso aceptar en la ventana de nuevo usuario
            else if(comando.equals("NewAceptar")){
            //Si se han rellenado los campos necesarios
		if (!this.Name.getText().equals("") && !this.Pass.getText().equals("")){
                    //name y pass adquieren los valores indicados
                    name = this.Name.getText();
                    pass = this.Pass.getText();
                    //Creación de la clase encargada del login y NewUser
                inicio = new Inicio();
                
                boolean noconectar = false;
                //Si fallo la conexión
                while (!inicio.estado() && !noconectar){
                    this.setCursor(Cursor.WAIT_CURSOR);
                    //Si se pulso el boton Logout
                    String message = "Fallo al conectar con el servidor.\n¿Desea volver a intentar conectar?";
                    String title = "Error de conexión";
                    //Se ofrece la posibilidad de volver a conectar
                    int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                    //Si no se quiere volver a conectar se cierra el programa
                    if (reply == JOptionPane.NO_OPTION){     
                        this.setCursor(Cursor.WAIT_CURSOR);
                        noconectar = true;
                        //System.exit(0);
                        new VentanaInicio();
                        //Se cierra la ventana de login
                        setVisible(false);
                        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                        this.setCursor(Cursor.DEFAULT_CURSOR);
                    }
                    //Si se dijo que si, se vuelve a intentar la conexion
                    else{
                        inicio = new Inicio();
                    }
                
                }
                        //Se comprueba el user y pass
                        if(inicio.NewUser(name, pass))
			    LoginCorrecto = inicio.Login(name, pass);
                        //Si login correcto se avisa de ello
                        if (LoginCorrecto){
                            JOptionPane.showMessageDialog (null,"Conectado.",
				"Conectado", JOptionPane.INFORMATION_MESSAGE);
                            borrado();
                            //Se genera la ventana donde se mostrara la lista
                            new Ventana(name,0);
                            //Se cierra la ventana de nuevo usuario
                            setVisible(false);
                            setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                        }
                        //Si login incorrecto se avisa que el user o pass son incorrectos
                        else
                            JOptionPane.showMessageDialog (null,"El nombre de usuario ya está en uso.",
				"Fallo al conectar", JOptionPane.INFORMATION_MESSAGE);
		}
                // Si falta algun campo que rellenar se avisa de ello
		else
		JOptionPane.showMessageDialog (null,"Debe rellenar todos los campos.",
				"Atención", JOptionPane.INFORMATION_MESSAGE);
            }
            //Si se ha pulsado cancelar
            else if(comando.equals("Cancelar")){
                new VentanaInicio();
                setVisible(false);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
         this.setCursor(Cursor.DEFAULT_CURSOR);
        }	
	
}

