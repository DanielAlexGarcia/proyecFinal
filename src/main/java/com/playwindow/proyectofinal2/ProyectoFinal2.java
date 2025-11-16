/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.playwindow.proyectofinal2;
import Controlador.*;
import Modelo.*;
import Vistas.*;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author daniel
 */
public class ProyectoFinal2 {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VentanaInicio ventana = VentanaInicio.getInstance(); 
                ventana.setVisible(true);
                ventana.setInstance(ventana);
            }
        });
        
        
    }
}
