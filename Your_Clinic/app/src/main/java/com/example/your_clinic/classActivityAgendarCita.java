package com.example.your_clinic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import Entidades.Afiliado;
import Entidades.Cita;
import db.ClinicaBD;
import plantillas.PlantillasComponentes;

public class classActivityAgendarCita extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner SPDoctores, SPAfil;
    EditText Date, hora, motivo;
    PlantillasComponentes PComp = new PlantillasComponentes();
    Cita cit;
    List<Afiliado> afiliados;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);
        SPDoctores = findViewById(R.id.SPDoctor);
        Date = findViewById(R.id.txtDate);
        hora = findViewById(R.id.txtHora);
        motivo = findViewById(R.id.txtMotivo);
        SPAfil = findViewById(R.id.SPAfiliados);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new ArrayList<String>()
        );

// Define cómo se verá el elemento cuando está seleccionado
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// 3. Conecta el Adaptador al Spinner
        SPAfil.setAdapter(adapter);

        SPDoctores = findViewById(R.id.SPDoctor); // Reemplaza R.id.SPDoctores con el ID real de tu XML

        // 1. Crear el ArrayAdapter (¡SOLO AQUÍ!)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.SP_Doctores, // ✅ Tu lista de opciones (desde resources/arrays.xml)
                android.R.layout.simple_spinner_item
        );

        // 2. Establecer el layout para la vista desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 3. Asignar el adaptador al Spinner
        SPDoctores.setAdapter(adapter);

        // 4. Asignar el listener para manejar selecciones
        SPDoctores.setOnItemSelectedListener(this);

        getAfil();

    }

    private void getAfil(){
        ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                afiliados = db.afilDAO().allAfiliados();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actualizarSPPaciente();
                    }
                });
            }
        }).start();

    }
    private void actualizarSPPaciente(){
        adapter.clear();

        // 2. Extrae solo los NOMBRES de la lista de Afiliados
        List<String> nombresAfiliados = new ArrayList<>();
        for (Afiliado afiliado : afiliados) {
            // Asumiendo que tu clase Afiliado tiene un método getName()
            nombresAfiliados.add(afiliado.getNombres() +" " + afiliado.getPrimerAP() +" "+ afiliado.getSegundoAP());
        }

        // 3. Añade los nuevos nombres al Adaptador
        adapter.addAll(nombresAfiliados);

        // 4. Notifica al Spinner que los datos han cambiado
        adapter.notifyDataSetChanged();
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
            /*
        // 4. (Opcional) Agregar un botón de acción Negativo (Cancelar/Salir)
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Código que se ejecuta si el usuario presiona "Cancelar".
                // Por ejemplo: no hacer nada o registrar el error.
                dialog.cancel(); // Cierra el diálogo.
            }
        });*/

        // 5. Crear y mostrar la ventana de diálogo.
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void accionVerificar(View v){
        if(v.getId() == R.id.btnAgendarCit){
            VerificarDatos();
        }else{
            Toast.makeText(this, "Error, no se identifico la accion", Toast.LENGTH_SHORT).show();
        }
    }


    private void VerificarDatos(){
        boolean fecha = PComp.fechaValida(Date.getText().toString(), Date);
        boolean hor = PComp.horaValida(hora.getText().toString(), hora);
        boolean mot = (motivo.getText().toString().trim().length() >= 5);
        String Mensaje = "";
        int error= 0;

        if(!fecha){
            Mensaje = Mensaje + "Fecha mal escrita o invalida\n";
            error ++;
        }
        if (!hor) {
            Mensaje = Mensaje +"Hora mal escrita o invalida\n";
            error++;
        }
        if (!mot) {
            Mensaje = Mensaje + "El campo de motivo no puede estar vacio (min 10 caracteres)\n";
            error++;
        }
        if (fecha && hor && mot){
            Mensaje = Mensaje + "Se agendo la cita correctamente";
        }
        if(error >0){
            mostrarVentanaError("Error", Mensaje);
        }else{
            Toast.makeText(this, "Agendando...", Toast.LENGTH_SHORT).show();
            agregarCita();
        }

    }

    private void agregarCita (){
        cit = new Cita((String) SPDoctores.getSelectedItem(), (String) SPAfil.getSelectedItem(), Date.getText().toString(), hora.getText().toString(), motivo.getText().toString(), "Programada");
        ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.citDAO().addCita(cit);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mostrarVentanaError("Exito", "Se agendo la cita correctamente");
                    }
                });
            }
        }).start();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // 'position' es la posición del elemento seleccionado (0, 1, 2, ...)
        String itemSeleccionado = parent.getItemAtPosition(position).toString();

        if (position > 0) { // Evita mostrar el Toast si selecciona la primera opción ("Selecciona un Producto")
            Toast.makeText(parent.getContext(), "Seleccionaste: " + itemSeleccionado, Toast.LENGTH_SHORT).show();
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.SP_Doctores, // La lista de opciones
                android.R.layout.simple_spinner_item // Layout de cómo se ve el Spinner cerrado
        );

        // 2. Establecer el layout para la vista desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SPDoctores.setAdapter(adapter);
        SPDoctores.setOnItemSelectedListener(this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Se llama si la selección desaparece (raro en un Spinner)
    }

}
