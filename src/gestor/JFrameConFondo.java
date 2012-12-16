/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor;

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
import java.awt.Image;
import javax.swing.JFrame;

public class JFrameConFondo extends JFrame {

    private final JPanelConFondo contenedor = new JPanelConFondo();

    public JFrameConFondo(String name) {
        super(name);
        setContentPane(contenedor);
    }

    public void setImagen(String nombreImagen) {
        contenedor.setImagen(nombreImagen);
    }

    public void setImagen(Image nuevaImagen) {
        contenedor.setImagen(nuevaImagen);
    }
}
