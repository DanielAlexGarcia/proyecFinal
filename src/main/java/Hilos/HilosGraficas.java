/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Controlador.CitaDAO;
import Controlador.ListadosConcurrentes;
import Vistas.VentanaInicio;
import java.sql.ResultSet;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author daniel
 */
public class HilosGraficas {
    VentanaInicio faz;
    ListadosConcurrentes lists;
    CitaDAO CDAO;
    
    public HilosGraficas(VentanaInicio fa){
        this.faz = fa;
        this.CDAO = new CitaDAO(faz);
        this.lists = new ListadosConcurrentes();
    }
    
    
    public void AñadirCita(DefaultCategoryDataset datSet) {
    	faz.showMessageDialog(faz.frame, "Añadiendo...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(datSet != null) {
                    ResultSet RSReprog = CDAO.busquedaPorEstadoCita("Reprogramada");
                    ResultSet RSCample = CDAO.busquedaPorEstadoCita("Completada");
                    ResultSet RSProg = CDAO.busquedaPorEstadoCita("Programada");
                    ResultSet RSCancel = CDAO.busquedaPorEstadoCita("Cancelada");
                    
                    
                    if (RSReprog != null && RSCample != null && 
                            RSProg != null && RSCancel != null){
                        TableModel model1 = lists.crearModeloTabla(RSReprog);
                        TableModel model2 = lists.crearModeloTabla(RSCample);
                        TableModel model3 = lists.crearModeloTabla(RSProg);
                        TableModel model4 = lists.crearModeloTabla(RSCancel);
                    
                        int cantReprog = model1.getRowCount();
                        int cantComple = model2.getRowCount();
                        int cantProg = model3.getRowCount();
                        int cantCancel = model4.getRowCount();
                    
                        datSet.addValue(cantReprog, "Reprogramadas", "");
                        datSet.addValue(cantComple, "Completadas", "");
                        datSet.addValue(cantProg, "Programadas", "");
                        datSet.addValue(cantCancel, "Canceladas", "");
                        
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Añadiendo...", false);
                    	faz.ShowMessageFeerback("Grafica de estados de citas");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	faz.showMessageDialog(faz.frame, "Añadiendo...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        faz.ShowMessage("No se encontraron registros para la grafica");
                    });
                    }
            	}
            	else {
                        faz.showMessageDialog(faz.frame, "Buscando...", false);
            		faz.ShowMessage("No se asignaron recursos para la grafica");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    
    
    
}
