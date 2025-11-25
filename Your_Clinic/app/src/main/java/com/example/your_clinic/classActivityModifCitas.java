package com.example.your_clinic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private ArrayList<Cita> citasBusq;

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
                        citasBusq = new ArrayList<>(datos);
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

        if(SPPac.getSelectedItemPosition() == 0){
            mostrarVentanaError("Error", "No se puede hacer la busqueda si no eliges al paciente primero");
        }else{
            loadCits(SPPac.getSelectedItem().toString());
        }
    }

private void loadCits(String Pac){
    new Thread(new Runnable() {
        @Override
        public void run() {
            citas = db.citDAO().consultCitaPac(Pac);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (citas.size() > 0) {
                        citasBusq = null;
                        citasBusq = new ArrayList<>(citas);
                        actualizarResultados(citas);
                    }else{
                        Toast.makeText(getBaseContext(), "No hay citas con ese criterio de busqueda", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }).start();
}

private void getIDcitVal(){
        try {
            String idCit = (String) idCita.getText().toString();
            if(idCit.equals("")){
                mostrarVentanaError("Error", "Ingresa el id de la cita a modificar");
            }else {
                int CitID = Integer.parseInt(idCit);
                boolean paso = false;
                String[] citaSelect = new String[7];
                Cita ci = null;
                if (CitID > 0) {
                    Log.i("Erors1", "entro:" + CitID + ":entro");
                    Log.i("Erors2", "m:" + citasBusq.size());
                    Log.i("Erors3", "l:" + citasBusq.get(0));
                    for (Cita m : citasBusq) {
                        if (m.getId() == CitID) {
                            paso = true;
                            ci = m;
                            citaSelect[0] = String.valueOf(ci.getId());
                            citaSelect[1] = ci.getDoctor();
                            citaSelect[2] = ci.getPaciente();
                            citaSelect[3] = ci.getFecha();
                            citaSelect[4] = ci.getHora();
                            citaSelect[5] = ci.getMotivo();
                            citaSelect[6] = ci.getEstado();

                            break;
                        }
                    }
                    if (paso) {
                        Intent i = new Intent(this, modifiCitSelect.class);
                        String clave_dat = "com.example.your_clinic.ID_CITA_SELECCIONADA";
                        i.putExtra(clave_dat, citaSelect);
                        idCita.setText("");
                        SPPac.setSelection(0);
                        startActivity(i);
                    } else {
                        mostrarVentanaError("Error", "No se encontro una cita con ese ID\n que coincida con alguna de las citas mostradas en la lista");
                    }
                } else {
                    Toast.makeText(this, "Ingresa un ID primero", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "No se pudo obtener el id", Toast.LENGTH_SHORT).show();
        }



}

public void loadCitSelect(View v){
        getIDcitVal();
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
        nombresAfiliados.add("Seleccionar paciente");
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


