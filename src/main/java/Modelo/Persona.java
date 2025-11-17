/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author daniel
 */
public class Persona {
    int DNI;
    String nombres;
    String primerAP;
    String segundoAP;
    Date fechNaci;
    String telefono;
    
    public Persona (int dni, String Nombres, String PerimerAP, String segAP, 
            Date FechNaci, String Telefono){
        this.DNI = dni;
        this.nombres = Nombres;
        this.primerAP = PerimerAP;
        this.segundoAP = segAP;
        this.fechNaci = FechNaci;
        this.telefono = Telefono;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerAP() {
        return primerAP;
    }

    public void setPrimerAP(String primerAP) {
        this.primerAP = primerAP;
    }

    public String getSegundoAP() {
        return segundoAP;
    }

    public void setSegundoAP(String segundoAP) {
        this.segundoAP = segundoAP;
    }

    public Date getFechNaci() {
        return fechNaci;
    }

    public void setFechNaci(Date fechNaci) {
        this.fechNaci = fechNaci;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    
}
