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
    Spinner SPEstado, SPNomDoc;
    RadioGroup RBGBusq;
    RecyclerView resultados;
    ArrayList<Cita> datos = null;

    RadioButton RBBusqEstado, RBBusqNomDoc;
    CustomAdapter adapter;

    RecyclerView.LayoutManager layoutManager;


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

        List<Cita> listaInicial = new ArrayList<>();



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

        // El método devuelve el ID del recurso (R.id.tu_radio_button) o -1 si no hay ninguno.
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





class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<Cita> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private final TextView textView2;
        private final TextView textView3;
        private final TextView textView4;

        public ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.itemCitaView1);
            textView2 = view.findViewById(R.id.itemCitaView2);
            textView3 = view.findViewById(R.id.itemCitaView3);
            textView4 = view.findViewById(R.id.itemCitaView4);
        }
        public TextView getTextView(){return textView;}
        public TextView getTextView2(){return textView2;}
        public TextView getTextView3(){return textView3;}
        public TextView getTextView4(){return textView4;}
    }

    public CustomAdapter(ArrayList<Cita> dataset){
        localDataSet = dataset;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_cita, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText("Cita "+(position+1)+": \n"+localDataSet.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
    public void updateData(List<Cita> newData) {
        localDataSet.clear();
        localDataSet.addAll(newData);
        notifyDataSetChanged();
    }


}


