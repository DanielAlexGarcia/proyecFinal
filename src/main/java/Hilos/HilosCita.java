/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Controlador.CitaDAO;
import Vistas.VentanaInicio;
import Modelo.Cita;
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
    
}
