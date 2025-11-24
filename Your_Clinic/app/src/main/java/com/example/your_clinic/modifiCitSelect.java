package com.example.your_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class modifiCitSelect extends AppCompatActivity {
    private int IDcit;
    private TextView label;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_cita_select);


        /*
        Intent intent = getIntent();
        String clave_dat = "com.example.your_clinic.ID_CITA_SELECCIONADA";
        IDcit =intent.getIntExtra(clave_dat, -1);
        if (IDcit != -1) {
            // 3. ⭐️ CORRECCIÓN CLAVE: Convertir el entero a String antes de usar setText()
            label.setText(String.valueOf(IDcit));

            // Opcional: Si quieres un texto más descriptivo
            // label.setText("Cita seleccionada ID: " + IDcit);
        } else {
            // Manejar el caso de que no se haya pasado el ID
            label.setText("Error: ID de cita no recibido.");
        }*/
    }
}
