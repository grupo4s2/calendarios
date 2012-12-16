package gestor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class Inicio {
    
    /*
     * Variables para la conexión a la BD
     */
        private String bd = "is2"; 
        private String user = "root";
        private String url ="jdbc:mysql://localhost/" + bd;
        private Connection conexion;
        private java.sql.Statement statement;
    
    
    /*
     * En el constructor se conecta a la base de datos
     */
    public Inicio(){
            try {
                //Se añade la biblioteca de mysql
                Class.forName("com.mysql.jdbc.Driver");
                //Se conecta a la BD
                conexion = DriverManager.getConnection(url, user,"");
                //Se genera la variable que interacciona con la BD
                statement = conexion.createStatement();
            } catch (SQLException ex) {
                //Logger.getLogger(VentanaInicio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(VentanaInicio.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
   /**
    * Metodo que indica el estado de la conexion
    * @return true si se ha conectado, false en otro caso
    */
    public boolean estado(){
        return (!(statement == null));
    }
    
    
    /**
     * Metodo que dados un user y un pass realiza la conexión.
     * @param name
     * @param pass
     * @return True si consigue conectarse, false para cualquier otro caso
     */
    public boolean Login(String name, String pass){
            boolean logueado = false;
            try { 
               //Consulta SQL, buscando un usuario que coincida user y pass
                String consulta = "SELECT U.Nombre FROM usuario U WHERE ((U.Nombre = \""+name+"\")AND"
                        + "(U.Contraseña = \""+pass+"\"));";
                //Resultado de la consulta
                ResultSet result = statement.executeQuery(consulta);
                //Comprobación si hay al menos un resultado
                if (result.next()){
                    logueado = true;
                }
            
            } catch (SQLException ex) {
                //Si no se consigue la conexion se indica
                logueado = false;
            }                
            return logueado;
        } 
        
    
        /**
         * Metodo que dados un user y un pass realiza crea un nuev usuario.
         * @param name
         * @param pass
         * @return True si consigue crear el nuevo usuario, false para cualquier otro caso
         */
        public boolean NewUser(String name,String pass){
            boolean logueado = false;
            try {
                //Consulta para ver si el nombre de usuario ya esta cogido
                ResultSet rs = statement.executeQuery("SELECT U.Nombre FROM usuario U WHERE U.Nombre = \""+name+"\";");
                //Se comprueba que no exista el usuario
                if (!rs.next()){
                    //Se crea el usuario
                    statement.executeUpdate("INSERT INTO usuario VALUES ('"+name+"','"+pass+"')");
                    //Se crea su Lista Principal
                    statement.executeUpdate("INSERT INTO lista VALUES  ('"+name+"Lista Principal','Lista Principal','"
                        +name+"')");
                    logueado = true;
            }
            } catch (SQLException ex) {
                //Si la consulta falla se indica
                logueado = false;
            }
           return logueado;
        }
}
