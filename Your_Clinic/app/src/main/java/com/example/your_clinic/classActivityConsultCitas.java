package com.example.your_clinic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cita;
import db.ClinicaBD;

public class classActivityConsultCitas extends AppCompatActivity {
    private Spinner SPEstado, SPNomDoc;
    private RadioGroup RBGBusq;
    private RecyclerView resultados;
    private ArrayList<Cita> datos = null;

    private RadioButton RBBusqEstado, RBBusqNomDoc;
    private CustomAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_cita);

        SPEstado = findViewById(R.id.SPEstadoCit);
        SPNomDoc = findViewById(R.id.SPNomDoc);
        RBGBusq = findViewById(R.id.RBGBusqueda);
        resultados = findViewById(R.id.ResultBusqCit);
        resultados.setHasFixedSize(true);
        RBBusqEstado = findViewById(R.id.RBBusqCitEstado);
        RBBusqNomDoc = findViewById(R.id.RBBusqCitNomDoc);

        layoutManager = new LinearLayoutManager(this);
        resultados.setLayoutManager(layoutManager);

        ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                datos = (ArrayList<Cita>) db.citDAO().AllCitas();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new CustomAdapter(datos);
                        resultados.setAdapter(adapter);
                    }
                });
            }
        }).start();




        ArrayAdapter<CharSequence> adapterDoctores = ArrayAdapter.createFromResource(
                this,
                R.array.SP_Doctores,
                android.R.layout.simple_spinner_item
        );

        adapterDoctores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SPNomDoc.setAdapter(adapterDoctores);

        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(
                this,
                R.array.SP_EstadoCita,
                android.R.layout.simple_spinner_item
        );

        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SPEstado.setAdapter(adapterEstado);

    }
    private int obtenerOpcionBusquedaSeleccionada() {
        // RBGBusq es tu objeto RadioGroup
        int checkedId = RBGBusq.getCheckedRadioButtonId();

        // El metodo devuelve el ID del recurso (R.id.tu_radio_button) o -1 si no hay ninguno.
        return checkedId;
    }

    public void buscarCita(View v){

        int opSelect = obtenerOpcionBusquedaSeleccionada();
        if(opSelect == R.id.RBBusqCitEstado){
            Consultar2CitaEnBD(SPEstado.getSelectedItem().toString());
        } else if (opSelect == R.id.RBBusqCitNomDoc) {
            Consultar1CitaEnBD(SPNomDoc.getSelectedItem().toString());
        }else{
            Toast.makeText(this, "Selecciona una opcion de busqueda", Toast.LENGTH_SHORT).show();
        }
    }

    private void Consultar1CitaEnBD(String doctor){
        ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Cita> citas = db.citDAO().consultCitaDoc(doctor);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(citas.size() > 0){
                            actualizarResultados(citas);
                        }else{
                            Toast.makeText(getBaseContext(), "No hay citas con esa descripcion", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }).start();
    }

    private void Consultar2CitaEnBD(String estado){
        ClinicaBD db = ClinicaBD.getAppDatabase((getBaseContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Cita> citas = db.citDAO().consultCitaEstado(estado);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actualizarResultados(citas);
                    }
                });
            }
        }).start();
    }

    private void actualizarResultados(List<Cita> listaDeResultados) {
        if (adapter != null) {
            adapter.updateData(listaDeResultados);
        }
    }

}








