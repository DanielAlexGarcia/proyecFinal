package com.example.your_clinic;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Controlador.AfiliadoDAO;
import Entidades.Afiliado;
import Entidades.Cita;
import db.ClinicaBD;

public class classActivityInicio extends AppCompatActivity {
    private long tiempoUltimoToque = 0;
    private String TAG = "informacion";
    private static final int TIEMPO_ESPERA = 2000;      // 2 segundos de espera


    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_inicio);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));
                List<Cita> cit = db.citDAO().AllCitas();
                if(cit.size() > 0){

                }else{
                    loadDatDefault();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        // 1. Verifica si la diferencia entre el tiempo actual y el último toque
        // es menor que el tiempo de espera (TIEMPO_ESPERA).
        if (tiempoUltimoToque + TIEMPO_ESPERA > System.currentTimeMillis()) {
            // Caso 2: Se presionó por segunda vez rápidamente.
            super.onBackPressed(); // Ejecuta la acción por defecto (salir de la Activity)
            return;
        }

        // Caso 1: Es el primer toque.
        this.tiempoUltimoToque = System.currentTimeMillis();

        // Muestra un mensaje al usuario.
        Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show();
    }

    private void loadDatDefault(){
        ArrayList<Afiliado> listaDefault = new ArrayList<>();
        listaDefault.add(new Afiliado(1, "Jose", "Torres", "Castro","Propietario", "7N4937B438CJJ3"));
        listaDefault.add(new Afiliado(2, "Marta", "Ortega", "Cruz", "Hermano/a", "230NDOW08E"));
        listaDefault.add(new Afiliado(3, "Gabriel", "Hidalgo", "Lara", "Hijo/a", "8NOF9YB"));
        listaDefault.add(new Afiliado(4, "Natalia", "Rojas", "Marin", "Hermano/a", "7FO6W97D"));
        listaDefault.add(new Afiliado(5, "Felipe", "Blanco", "Guzman", "Tio/a", "8N48F08V"));
        listaDefault.add(new Afiliado(6, "Andrea", "Serrano", "Paz", "Madre", "78N3OY478F"));

        ArrayList<Cita> listaDefault2  = new ArrayList<>();
        listaDefault2.add(new Cita(1,"Ana Maria Garcia López", "Jose Torres Castro", "20-02-2020", "13:30", "Revision", "Completada"));
        listaDefault2.add(new Cita(2, "Juan Carlos Pérez Martinez", "Jose Torres Castro", "20-02-2026", "12:00", "Continuidad", "Programada"));
        listaDefault2.add(new Cita(3, "Luis Alberto Diaz Sánchez", "Gabriel Hidalgo Lara", "10-11-2025","11:30", "Especialista", "Cancelada"));
        listaDefault2.add(new Cita(4, "Sofia Hernandez Ramos", "Marta Ortega Cruz", "20-10-2025", "15:30", "consulta", "Completada"));
        listaDefault2.add(new Cita(5, "Juan Carlos Perez, Martinez", "Andrea Serrano Paz", "28-02-2026", "13:30", "Revision", "Reprogramada"));

        ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(Afiliado n : listaDefault){
                    db.afilDAO().addAfiliado(n);
                }


            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Cita m : listaDefault2){
                    db.citDAO().addCita(m);
                }


            }
        }).start();
        Toast.makeText(this, "Datos por defecto restaurados/añadidos", Toast.LENGTH_SHORT).show();
    }
    public void cargarDefault(View v){
        loadDatDefault();

    }
    
    public void loadPage(View v){
        Intent i = null;
        Log.i("nones", "boton con id: "+ v.getId()+ "funciona ("+ v.getTransitionName()+")");
        if (v.getId() == R.id.btnAgenCita || v.getId() == R.id.agenCit){
            i = new Intent(this, classActivityAgendarCita.class);
        } else if (v.getId() == R.id.btnConsultCitas || v.getId() == R.id.loadCit) {
            i = new Intent(this, classActivityConsultCitas.class);
        } else if (v.getId() == R.id.btnGestAfil || v.getId() == R.id.gestAfil) {
            i = new Intent(this, classActivityGestionAfil.class);
        } else if (v.getId() == R.id.btnCambioCit || v.getId() == R.id.cambioCit) {
            i = new Intent(this, classActivityModifCitas.class);
        } else if (i == null) {
            Toast.makeText(this, "Problemas al intentar cargar la interfaz", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Pantalla no disponible", Toast.LENGTH_SHORT).show();
        }

        if(i != null){
            startActivity(i);
        }
    }



}

