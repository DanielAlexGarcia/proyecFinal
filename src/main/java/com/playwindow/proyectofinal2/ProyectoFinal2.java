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
        CitaDAO per = new CitaDAO(interfaz);
        
        //Cita perso = new Cita(1005, 2,1,  "2020-10-11", "14:20", 
                //"seguimiento", "camcelada");
        ResultSet listo = per.busquedaPorNombreDoc("Ana");
        per.imprimirresultadoBusqueda(listo);
        
        //Paciente m = per.buscarPaciente(5);
        //System.out.println(listo);
        
        
    }
}
