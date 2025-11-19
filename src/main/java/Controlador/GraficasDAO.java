/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vistas.VentanaInicio;
import java.sql.ResultSet;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author daniel
 */
public class GraficasDAO extends AbstracDAO{
    VentanaInicio faz;
    DefaultCategoryDataset dataset;
    
    
    public GraficasDAO (VentanaInicio fa){
        this.Interfaz = fa;
        dataset = new DefaultCategoryDataset();
    }
    
    
    
    
}
