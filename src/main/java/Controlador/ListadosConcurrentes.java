/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Paciente;
import Modelo.Persona;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author daniel
 */
public class ListadosConcurrentes extends AbstracDAO{
    ArrayList<String[]> listaPersonas;
    ArrayList<String[]> listaPersonal;
    ArrayList<String[]> listaPacientes;
    ArrayList<String> listaEstados = new ArrayList<>();
    
    ResultSet listadoPersonas;
    ResultSet listadoPersonal;
    ResultSet listadoPacientes;
    
    
    
    public ListadosConcurrentes(){
        listadoPersonas = listaPersona();
        listadoPersonal = listaPersonal();
        listadoPacientes = listaPaciente();
        
        listaEstados.add("Reprogramada");
        listaEstados.add("Completada");
        listaEstados.add("Programada");
        listaEstados.add("Cancelada");
        
        try {
            listaPacientes = transformarResultSet(listadoPacientes);
            listaPersonal = transformarResultSet(listadoPersonal);
            listaPersonas = transformarResultSet(listadoPersonas);
        } catch (Exception e) {
            System.out.println("Error al obtener listados");
        }
        System.out.println("listas completas");
    }
    
    public void imprimirArrayListBidimensional(ArrayList<String[]> listados){
        for (String[] list : listados){
            System.out.println(list[0] + " : "+ list[1]);
        }
    }
    public void imprimirArrayList(ArrayList<String> listados){
        for (String list : listados){
            System.out.println(list);
        }
    }
    
    private ResultSet listaPersonal(){
        String sql = "select * from fn_AllPeronalList()";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "Consultar listado de personal");
    }
    
    public void actualizarPacientes(){
        listaPacientes = null;
        listadoPacientes = null;
        listadoPacientes = listaPaciente();
        try {
            listaPacientes = transformarResultSet(listadoPacientes);
            System.out.println("Lista de pacientes actualizada");
        } catch (Exception e) {
            System.out.println("Error al actualizar datos");
        }
    }
    
    private ResultSet listaPaciente(){
        String sql = "select * from fn_AllPacienteList()";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "Consultar listado de pacientes");
    }
    
    private ResultSet listaPersona(){
        String sql = "select * from fn_AllPeronaList()";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "Consultar listado de personas");
    }
    
    public static DefaultTableModel resultSetToTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Crear modelo de tabla
        DefaultTableModel modelo = new DefaultTableModel();

        // Añadir nombres de columnas
        for (int i = 1; i <= columnCount; i++) {
            modelo.addColumn(metaData.getColumnLabel(i));
        }

        // Añadir filas del ResultSet
        while (rs.next()) {
            Object[] fila = new Object[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                fila[i - 1] = rs.getObject(i);
            }

            modelo.addRow(fila);
        }

        return modelo;
    }
    
    public static boolean fechaValida(String fecha) {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Si no es válida, lanzará una excepción
        LocalDate.parse(fecha, formatter);

        return true;
    } catch (DateTimeParseException e) {
        return false;
    }
}
    public static boolean horaValida(String hora) {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime.parse(hora, formatter);
        return true;
    } catch (DateTimeParseException e) {
        return false;
    }
}

    
    public static ArrayList<String[]> transformarResultSet(ResultSet rs) throws SQLException {
        
        // El ArrayList final que almacenará los datos de personas.
        ArrayList<String[]> listaPersonas = new ArrayList<>();
        
        // El bucle principal para recorrer todas las filas del ResultSet.
        while (rs.next()) {
            
            int id = rs.getInt(1);
            String nombres = rs.getString(2);
            String primerAP = rs.getString(3);
            String segundoAP = rs.getString(4);
            
            // 2. Obtener el resto de los datos
            // Usamos getString para FechaNaci para mantener todo como String
            String fechaNaci = rs.getString(5);
            String telefono = rs.getString(6);
            
            // 3. Crear el Nombre Completo (el elemento deseado)
            // Se asume que el Segundo Apellido puede ser nulo o vacío
            String nombreCompleto = nombres + " " + primerAP+ " "+ segundoAP;
            
            
            // 4. Crear el String[] final con el nuevo orden y combinación (4 elementos)
            // Estructura: [ID, Nombre Completo, FechaNaci, Telefono]
            String[] filaPersona = new String[4];
            filaPersona[0] = String.valueOf(id);
            filaPersona[1] = nombreCompleto;

            // 5. Agregar el arreglo a la lista
            listaPersonas.add(filaPersona);
        }
        
        return listaPersonas;
    }
    
    public static DefaultComboBoxModel<String> crearModeloComboBox(ArrayList<String> listaDatos) {
        
        
        // 1. Convertir el ArrayList<String> a un array de String[] 
        // para pasarlo directamente al constructor del modelo.
        String[] arrayDatos = listaDatos.toArray(new String[0]);
        
        return new DefaultComboBoxModel<>(arrayDatos);
    }
    public static DefaultTableModel crearModeloTabla(ResultSet rs) throws SQLException {
        // Objeto para almacenar nombres de columnas y datos
        Vector<String> nombresColumnas = new Vector<>();
        Vector<Vector<Object>> datosTabla = new Vector<>();

        // 1. Obtener los nombres de las columnas (Metadatos)
        ResultSetMetaData metaDatos = rs.getMetaData();
        int cuentaColumnas = metaDatos.getColumnCount();

        for (int i = 1; i <= cuentaColumnas; i++) {
            nombresColumnas.add(metaDatos.getColumnLabel(i));
        }

        // 2. Obtener los datos fila por fila
        while (rs.next()) {
            Vector<Object> fila = new Vector<>();
            for (int i = 1; i <= cuentaColumnas; i++) {
                // Obtiene el valor de cada columna de la fila actual
                fila.add(rs.getObject(i)); 
            }
            datosTabla.add(fila);
        }

        // 3. Crear el modelo de la tabla
        return new DefaultTableModel(datosTabla, nombresColumnas);
    }

    public static DefaultComboBoxModel<String> crearModeloComboBox2(ArrayList<String[]> listaArreglos) {
        
        ArrayList<String> datosParaComboBox = new ArrayList<>();
        
        for (String[] arreglo : listaArreglos) {
            
            String valorComboBox = arreglo[1];
            datosParaComboBox.add(valorComboBox);
            
        }
        
        String[] arrayDatos = datosParaComboBox.toArray(new String[0]);
        
        return new DefaultComboBoxModel<>(arrayDatos);
    }
    
    public ArrayList<String[]> getListaPersonas() {
        return listaPersonas;
    }

    public ArrayList<String[]> getListaPersonal() {
        return listaPersonal;
    }

    public ArrayList<String[]> getListaPacientes() {
        return listaPacientes;
    }

    public ArrayList<String> getListaEstados() {
        return listaEstados;
    }
    
    
    
}
