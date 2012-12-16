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
public class Tareas {
        
     /*
     * Variables para la conexión a la BD
     */
        private String bd = "is2"; 
        private String user = "root";
        //Se añade un campo extra a la URI, para que admita transformaciones a null
        private String url ="jdbc:mysql://localhost/" + bd+"?zeroDateTimeBehavior=convertToNull";
        private Connection conexion;
        private java.sql.Statement statement;
        
        public Tareas(){
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
         * Metodo que devuelve las tareas asociadas a una lista
         * @param name
         * @param lista
         * @return Cadena de String con los nombres de las tareas
         */
        public String[] gettareas(String name,String lista){
            //Variable que contiene los nombres de las tareas, tamaño maximo 1000 tareas
            String[] tareas = new String[1000];
            //Numero de tareas
            int numtareas = 0;
            try { 
               //Consulta SQL, buscando una tarea con ideLista igual al de la lista dada
                String consulta = "SELECT T1.Nombre nombre FROM tarea T1 Where T1.IdLista = \""+name+lista+"\";";
                //Resultado de la consulta
                ResultSet result = statement.executeQuery(consulta);
                //Comprobación si hay al menos un resultado
                while (result.next()){
                    tareas[numtareas] = result.getString(1);
                    numtareas++;
                }
            
            } catch (SQLException ex) {
                //Si no hay tareas se devuelve null
                tareas = null;
            }
            return tareas;
        }
        
        /**
         * Metodo que da los datos de una tarea
         * @param user
         * @param lista
         * @param name
         * @return cadena de string con los datos de una tarea
         */
        public String[] gettarea(String user,String lista,String name){
            //Variable donde se almacenan los datos de la tarea
            String[] datos = new String[3];
            try { 
               //Consulta SQL, buscando una tarea que coincida el identificador de la tarea
                String consulta = "SELECT T1.Nombre nombre FROM tarea T1 Where T1.Identificador = \""+user+lista+name+"\";";
                //Resultado de la consulta
                ResultSet result = statement.executeQuery(consulta);
                //Comprobación si hay al menos un resultado
                if(result.next()){
                    consulta = "SELECT T1.FechaLimite fecha FROM tarea T1 Where T1.Identificador = \""+user+lista+name+"\";";
                    //Resultado de la consulta
                    result = statement.executeQuery(consulta);
                    
                    if(result.next()){
                        datos[0] = result.getString(1);
                        //Si la fecha es null, se actualiza con los valores indicados
                        if (datos[0]==null)
                            datos[0]="0000-00-00";
                        consulta = "SELECT T1.TiempoEstimado tiempo FROM tarea T1 Where T1.Identificador = \""+user+lista+name+"\";";
                        result = statement.executeQuery(consulta);
                        //Resultado de la consulta
                        if(result.next()){
                            datos [1] = result.getString(1);
                        }
                        datos [2] = user+lista+name;
                    }
                }
                else 
                    datos = null;
            
            } catch (SQLException ex) {
                //Si la consulta falla se devuelve null
                datos = null;
            }
            return datos;
        }
        
        /**
         * Metodo que añade una tarea nueva
         * @param id
         * @param name
         * @param dia
         * @param mes
         * @param año
         * @param tiempo
         * @return true si se ha añadido la tarea, false en otro caso
         */
        public boolean addtarea(String id,String name,String dia,String mes, String año,String tiempo){
            boolean terminado = true;
            try { 
               //Consulta SQL, insertado una nueva tarea
                String consulta = "INSERT INTO tarea(Identificador, Nombre, FechaLimite, TiempoEstimado, IdLista)"+
                        "VALUES (\""+id+name+"\",\""+name+"\",\""+año+"/"+mes+"/"+dia+"\","+tiempo+",\""+id+"\");";
                //Resultado de la consulta
                int result = statement.executeUpdate(consulta);
            } catch (SQLException ex) {
                //Si la consulta falla se indica
                terminado = false;
            }
            return terminado;
        }
        
        /**
         * Metodo que modifica una tarea existente
         * @param idanterior
         * @param id
         * @param name
         * @param dia
         * @param mes
         * @param año
         * @param tiempo
         * @return true si se ha modificado, false en otro caso
         */
        public boolean modificartarea(String idanterior,String id,String name,String dia,String mes, String año,String tiempo){
            boolean terminado = true;
            try { 
               //Consulta SQL, para actualizar la tabla de tareas
                String consulta = "UPDATE tarea SET Identificador =\""+id+"\",Nombre=\""+name+"\","
                                +"FechaLimite=\""+año+"/"+mes+"/"+dia+"\",TiempoEstimado="+tiempo+
                                " Where Identificador = \""+idanterior+"\";";
                //Resultado de la consulta
                int result = statement.executeUpdate(consulta);
            } catch (SQLException ex) {
                //Si la consulta falla
                terminado = false;
            }
            return terminado;
        }
        
        /**
         * Metodo para eliminar una tarea
         * @param id 
         */
        public void eliminartarea(String id){
            try { 
               //Consulta SQL, para borrar una tarea
                String consulta = "DELETE FROM tarea"+
                                " Where Identificador = \""+id+"\";";
                //Resultado de la consulta
                int result = statement.executeUpdate(consulta);
            } catch (SQLException ex) {
            }
        }
    
}
