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
        System.out.println("iniciando");
        VentanaInicio interfaz = new VentanaInicio();
        PersonalDAO per = new PersonalDAO(interfaz);
        
        Paciente perso = new Paciente(7, 116, "calimbo", "O+", 
                "pecos");
        ResultSet listo = per.allPersonal();
        per.imprimirresultadoBusqueda(listo);
        
        //Paciente m = per.buscarPaciente(5);
        System.out.println(listo);
        
        
    }
}
