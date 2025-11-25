package com.example.your_clinic;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class classActivityAgendarCita extends AppCompatActivity{

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

        PComp.configurarFormatoFecha(Date);
        PComp.configurarFormatoHora(hora);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new ArrayList<String>()
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SPAfil.setAdapter(adapter);

        SPDoctores = findViewById(R.id.SPDoctor);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.SP_Doctores,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SPDoctores.setAdapter(adapter);

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

        List<String> nombresAfiliados = new ArrayList<>();
        for (Afiliado afiliado : afiliados) {
            nombresAfiliados.add(afiliado.getNombres() +" " + afiliado.getPrimerAP() +" "+ afiliado.getSegundoAP());
        }

        adapter.addAll(nombresAfiliados);

        adapter.notifyDataSetChanged();
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
                        mostrarVentanaExito("Exito", "Se agendo la cita correctamente");
                    }
                });
            }
        }).start();
    }





}
