package com.example.your_clinic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;

import Entidades.Afiliado;
import db.ClinicaBD;

public class addAfiliado extends AppCompatActivity {

    private EditText txtNombres, txtPAP, txtSAP;
    private Spinner SPParentezco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_afiliado);

        txtNombres = findViewById(R.id.txtNombresAfil);
        txtPAP = findViewById(R.id.txtPAPAfil);
        txtSAP = findViewById(R.id.txtSAPAfil);
        SPParentezco = findViewById(R.id.SPParentez);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.SP_Parentezco,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SPParentezco.setAdapter(adapter);

    }

    public void addAfil(View v){
        validarDat();
    }

    private boolean validarDat(){
        String nombres = txtNombres.getText().toString();
        String PAP = txtPAP.getText().toString();
        String SAP =txtSAP.getText().toString();
        boolean n = (nombres.equals(""));
        boolean n2 = (PAP.equals(""));
        boolean n3 = (SAP.equals(""));
        boolean n4 = SPParentezco.getSelectedItemPosition() == 0;

        String Mensaje= "";

        if(n||n2||n3){
            if(n)Mensaje = Mensaje+ "El campo nombres no puede quedar vacio\n";
            if(n2)Mensaje = Mensaje+ "El campo primer apellido no puede quedar vacio\n";
            if(n3)Mensaje = Mensaje+ "El campo segundo apellido no puede quedar vacio\n";
            if(n4) Mensaje= Mensaje+ "Debes elegir el parentezco del nuevo miembro";

            mostrarVentanaError("Error", Mensaje);
        }else{
            nombres = ConvertNombre(nombres);
            PAP= ConvertNombre(PAP);
            SAP = ConvertNombre(SAP);
            Afiliado afil = new Afiliado(nombres, PAP, SAP, (String)SPParentezco.getSelectedItem(), generarCadenaAleatoria(10));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));
                    db.afilDAO().addAfiliado(afil);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mostrarVentanaExito("Exito", "Miembro añadido con exito ");
                        }
                    });
                }
            }).start();
        }


        return false;
    }

    public static String generarCadenaAleatoria(int longitud) {
        // Usa SecureRandom para una mejor aleatoriedad si es importante
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(longitud);

        // Recorre 10 veces para generar 10 caracteres
        for (int i = 0; i < longitud; i++) {
            String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            // 1. Genera un índice aleatorio dentro del rango de CARACTERES_PERMITIDOS
            int indiceAleatorio = random.nextInt(CARACTERES_PERMITIDOS.length());

            // 2. Obtiene el carácter en ese índice
            char caracterAleatorio = CARACTERES_PERMITIDOS.charAt(indiceAleatorio);

            // 3. Añade el carácter al constructor de cadenas
            sb.append(caracterAleatorio);
        }

        // Devuelve la cadena final
        return sb.toString();
    }
    private void mostrarVentanaError(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void mostrarVentanaExito(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String ConvertNombre(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String primeraLetraMayuscula = str.substring(0, 1).toUpperCase();

        String restoCadena = str.substring(1).toLowerCase(); // Opcional: convertir el resto a minúsculas

        return primeraLetraMayuscula + restoCadena;
    }


}
