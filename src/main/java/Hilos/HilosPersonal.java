/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Controlador.ListadosConcurrentes;
import Vistas.VentanaInicio;
import javax.swing.SwingUtilities;
import Modelo.Personal;
import Controlador.PersonalDAO;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JTable;
/**
 *
 * @author daniel
 */
public class HilosPersonal {
    VentanaInicio interfaz;
    PersonalDAO PDAO;
    Personal perso;
    
    
    public HilosPersonal(VentanaInicio faz, Personal pers){
        this.interfaz = faz;
        this.perso = pers;
        PDAO = new PersonalDAO(interfaz);
    }
    
    public void AñadirPersonal() {
    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(perso != null) {
                    boolean donana = PDAO.altasPersonal(perso);
                    if (donana){
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    	interfaz.ShowMessageFeerback("Personal añadidio con exito");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        interfaz.ShowMessage("Conflico al intentar añadir personal");
                    });
                    }
            	}
            	else {
            		interfaz.showMessageDialog(interfaz.frame, "Buscando...", false);
            		interfaz.ShowMessage("No hay datos para añadir personal");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
    public void BuscarCoincidenciaPersonal(JTable tabla, String nombre) {
    	interfaz.showMessageDialog(interfaz.frame, "Buscando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(nombre != null && tabla != null) {
                    ResultSet donana = PDAO.busquedaPersonal(nombre);
                    ListadosConcurrentes lis = new ListadosConcurrentes();
                    tabla.setModel(lis.crearModeloTabla(donana));
                    if (donana != null){
                        
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Bucando...", false);
                    	interfaz.ShowMessageFeerback("Busqueda exitosa");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Bucando...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        interfaz.ShowMessage("No hay ningun personal que coincida con la busqueda");
                    });
                    }
            	}
            	else {
            		interfaz.showMessageDialog(interfaz.frame, "Buscando...", false);
            		interfaz.ShowMessage("No hay datos para añadir personal");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    public void EliminarPersonal(Integer id, JTable tablas) {
    	interfaz.showMessageDialog(interfaz.frame, "Eliminando...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
            	if(id != null) {
                    boolean donana = PDAO.bajasPersonal(id);
                    ResultSet tabl = PDAO.allPersonal();
                    ListadosConcurrentes lis = new ListadosConcurrentes();
                    tablas.setModel(lis.crearModeloTabla(tabl));
                    if (donana){
                        // Actualizar GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Eliminando...", false);
                    	interfaz.ShowMessageFeerback("Personal eliminado con exito");
                    });
                    }else{
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Eliminando...", false);
                    	//interfaz.interfaz.setDonador(donana, ventana);
                        interfaz.ShowMessage("Este personal tiene otras dependencias \n"
                                + "Imposible eliminarlo sin eliminar sus dependencias antes\n"
                                + "O no existe");
                    });
                    }
            	}
            	else {
            		interfaz.showMessageDialog(interfaz.frame, "Eliminando...", false);
            		interfaz.ShowMessage("No hay datos para eliminar personal");
            	}
                

            } catch (Exception e) {
                System.err.println("Error al consultar datos: " + e.getMessage());
            }
        }).start();
    }
    
}
