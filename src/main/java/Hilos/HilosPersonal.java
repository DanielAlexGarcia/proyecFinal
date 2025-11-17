/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Vistas.VentanaInicio;
import javax.swing.SwingUtilities;
import Modelo.Personal;
import Controlador.PersonalDAO;
/**
 *
 * @author daniel
 */
public class HilosPersonal {
    VentanaInicio interfaz;
    PersonalDAO PDAO;
    
    
    public HilosPersonal(VentanaInicio faz){
        this.interfaz = faz;
    }
    /*
    public void hiloAgregarPersonal(){
        boolean exito = false;
        interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", true);
        new Thread(() -> {								// Hilo que hace la consulta 
            try {
                String message;
                    if (PDAO != null){
                        int dat = Integer.parseInt(datos.get(0));
                        Personal p = PDAO.buscarPersonalPorDNI(dat);
                        boolean noHay = (p == null);
                        if (noHay){
                            boolean logro = PDAO.insertarPersonal(null, dat, datos.get(1), datos.get(2), datos.get(3), Float.parseFloat(datos.get(4)));
                        if(logro){
                            System.out.println("se logro hacer la insercion");
                            exito = true;
                        }else {
                            interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                            interfaz.ShowMessage("ocurrio un conflicto con al intentar añadirlo");
                        }
                        }else{
                            interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                            interfaz.ShowMessage("ya esta registrada esa persona como personal");
                        }
                        
                    } //Movimientos en la GUI en el hilo de eventos de Swing
                    SwingUtilities.invokeLater(() -> {				//delega la tarea de actualizar la GUI al hilo principal (el que maneja la GUI)
                    	interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                    	if(exito){
                            interfaz.ShowMessageFeerback("Añadido con exito");
                        }
                        
                    });
            	
                

            } catch (Exception e) {
                System.err.println("Error al intentar hacer la insercion: " + e.getMessage());
                interfaz.showMessageDialog(interfaz.frame, "Añadiendo...", false);
                interfaz.ShowMessage("No se puedo añadir conflicto con " + e.getMessage());
            }
        }).start();
    }
    */
}
