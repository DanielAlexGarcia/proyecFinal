package com.example.your_clinic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import Entidades.Cita;
import db.ClinicaBD;
import plantillas.PlantillasComponentes;

public class modifiCitSelect extends AppCompatActivity {
    private String[] IDcit;
    private EditText txtDoc, txtPac, txtMot, txtFech, txtHora;

    private Cita citaModificar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_cita_select);

        txtDoc = findViewById(R.id.txtNomDocCit);
        txtPac = findViewById(R.id.txtNomPacCit);
        txtMot = findViewById(R.id.txtMotCitMod);
        txtFech = findViewById(R.id.txtFechaEdit);
        txtHora = findViewById(R.id.txtHoraEdit);
        boolean si = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                String clave_dat = "com.example.your_clinic.ID_CITA_SELECCIONADA";
                IDcit =intent.getStringArrayExtra(clave_dat);



                if(IDcit != null){
                    txtDoc.setText(IDcit[1].toString());
                    txtPac.setText(IDcit[2].toString());
                    txtFech.setText(IDcit[3].toString());
                    txtHora.setText(IDcit[4].toString());
                    txtMot.setText(IDcit[5].toString());
                    citaModificar = new Cita(Integer.parseInt(IDcit[0]),IDcit[1].toString(),IDcit[2].toString(), IDcit[3].toString(), IDcit[4].toString(),
                    IDcit[5].toString(),IDcit[6].toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Datos cargados", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "No se pudieron cargar los datos", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).start();

    }
    private void validarDatos(){
        PlantillasComponentes PCValid = new PlantillasComponentes();
        boolean n = PCValid.fechaValida(txtFech.getText().toString(), txtFech);
        boolean n2 = PCValid.horaValida(txtHora.getText().toString(), txtHora);
        String Mensaje = "";
        if(!n || !n2){
            if(!n){
                Mensaje = Mensaje + "Fecha mal escrita o no valida\n";
            }
            if(!n2){
                Mensaje = Mensaje+ "Hora mal escrita o no valida";
            }
            mostrarVentanaError("Error", Mensaje);
        }else{
            String fecha = txtFech.getText().toString();
            String hora = txtHora.getText().toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));
                    db.citDAO().actualizarCita(fecha, hora, "Reprogramada",citaModificar.getId());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mostrarVentanaExito("Exito", "Se Reprogramo la cita correctamanete");
                        }
                    });
                }
            }).start();
        }
    }

    public void sabeCita(View v){
        validarDatos();
    }

    private void mostrarVentanaError(String titulo, String mensaje) {
        // 1. Crear una nueva instancia del Builder. Se usa 'this' como Contexto.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Configurar las propiedades de la ventana: Título y Mensaje.
        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        // 3. Agregar un botón de acción (Positivo).
        // Este botón es el que generalmente se usa para "Aceptar", "OK" o "Continuar".
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Opcional: Código que se ejecuta cuando el usuario presiona "Aceptar".
                // Por ejemplo: reintentar la acción, limpiar un campo, o simplemente cerrar.
                dialog.dismiss(); // Cierra el diálogo.
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void mostrarVentanaExito(String titulo, String mensaje) {
        // 1. Crear una nueva instancia del Builder. Se usa 'this' como Contexto.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Configurar las propiedades de la ventana: Título y Mensaje.
        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        // 3. Agregar un botón de acción (Positivo).
        // Este botón es el que generalmente se usa para "Aceptar", "OK" o "Continuar".
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Opcional: Código que se ejecuta cuando el usuario presiona "Aceptar".
                // Por ejemplo: reintentar la acción, limpiar un campo, o simplemente cerrar.
                dialog.dismiss(); // Cierra el diálogo.
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
