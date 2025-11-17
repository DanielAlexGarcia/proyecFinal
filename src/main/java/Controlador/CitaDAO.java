/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Cita;
import Vistas.VentanaInicio;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class CitaDAO extends AbstracDAO{
    Cita cita;
    VentanaInicio interfaz;
    
    public CitaDAO(VentanaInicio faz){
        this.interfaz = faz;
    }
    
    public boolean altasCitas(Cita cit){
        String sql = "insert into Cita (IDPaciente, IDDoctor, fecha, hora, motivo, estado)"
                + "values (?,?,?,?,?,?)";
        Date fecha = convertirStringAFechaSQL(cit.getFech());
        StatementSetter setter = (stmt) -> {
                    stmt.setInt(1, cit.getIdpac());
                    stmt.setInt(2, cit.getIddoc());
                    stmt.setDate(3, fecha);
                    stmt.setString(4, cit.getHora());
                    stmt.setString(5, cit.getMot());
                    stmt.setString(6, cit.getEst());
                };
        return ejecutarCRUDTemplate(sql, setter, "agregar cita");
    }
    
    public boolean cambiosCita(Cita cit){
        String sql = "EXEC SP_CambiosCita @ID = ?, @IDDoctor = ?, "
                + "@Fecha = ?, @Hora = ?, @Estado = ? "; 
        Date fecha = convertirStringAFechaSQL(cit.getFech());
        StatementSetter setter = (stmt) -> {
                    stmt.setInt(1, cit.getId());
                    stmt.setInt(2, cit.getIddoc());
                    stmt.setDate(3, fecha);
                    stmt.setString(4, cit.getHora());
                    stmt.setString(5, cit.getEst());
                };
        return ejecutarCRUDTemplate(sql, setter, "actualizar cita");
    }
    
    public ResultSet busquedaPorNombreDoc (String nomDoctor){
        String sql = "select * from fn_buscarCitasNomPersonal(?)";
        StatementSetter setter = (stmt) -> {
            stmt.setString(1, nomDoctor);
                };
        return ejecutarQueryTemplate(sql, setter, "Busqueda por nombre personal");
    }
    
    public ResultSet busquedaPorNombrePac (String nomPaciente){
        String sql = "select * from fn_buscarCitasNomPaciente(?)";
        StatementSetter setter = (stmt) -> {
            stmt.setString(1, nomPaciente);
                };
        return ejecutarQueryTemplate(sql, setter, "Busqueda por nombre paciente");
    }
    
    public ResultSet busquedaPorEstadoCita (String estado){
        String sql = "select * from fn_buscarCitasEstado (?)";
        StatementSetter setter = (stmt) -> {
            stmt.setString(1, estado);
                };
        return ejecutarQueryTemplate(sql, setter, "Busqueda por estado de la cita");
    }
    
    public ResultSet allCitas(){
        String sql = "select * from fn_TodasLasCitas ()";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "todas las citas");
    }
    
    public Cita BuqCitaPorID(int id){
        String sql = "select * from Cita where id = ?";
        StatementSetter setter = (stmt) -> {
            stmt.setInt(1, id);
                };
        ResultSet n = ejecutarQueryTemplate(sql, setter, "todas las citas");
        try {
            ArrayList<String> li = resultSetToList(n);
            Cita c = convertArrayListTOCita(li);
            return c;
        } catch (Exception e) {
            System.out.println("Error al intentar convertir resultset a arraylist");
            return null;
        }
    }
    
    private Cita convertArrayListTOCita(ArrayList<String> datos){
        Cita c = new Cita(Integer.parseInt(datos.get(0)),
                Integer.parseInt(datos.get(1)),
                Integer.parseInt(datos.get(2)),
                datos.get(3),
                datos.get(4),
                datos.get(5), 
                datos.get(6));
        return c;
    }
    
    private static ArrayList<String> resultSetToList(ResultSet rs) throws SQLException {

    ArrayList<String> lista = new ArrayList<>();

    // Primero verificamos si existe una fila
    if (rs.next()) {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        // Recorrer todas las columnas de la fila
        for (int i = 1; i <= columnCount; i++) {
            Object valor = rs.getObject(i);
            lista.add(valor != null ? valor.toString() : null);
        }
    }

    return lista;
}
    
    public static Date convertirStringAFechaSQL(String fechaString) {
        
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-dd-MM");

        try {
            LocalDate fechaLocal = LocalDate.parse(fechaString, formatoEntrada);
                System.out.println(fechaLocal);
            return Date.valueOf(fechaLocal);

        } catch (java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear la fecha. Asegúrese que el formato sea 'yyyy-dd-mm'. String: " + fechaString);
            return null;
        }
    }
    public void imprimirresultadoBusqueda(ResultSet rs){
        imprimirResultSet(rs);
    }
}
