/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author daniel
 */
public class DestopConFondo  extends JDesktopPane{
    
    private Image imagenFondo;

    /**
     * Constructor que carga la imagen de fondo.
     * @param rutaImagen La ruta relativa de la imagen (ej: "/Vistas/recursos/fondo.jpg").
     */
    public DestopConFondo(String rutaImagen) {
        try {
            // Asegúrate de que la ruta sea correcta, se busca en el classpath (ruta relativa al proyecto o clase)
            this.imagenFondo = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + rutaImagen);
            e.printStackTrace();
        }
    }

    // Sobreescribe el método para pintar el componente
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Llama al padre para asegurar el correcto funcionamiento de los InternalFrames
        
        if (imagenFondo != null) {
            // Dibuja la imagen cubriendo todo el JDesktopPane
            // getWidth() y getHeight() se usan para que la imagen se estire/ajuste si se redimensiona
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Sobreescribe el método para definir el tamaño preferido del JDesktopPane
    /*@Override
    /*public Dimension getPreferredSize() {
        if (imagenFondo != null) {
            // La ventana se adaptará a estas dimensiones
            int ancho = imagenFondo.getWidth(this);
            int alto = imagenFondo.getHeight(this);
            return new Dimension(ancho, alto);
        }
        return super.getPreferredSize();
    }*/
    
}
