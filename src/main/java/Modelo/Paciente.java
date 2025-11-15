/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author daniel
 */
public class Paciente {
    int id;
    int dni;
    String numSeg;
    String grupSan;
    String Alergias;
    
    public Paciente(int ID, int DNI, String NumSeg, String GrupSang, String Alerg){
        this.id = ID;
        this.dni = DNI;
        this.numSeg = NumSeg;
        this.grupSan = GrupSang;
        this.Alergias = Alerg;
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

    public String getNumSeg() {
        return numSeg;
    }

    public void setNumSeg(String numSeg) {
        this.numSeg = numSeg;
    }

    public String getGrupSan() {
        return grupSan;
    }

    public void setGrupSan(String grupSan) {
        this.grupSan = grupSan;
    }

    public String getAlergias() {
        return Alergias;
    }

    public void setAlergias(String Alergias) {
        this.Alergias = Alergias;
    }
    
    @Override
    public String toString(){
        return "id "+ id+
                "dni persona "+dni+
                "numero de seguro "+ numSeg+
                "grupo sangineo "+grupSan+
                "alergias "+Alergias;
    }
    
}
