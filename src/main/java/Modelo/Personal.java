/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author daniel
 */
public class Personal {
    int id;
    int dni;
    String rol;
    String depart;
    String Especial;
    BigDecimal salario;
    
    public Personal(int ID, int DNI, String Rol, String Dep, String Espe, BigDecimal Salario){
        this.id = ID;
        this.dni = DNI;
        this.rol = Rol;
        this.depart =Dep;
        this.Especial = Espe;
        this.salario = Salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getEspecial() {
        return Especial;
    }

    public void setEspecial(String Especial) {
        this.Especial = Especial;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
    
    public String toString(){
        return "";
    }
}
