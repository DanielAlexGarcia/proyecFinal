/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Controlador.ListadosConcurrentes;
import Controlador.ReportesDAO;
import Vistas.VentanaInicio;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

/**admin
 *
 * @author daniel
 */
public class HilosReportes {
    VentanaInicio faz;
    ReportesDAO RDAO;
    
    public HilosReportes(VentanaInicio fa){
        this.faz = fa;
        this.RDAO = new ReportesDAO();
    }
    
    public void reportesAltas(JTable tabla) {
    	faz.showMessageDialog(faz.frame, "Buscando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(tabla != null) {
                    ResultSet donana = RDAO.RegistrosAltas();
                    ListadosConcurrentes lis = new ListadosConcurrentes();
                    TableModel model = lis.crearModeloTabla(donana);
                    tabla.setModel(model);
                    if (donana != null){
                        final int rowCount = model.getRowCount();
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Bucando...", false);
                    	if(rowCount >0){
                            faz.ShowMessageFeerback("Registros encontrados");
                        }else{
                            faz.ShowMessage("No hay registros que mostrar");
                        }
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Bucando...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        faz.ShowMessage("No se encontraron registros");
                    });
                    }
            	}
            	else {
            		faz.showMessageDialog(faz.frame, "Buscando...", false);
            		faz.ShowMessage("No se asigno ninguna tabla para mostrar los registros");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    public void reportesBajas(JTable tabla) {
    	faz.showMessageDialog(faz.frame, "Buscando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(tabla != null) {
                    ResultSet donana = RDAO.RegistrosBajas();
                    ListadosConcurrentes lis = new ListadosConcurrentes();
                    TableModel model = lis.crearModeloTabla(donana);
                    tabla.setModel(model);
                    if (donana != null){
                        
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {	
                        final int rowCount = model.getRowCount();
                        //delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Bucando...", false);
                    	if(rowCount >0){
                            faz.ShowMessageFeerback("Registros encontrados");
                        }else{
                            faz.ShowMessage("No hay registros que mostrar");
                        }
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Bucando...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        faz.ShowMessage("No se encontraron registros");
                    });
                    }
            	}
            	else {
            		faz.showMessageDialog(faz.frame, "Buscando...", false);
            		faz.ShowMessage("No se asigno ninguna tabla para mostrar los registros");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    public void reportesCambios(JTable tabla) {
    	faz.showMessageDialog(faz.frame, "Buscando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(tabla != null) {
                    ResultSet donana = RDAO.RegistrosCambios();
                    ListadosConcurrentes lis = new ListadosConcurrentes();
                    tabla.setModel(lis.crearModeloTabla(donana));
                    TableModel model = lis.crearModeloTabla(donana);
                    tabla.setModel(model);
                    if (donana != null){
                        final int rowCount = model.getRowCount();
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Bucando...", false);
                    	if(rowCount >0){
                            faz.ShowMessageFeerback("Registros encontrados");
                        }else{
                            faz.ShowMessage("No hay registros que mostrar");
                        }
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Bucando...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        faz.ShowMessage("No se encontraron registros");
                    });
                    }
            	}
            	else {
            		faz.showMessageDialog(faz.frame, "Buscando...", false);
            		faz.ShowMessage("No se asigno ninguna tabla para mostrar los registros");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
}
