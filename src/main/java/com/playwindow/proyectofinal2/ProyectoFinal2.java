/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.playwindow.proyectofinal2;
import Controlador.*;
import Modelo.*;
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
        PacienteDAO per = new PacienteDAO(interfaz);
        
        Paciente perso = new Paciente(5, 116, "92nosa78", "O+", 
                "ninguna");
        boolean listo = per.altasPaciente(perso);
        System.out.println(listo);
        
        
    }
}
