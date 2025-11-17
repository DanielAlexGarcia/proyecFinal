/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Controlador.ListadosConcurrentes;
import Vistas.VentanaInicio;
import Controlador.PacienteDAO;
import Modelo.Paciente;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daniel
 */
public class HilosPaciente {
    VentanaInicio interfaz;
    PacienteDAO PDAO;
    Paciente pac;
    
    public HilosPaciente(VentanaInicio fa, Paciente p){
        this.interfaz = fa;
        this.pac = p;
        PDAO = new PacienteDAO(interfaz);
    }
    
    public void AñadirPaciente() {
    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(pac != null) {
                    boolean donana = PDAO.altasPaciente(pac);
                    if (donana){
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    	interfaz.ShowMessageFeerback("Paciente añadidio con exito");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        interfaz.ShowMessage("Conflico al intentar añadir paciente");
                    });
                    }
            	}
            	else {
            		interfaz.showMessageDialog(interfaz.frame, "Buscando...", false);
            		interfaz.ShowMessage("No hay datos para añadir paciente");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    public void BuscarPaciente(int idPac, JTextField numseg, JTextArea alergi) {
    	interfaz.showMessageDialog(interfaz.frame, "Buscando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(pac == null) {
                    Paciente donana = PDAO.buscarPaciente(idPac);
                    
                    if (donana != null){
                        pac = donana;
                        numseg.setText(pac.getNumSeg());
                        alergi.setText(pac.getAlergias());
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    	interfaz.ShowMessageFeerback("Paciente encontrado y cargado");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        interfaz.ShowMessage("Conflico al intentar buscar paciente");
                    });
                    }
            	}
            	else {
            		interfaz.showMessageDialog(interfaz.frame, "Buscando...", false);
            		interfaz.ShowMessage("No hay datos para buscar paciente");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    public void CambiarPaciente(JComboBox detfaul) {
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(pac != null) {
                    boolean donana = PDAO.setPaciente(pac);
                    if (donana){
                        detfaul.setEnabled(true);
                        detfaul.setSelectedItem(null);
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	
                    	interfaz.ShowMessageFeerback("Paciente guardado con exito");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        interfaz.ShowMessage("Conflico al intentar guardar paciente");
                    });
                    }
            	}
            	else {
            		interfaz.ShowMessage("No hay datos para guardar paciente");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    public void consultarPacientes(JTable tp) {
    	interfaz.showMessageDialog(interfaz.frame, "Cargando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(tp != null) {
                    ResultSet donana = PDAO.todosPacientes();
                    if (donana != null){
                        ListadosConcurrentes li = new ListadosConcurrentes();
                        DefaultTableModel modelo = li.resultSetToTableModel(donana);
                        tp.setModel(modelo);
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        interfaz.ShowMessage("No se pudo actualizar la tabla de pacientes");
                    });
                    }
            	}
            	else {
            		interfaz.showMessageDialog(interfaz.frame, "Buscando...", false);
            		interfaz.ShowMessage("No hay pabla para mostrar datos");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
}
