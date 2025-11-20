/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas.Paneles;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 *
 * @author daniel
 */
public class PanelConFondoInterno extends JPanel{
    private Image imagenFondo;

    public PanelConFondoInterno(String rutaImagen) {
        setOpaque(false); // Necesario para que el padre pueda dibujar (si el padre lo hiciera)
        
        URL url = getClass().getResource(rutaImagen);
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            imagenFondo = icon.getImage();
        } else {
            System.err.println("Error: Imagen de fondo no encontrada en " + rutaImagen);
            imagenFondo = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            // Dibuja la imagen escalándola para que ocupe todo el ancho y alto del panel
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
