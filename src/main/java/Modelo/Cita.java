/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author daniel
 */
public class Cita {
    int id;
    int idpac;
    int iddoc;
    String fech;
    String hora;
    String mot;
    String est;
    
    public Cita(int ID, int IDPac, int IDDoc, String Fech, String hora, String Mot, String Estad){
        this.id = ID;
        this.idpac = IDPac;
        this.iddoc = IDDoc;
        this.fech = Fech;
        this.hora = hora;
        this.mot = Mot;
        this.est = Estad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpac() {
        return idpac;
    }

    public void setIdpac(int idpac) {
        this.idpac = idpac;
    }

    public int getIddoc() {
        return iddoc;
    }

    public void setIddoc(int iddoc) {
        this.iddoc = iddoc;
    }

    public String getFech() {
        return fech;
    }

    public void setFech(String fech) {
        this.fech = fech;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMot() {
        return mot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public String getEst() {
        return est;
    }

    public void setEst(String est) {
        this.est = est;
    }
    
    @Override
    public String toString(){
        return "id "+ id+
                "id paciente "+ idpac+
                "id personal "+ iddoc+
                "fecha "+ fech+
                "hora "+ hora+
                "motivo"+ mot+
                "estado"+ est;
    }
    
}
