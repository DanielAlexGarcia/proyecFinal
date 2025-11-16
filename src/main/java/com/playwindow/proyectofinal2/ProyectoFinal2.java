/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.playwindow.proyectofinal2;
import Controlador.*;
import Modelo.Personal;
import Vistas.*;
import java.math.BigDecimal;

/**
 *
 * @author daniel
 */
public class ProyectoFinal2 {

    public static void main(String[] args) {
        System.out.println("iniciando");
        VentanaInicio interfaz = new VentanaInicio();
        PersonalDAO per = new PersonalDAO(interfaz);
        
        //Personal perso = new Personal(5, 117, "Doctor", "clinic", 
          //      "perso", new BigDecimal(80020.89));
        boolean listo = per.bajasPersonal(1021);
        System.out.println(listo);
        
        
    }
}
