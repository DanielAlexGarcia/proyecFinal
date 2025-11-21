package Vistas; // O el paquete donde quieras colocarla

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * JInternalFrame que establece la imagen de fondo directamente
 * en su Content Pane, sin una clase JPanel externa.
 */
public class InternalFrameConFondoDirecto extends JInternalFrame {

    private Image imagenFondo;

    public InternalFrameConFondoDirecto(String rutaImagen, String titulo) {
        super(titulo, true, true, true, true); // Título y opciones

        try {
            // 1. Cargar la imagen
            this.imagenFondo = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + rutaImagen);
            e.printStackTrace();
        }

        // 2. Obtener el Content Pane por defecto del JInternalFrame
        // El Content Pane es un JPanel (o JRootPane.getContentPane())
        JPanel contentPane = (JPanel) this.getContentPane();
        
        // 3. Sobreescribir el paintComponent del Content Pane Anónimamente
        // ¡Esta es la clave para evitar crear una nueva clase!
        this.setContentPane(new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Siempre llama al super.paintComponent
                super.paintComponent(g); 
                
                if (imagenFondo != null) {
                    // Dibuja la imagen cubriendo el Content Pane
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        });

    }
}