/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Listas {
    
     /*
     * Variables para la conexión a la BD
     */
        private String bd = "is2"; 
        private String user = "root";
        private String url ="jdbc:mysql://localhost/" + bd;
        private Connection conexion;
        private java.sql.Statement statement;
        
        public Listas(){
            try {
                //Se añade la biblioteca de mysql
                Class.forName("com.mysql.jdbc.Driver");
                //Se conecta a la BD
                conexion = DriverManager.getConnection(url, user,"");
                //Se genera la variable que interacciona con la BD
                statement = conexion.createStatement();
            } catch (SQLException ex) {
            } catch (ClassNotFoundException ex) {
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
         * Metodo que invierte una lista de listas
         * @param listas
         * @param numlistas
         * @return cadena de listas invertida
         */
        private String[] invertir(String[] listas, int numlistas){
            String [] lists = new String[numlistas];
            int j = 0;
            for(int i = numlistas-1;i>=0;i--){
                lists[j]=listas[i];
                j++;
            }
            return lists;
        }
        
        /**
         * Metodo que devuelve las listas de un usuario
         * @param user
         * @return Cadena de String con los nombres de las listas
         */
        public String[] getListas(String user){
            String[] listas = new String[100];
            int numlista = 0;
            try { 
               //Consulta SQL, buscando un usuario que coincida user
                String consulta = "SELECT L1.Nombre nombre FROM lista L1 Where L1.NombreUsuario = \""+user+"\";";
                //Resultado de la consulta
                ResultSet result = statement.executeQuery(consulta);
                //Comprobación si hay al menos un resultado
                while (result.next()){
                    listas[numlista] = result.getString(1);
                    numlista++;
                }
            
            } catch (SQLException ex) {
                //Si no se encontraron resultados se devuelve null
                listas = null;
            }
            if (listas != null)
                    listas = invertir(listas,numlista);
            return listas;
        }
    
}
