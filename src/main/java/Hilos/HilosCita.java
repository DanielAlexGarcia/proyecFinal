/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Controlador.CitaDAO;
import Controlador.ListadosConcurrentes;
import Vistas.VentanaInicio;
import Modelo.Cita;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author daniel
 */
public class HilosCita {
    VentanaInicio faz;
    CitaDAO CDAO;
    Cita cit;
    
    public HilosCita(VentanaInicio fa, Cita c){
        this.faz = fa;
        this.cit = c;
        CDAO = new CitaDAO(faz);
    }
    
    public void AñadirCita() {
    	faz.showMessageDialog(faz.frame, "Añadiendo...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(cit != null) {
                    boolean donana = CDAO.altasCitas(cit);
                    if (donana){
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Añadiendo...", false);
                    	faz.ShowMessageFeerback("Cita agendada con exito");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Añadiendo...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        faz.ShowMessage("Conflico al intentar agendar cita");
                    });
                    }
            	}
            	else {
                        faz.showMessageDialog(faz.frame, "Buscando...", false);
            		faz.ShowMessage("No hay datos para agendar cita");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    public void BuscarCita(int id, JComboBox estad, JComboBox doc, JFormattedTextField fech,
            JFormattedTextField hora, JTextField ide) {
    	faz.showMessageDialog(faz.frame, "Añadiendo...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(id != 0) {
                    Cita c = CDAO.BuqCitaPorID(id);
                    if (c != null){
                        ide.setEditable(false);
                        String docto = "";
                        int per = 0;
                        ListadosConcurrentes l = new ListadosConcurrentes();
                        for (String[] n : l.getListaPersonal()){
                            int p = Integer.parseInt(n[0]);
                            if (p == c.getIddoc()){
                                per = p;
                                docto = n[1];
                                break;
                            }
                        }
                        doc.setSelectedItem(docto);
                        doc.setEnabled(true);
                        estad.setSelectedItem(c.getEst());
                        estad.setEnabled(true);
                        fech.setText(c.getFech());
                        fech.setEditable(true);
                        hora.setText(c.getHora());
                        hora.setEditable(true);
                        
                        
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Añadiendo...", false);
                    	faz.ShowMessageFeerback("Cita Encontrada");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Añadiendo...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        faz.ShowMessage("No existe una cita con ese ID");
                    });
                    }
            	}
            	else {
                        faz.showMessageDialog(faz.frame, "Buscando...", false);
            		faz.ShowMessage("No hay ID para buscar cita");
                        
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    
    public void CambiarCita() {
    	faz.showMessageDialog(faz.frame, "Guardando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(cit != null) {
                    boolean donana = CDAO.cambiosCita(cit);
                    if (donana){
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Añadiendo...", false);
                    	faz.ShowMessageFeerback("Cita Guardada con exito");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Añadiendo...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        faz.ShowMessage("Conflico al intentar guardar cita");
                    });
                    }
            	}
            	else {
                        faz.showMessageDialog(faz.frame, "Buscando...", false);
            		faz.ShowMessage("No hay datos para guardar cita");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    public void allCita(JTable tabla) {
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(tabla != null) {
                    ResultSet donana = CDAO.allCitas();
                    if (donana != null){
                        ListadosConcurrentes l = new ListadosConcurrentes();
                        tabla.setModel(l.resultSetToTableModel(donana));
                        System.out.println("Citas actualizadas");
                    }else{
                        System.out.println("No hay lista para actualizar tabla");
                    }
            	}
            	else {
                    System.out.println("no hay tabla para hacer la actualizacion");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
}
