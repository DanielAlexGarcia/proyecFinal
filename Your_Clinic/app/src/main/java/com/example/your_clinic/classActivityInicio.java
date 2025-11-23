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

import Controlador.AfiliadoDAO;
import Entidades.Afiliado;
import db.ClinicaBD;

public class classActivityInicio extends AppCompatActivity {
    private long tiempoUltimoToque = 0;
    private String TAG = "informacion";
    private static final int TIEMPO_ESPERA = 2000;      // 2 segundos de espera


    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_inicio);

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
    
    public void loadPage(View v){
        Intent i = null;
        Log.i("nones", "boton con id: "+ v.getId()+ "funciona ("+ v.getTransitionName()+")");
        if (v.getId() == R.id.btnAgenCita || v.getId() == R.id.agenCit){
            i = new Intent(this, classActivityAgendarCita.class);
        } else if (v.getId() == R.id.btnConsultCitas || v.getId() == R.id.loadCit) {
            test(1);
        } else if (v.getId() == R.id.btnGestAfil || v.getId() == R.id.gestAfil) {

        } else if (i == null) {
            Toast.makeText(this, "Problemas al intentar cargar la interfaz", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Pantalla no disponible", Toast.LENGTH_SHORT).show();
        }

        if(i != null){
            startActivity(i);
        }
    }

    private void test(int op){
        String un ="paco"      ;
        String dos ="deco"     ;
        String tre ="enco"     ;
        String fort ="hermano"    ;
        String five = "76JG93H4T"    ;
        String Six      ;
        String Seven    ;
        String eit      ;
        ClinicaBD bd = ClinicaBD.getAppDatabase(getBaseContext());
        if(op == 1){
            Afiliado alfil = new Afiliado(null, un, dos,tre, fort, five);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bd.afilDAO().addAfiliado(alfil);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Insercion correcta", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        }

    }



}

