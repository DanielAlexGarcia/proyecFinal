/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Controlador.AbstracDAO.StatementSetter;
import Modelo.Paciente;
import Vistas.VentanaInicio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author daniel
 */
public class PacienteDAO extends AbstracDAO{
    Paciente pac;
    VentanaInicio interfaz;
    
    public PacienteDAO(VentanaInicio faz){
    this.interfaz = faz;
}
    
    public boolean altasPaciente(Paciente paci){
        String sql = "insert into Paciente(DNIPersona, numSeguro, grupoSanineo, alergias)"
                + "values (?,?,?,?)";
        StatementSetter setter = (stmt) -> {
                    
                stmt.setInt(1, paci.getDni());
	        stmt.setString(2, paci.getNumSeg());
	        stmt.setString(3, paci.getGrupSan());
	        stmt.setString(4, paci.getAlergias());
                };
        return ejecutarCRUDTemplate(sql, setter, "insertar Paciente");
    }
    
    public Paciente buscarPaciente(int id){
        String sql = "SELECT * FROM Paciente WHERE ID = ?";
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        
	        try {
	            stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql);
	            stmt.setInt(1, id);
	            rs = stmt.executeQuery();
	            
	            // IMPORTANTE: Verificar si existe una fila antes de leer los datos
	            if (rs.next()) {
	                // Ahora sí podemos leer los datos de la fila
	                Paciente dona = new Paciente(
	                    rs.getInt("ID"),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
	                );
                              
	                return dona;
	            } else {
	                // No se encontró ningún donador con ese ID
	                System.out.println("No se encontró un paciente con el id: " + id);
	                return null;
	            }
	            
	        } catch (SQLException e) {
	            System.out.println("Error al buscar Persona: " + e.getMessage());
	            e.printStackTrace(); // Para más detalles del error
	            return null;
	        } finally {
	            // Cerrar recursos para evitar memory leaks
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	            } catch (SQLException e) {
	                System.out.println("Error al cerrar recursos: " + e.getMessage());
	            }
	        }
    }
    
    public boolean setPaciente(Paciente paci){
        String sql = " update Paciente set numSeguro = ?, alergias = ? )"
                + " where ID = ?";
        StatementSetter setter = (stmt) -> {
                stmt.setString(1, paci.getNumSeg());
	        stmt.setString(2, paci.getAlergias());
                stmt.setInt(3, paci.getId());
                };
        return ejecutarCRUDTemplate(sql, setter, "Actualizar paciente");
    }
    
}
