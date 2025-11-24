package com.example.your_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Entidades.Afiliado;
import Entidades.Cita;
import db.ClinicaBD;
import com.example.your_clinic.CustomAdapter;

public class classActivityModifCitas extends AppCompatActivity{
    private ClinicaBD db;
    private Spinner SPPac;
    private ArrayList<Afiliado> lista;
    private ArrayAdapter<String> adapter;
    private RecyclerView results;
    private CustomAdapter adapte;

    private ArrayList<Cita> datos = null;
    private RecyclerView.LayoutManager layoutManager;
    private List<Cita> citas;

    private EditText idCita;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_cita);
        SPPac = findViewById(R.id.SPPaci);

        results = findViewById(R.id.ResultsViewModif);
        results.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        results.setLayoutManager(layoutManager);
        idCita = findViewById(R.id.txtIDCit);

        db = ClinicaBD.getAppDatabase((getBaseContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                datos = (ArrayList<Cita>) db.citDAO().AllCitas();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapte = new CustomAdapter(datos);
                        adapte.opcionImpres = 2;
                        results.setAdapter(adapte);
                    }
                });
            }
        }).start();

        loadPacientes();
        getAfil();

    }
    public void loadResultsModifi(View v){
        loadCits(SPPac.getSelectedItem().toString());
    }

private void loadCits(String Pac){
    new Thread(new Runnable() {
        @Override
        public void run() {
            citas = db.citDAO().consultCitaPac(Pac);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    actualizarResultados(citas);
                }
            });
        }
    }).start();
}

public void loadCitSelect(View v){
        int i = Integer.parseInt(idCita.getText().toString());
        boolean esta = false;
        int IDcit = 0;
        for (Cita m : citas){
            if(i == m.getId()){
                esta = true;
                IDcit = m.getId();
                break;
            }
        }
        Intent l = new Intent(this, modifiCitSelect.class);
        if(esta && l != null){
            //String clave_dat = "com.example.your_clinic.ID_CITA_SELECCIONADA";
            //l.putExtra(clave_dat, IDcit);
            startActivity(l);
        }else{
        if(!esta){
            Toast.makeText(this, "No existe una cita con ese ID", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Problemas al cargarlo", Toast.LENGTH_SHORT).show();
        }
    }

}

    private void actualizarResultados(List<Cita> listaDeResult) {
        if (adapte != null) {
            adapte.updateData(listaDeResult);
        }
    }
    private void loadPacientes(){
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new ArrayList<String>()
        );

// Define cómo se verá el elemento cuando está seleccionado
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// 3. Conecta el Adaptador al Spinner
        SPPac.setAdapter(adapter);
    }

    private void getAfil(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                lista =(ArrayList<Afiliado>) db.afilDAO().allAfiliados();
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
        for (Afiliado afiliado : lista) {
            // Asumiendo que tu clase Afiliado tiene un método getName()
            nombresAfiliados.add(afiliado.getNombres() +" " + afiliado.getPrimerAP() +" "+ afiliado.getSegundoAP());
        }

        // 3. Añade los nuevos nombres al Adaptador
        adapter.addAll(nombresAfiliados);

        // 4. Notifica al Spinner que los datos han cambiado
        adapter.notifyDataSetChanged();
    }


}


