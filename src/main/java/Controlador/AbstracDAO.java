/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Vistas.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author daniel
 */
public abstract class AbstracDAO {
    
    
    // Se recomienda usar el tipo de Interfaz base si existe, o solo la clase VentanaInicio
    protected VentanaInicio Interfaz; 
    
    protected void setVentanaInicio(VentanaInicio interfaz){
        this.Interfaz = interfaz;
    }
    
    protected final boolean ejecutarCRUDTemplate(String sql, StatementSetter setter, String nombreEntidad) {
        try (PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql)) {
            
            // PASO ABSTRACTO: Las subclases definen cómo se llenan los parámetros del PreparedStatement.
            setter.setParameters(stmt);
            
            // PASO FIJO: Ejecutar la operación y verificar las filas afectadas.
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            // PASO FIJO: Manejo de excepciones y notificación a la UI
            System.out.println("Error al " + nombreEntidad + ": " + e.getMessage());
            if (Interfaz != null) {
                // Asumo que la VentanaInicio tiene un método ShowMessage estático o accesible
                // Si ShowMessage es estático, debe estar en Interfaz (VentanaInicio)
                // Si no, usamos la instancia:
    //Interfaz.ShowMessage("Error al " + nombreEntidad + ": \n" + e.getMessage());
            }
            return false;
        }
    }
    
    @FunctionalInterface
    protected interface StatementSetter {
        void setParameters(PreparedStatement stmt) throws SQLException;
    }
    
    protected ResultSet listaEntity(String entity) {
	        String sql = "SELECT * FROM "+ entity;
	        try {
	        	PreparedStatement stmt = ConexionBD.getInstancia().getConnection().prepareStatement(sql);
	            return stmt.executeQuery();
	        } catch (SQLException e) {
	            System.out.println("Error al listar "+entity+": " + e.getMessage());
	            
	            return null;
	        }
	    } 
    
    
    // Método para ejecutar comandos directos
    protected final boolean ejecutarComandoDirecto(String comando){
        // Mantenemos la implementación original ya que no se ajusta al Template CRUD
         java.sql.Connection conn = ConexionBD.getInstancia().getConnection();
            try (java.sql.Statement directStmt = conn.createStatement()) {
            directStmt.execute(comando);
            return true;
        } catch (java.sql.SQLException e) {
            System.err.println("Error al ejecutar comando directo: " + e.getMessage());
            return false;
        }
    }
    
    protected final ResultSet ejecutarQueryTemplate(String sql, StatementSetter setter, String nombreOperacion) {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        // PASO FIJO: Obtener la conexión y preparar la sentencia
        // No usamos try-with-resources aquí para mantener el stmt y la conexión abiertos.
        Connection conn = ConexionBD.getInstancia().getConnection();
        stmt = conn.prepareStatement(sql);
        
        // PASO ABSTRACTO: Establecer los parámetros (definido por el llamador)
        setter.setParameters(stmt);
        
        // PASO FIJO: Ejecutar la consulta y retornar el ResultSet
        rs = stmt.executeQuery();
        
        // IMPORTANTE: En este punto, no cerramos ni stmt ni rs.
        return rs;
        
    } catch (SQLException e) {
        // PASO FIJO: Manejo de excepciones
        System.out.println("Error al " + nombreOperacion + ": " + e.getMessage());
        
        // Notificación a la UI (asumiendo que 'Interfaz' es la ventana principal)
        // if (Interfaz != null) {
        //     Interfaz.ShowMessage("Error al " + nombreOperacion + ": \n" + e.getMessage());
        // }
        
        // Limpieza de recursos EN CASO DE ERROR
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException ex) {
            System.err.println("Error al cerrar recursos después de fallo: " + ex.getMessage());
        }
        
        return null;
    }
    }
    protected void imprimirResultSet(ResultSet rs) {
		if (rs == null) {
			System.out.println("ERROR: El ResultSet proporcionado es nulo.");
			return;
		}

		try {
			// Obtener metadatos del ResultSet para obtener nombres de columnas
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			final int colWidth = 20; // Ancho fijo para las columnas para alineación en consola
			
			// Preparar los StringBuilders para encabezado y separador
			StringBuilder header = new StringBuilder();
			StringBuilder separator = new StringBuilder();
			
			// 1. Construir el encabezado y el separador
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnName(i);
				
				// Aplicar formato para alinear el texto (justificado a la izquierda)
				String paddedColumnName = String.format("%-" + colWidth + "s", columnName);
				
				// Truncar si el nombre es demasiado largo
				paddedColumnName = paddedColumnName.substring(0, Math.min(colWidth, paddedColumnName.length()));
				
				header.append(paddedColumnName).append(" | ");
				separator.append("-".repeat(colWidth)).append("-+-");
			}
			
			String finalSeparator = separator.substring(0, separator.length() - 3);
			String finalHeader = header.substring(0, header.length() - 3);

			// 2. Imprimir encabezado y separador
			System.out.println("\n" + finalSeparator);
			System.out.println(finalHeader);
			System.out.println(finalSeparator);
			
			// 3. Imprimir los datos
			int rowCount = 0;
			// El cursor de ResultSet ya está antes de la primera fila.
			while (rs.next()) {
				StringBuilder row = new StringBuilder();
				for (int i = 1; i <= columnCount; i++) {
					// Obtener el valor como String (o "NULL" si es DB null)
					Object value = rs.getObject(i);
					String cellValue = (value != null) ? value.toString() : "NULL";
					
					// Aplicar formato y truncamiento al valor de la celda
					String paddedCellValue = String.format("%-" + colWidth + "s", cellValue);
					paddedCellValue = paddedCellValue.substring(0, Math.min(colWidth, paddedCellValue.length()));
					
					row.append(paddedCellValue).append(" | ");
				}
				System.out.println(row.substring(0, row.length() - 3));
				rowCount++;
			}
			
			// 4. Imprimir pie de página
			System.out.println(finalSeparator);
			System.out.println("Total de filas: " + rowCount + "\n");
			
			// Nota: Se asume que el método que llama a ejecutarQueryTemplate() y obtiene el ResultSet
			// es el responsable de cerrarlo.
			
		} catch (SQLException e) {
			System.err.println("Error SQL al intentar imprimir el ResultSet: " + e.getMessage());
		}
	}
}
