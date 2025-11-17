/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Persona;
import Vistas.VentanaInicio;
import java.sql.ResultSet;

/**
 *
 * @author daniel
 */
public class PersonaDAO extends AbstracDAO{
    Persona perso;
    VentanaInicio interfaz;
    
    public PersonaDAO(VentanaInicio faz){
        this.interfaz = faz;
    }
    
    public ResultSet listaPersonas(){
        String sql = "select * from Persona";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "Listado de personas");
    }
    
    public void imprimirRB (ResultSet rb){
        imprimirResultSet(rb);
    }
}
