/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Persona;
import Modelo.Personal;
import Vistas.VentanaInicio;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author daniel
 */
public class PersonalDAO extends AbstracDAO{
    Personal personal;
    VentanaInicio interfaz;
    
    public PersonalDAO(VentanaInicio faz){
       this.interfaz = faz;
    }
    
    public boolean bajasPersonal(int id){
        String sql = "delete from Personal where ID = ?";
        StatementSetter setter = (stmt) -> {
                    stmt.setInt(1, id);
                };
        return ejecutarCRUDTemplate(sql, setter, "eliminar Personal");
    }
    
    public boolean altasPersonal(Personal personal){
        String sql = "insert into Personal(DNIPersona, rol, departamento, especialidad, salario)"
                + "values (?,?,?,?,?)";
        StatementSetter setter = (stmt) -> {
                    
                stmt.setInt(1, personal.getDni());
	        stmt.setString(2, personal.getRol());
	        stmt.setString(3, personal.getDepart());
	        stmt.setString(4, personal.getEspecial());
	        stmt.setBigDecimal(5, personal.getSalario());
                };
        return ejecutarCRUDTemplate(sql, setter, "insertar Personal");
    }
    
    public ResultSet busquedaPersonal(String nom){
        String sql = "select * from fn_buscarCoincidenciaPersonal(?)";
        StatementSetter setter = (stmt) -> {
            stmt.setString(1, nom);
                };
        return ejecutarQueryTemplate(sql, setter, "Consultar personal por nombre");
        
    }
    
    public boolean transaccionAddPersonaYPersonal(Personal p, Persona pe){
        String sql = "EXEC SP_AltaPersonalTransaccion "
                + "@Nombres = ?, " +
                    "@PrimerAP = ?, " +
                    "@SegundoAP = ?, " +
                    "@FechaNaci = ?, " +
                    "@Telefono = ?, " +
                    "@Rol  =?, " +
                    "@Departamento = ?, " +
                    "@Especialidad = ?, " +
                    "@Salario = ?";
        
        StatementSetter setter = (stmt) -> {
            stmt.setString(1, pe.getNombres());
            stmt.setString(2, pe.getPrimerAP());
            stmt.setString(3, pe.getSegundoAP());
            stmt.setString(4, pe.getFechNaci());
            stmt.setString(5, pe.getTelefono());
            stmt.setString(6, p.getRol());
            stmt.setString(7, p.getDepart());
            stmt.setString(8, p.getEspecial());
            stmt.setBigDecimal(9, p.getSalario());
            
                };
        
        return ejecutarCRUDTemplate(sql, setter, "Transaccion insertar personal y persona");
    }
    
    public ResultSet allPersonal(){
        String sql = "select * from fn_AllPeronals()";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "Consultar todo el personal");
    }
    
    public void imprimirresultadoBusqueda(ResultSet rs){
        imprimirResultSet(rs);
    }
}
