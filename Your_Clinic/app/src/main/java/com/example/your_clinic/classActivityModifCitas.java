package com.example.your_clinic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
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
import plantillas.PlantillasComponentes;

public class classActivityModifCitas extends AppCompatActivity{
    private ClinicaBD db;
    private ArrayList<Afiliado> lista;
    private ArrayAdapter<String> adapter;
    private RecyclerView results;

    private EditText busq;

    private ArrayList<Cita> datos = null;
    private RecyclerView.LayoutManager layoutManager;
    private List<Cita> citas;
    private ArrayList<Cita> citasBusq;
    private CitasAdapter adapterCitas;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_cita);

        results = findViewById(R.id.ResultsViewModif);
        results.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        results.setLayoutManager(layoutManager);
        busq = findViewById(R.id.txtBusqNomPac);



        db = ClinicaBD.getAppDatabase((getBaseContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                datos = (ArrayList<Cita>) db.citDAO().AllCitas();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 1. INICIALIZACIÓN DEL ADAPTADOR Y LA LISTA INICIAL
                        if (datos != null) {
                            citasBusq = new ArrayList<>(datos);
                            // Crear el adaptador pasando la lista cargada
                            adapterCitas = new CitasAdapter(classActivityModifCitas.this, citasBusq, db);
                            // Asignar el adaptador al RecyclerView
                            results.setAdapter(adapterCitas);
                        } else {
                            Toast.makeText(classActivityModifCitas.this, "No se encontraron citas.", Toast.LENGTH_LONG).show();
                            citasBusq = new ArrayList<>(); // Inicializar lista vacía
                            adapterCitas = new CitasAdapter(classActivityModifCitas.this, citasBusq, db);
                            results.setAdapter(adapterCitas);
                        }
                    }
                });
            }
        }).start();


        busq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void filter(String text) {
        ArrayList<Cita> listaFiltrada = new ArrayList<>();

        if (datos == null) return;
        for (Cita cita : datos) {
            String nombreCompleto = cita.getPaciente();

            if (nombreCompleto != null && nombreCompleto.toLowerCase().contains(text.toLowerCase())) {
                listaFiltrada.add(cita);
            }
        }

        if (adapterCitas != null) {
            adapterCitas.updateData(listaFiltrada);
        } else {
            Toast.makeText(this, "Datos no cargados completamente", Toast.LENGTH_SHORT).show();
        }
        if(listaFiltrada == null || listaFiltrada.size()<1){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "No hay datos que coincidan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void loadResultsModifi(View v){

    }


private void getIDcitVal(){
        try {

        } catch (Exception e) {
            Toast.makeText(this, "No se pudo obtener el id", Toast.LENGTH_SHORT).show();
        }



}

public void loadCitSelect(View v){
        getIDcitVal();
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






}

class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.ViewHolder> { // Renombrado

    private List<Cita> listaDeDatos;
    private final ClinicaBD db;
    private final Context context;
    PlantillasComponentes PComp = new PlantillasComponentes();

    public CitasAdapter(Context context, List<Cita> listaDeDatos, ClinicaBD db) {
        this.context = context;
        this.listaDeDatos = listaDeDatos;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_select_modificar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Cita citaActual = listaDeDatos.get(position);
        holder.textViewDato.setText(citaActual.seeall(1));

        holder.imageButtonReagendar.setVisibility(View.VISIBLE);

        holder.imageButtonReagendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoModificacion(citaActual, holder.getAdapterPosition());
            }
        });
    }

    public void updateData(List<Cita> nuevaLista) {
        this.listaDeDatos.clear();
        this.listaDeDatos.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaDeDatos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDato;
        public ImageButton imageButtonReagendar;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewDato = itemView.findViewById(R.id.itemList);
            imageButtonReagendar = itemView.findViewById(R.id.imgBtnModCit);
        }
    }


    private void mostrarDialogoModificacion(final Cita cita, final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_reagendar_cita, null);
        dialogBuilder.setView(dialogView);

        final TextView txtNombrePaciente = dialogView.findViewById(R.id.txt_paciente_nombre);
        EditText editFecha = dialogView.findViewById(R.id.edit_fecha);
        PComp.configurarFormatoFecha(editFecha);
        EditText editHora = dialogView.findViewById(R.id.edit_hora);
        PComp.configurarFormatoHora(editHora);

        txtNombrePaciente.setText(cita.getPaciente());
        editFecha.setText(cita.getFecha());
        editHora.setText(cita.getHora());

        dialogBuilder.setTitle("Reagendar Cita : ");
        dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                final String nuevaFecha = editFecha.getText().toString();
                final String nuevaHora = editHora.getText().toString();

                if (nuevaFecha.isEmpty() || nuevaHora.isEmpty()) {
                    Toast.makeText(context, "Fecha y hora no pueden estar vacías.", Toast.LENGTH_SHORT).show();
                    return;
                }

                actualizarCita(cita, nuevaFecha, nuevaHora, position);
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", null);
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void actualizarCita(final Cita citaOriginal, final String nuevaFecha, final String nuevaHora, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                citaOriginal.setFecha(nuevaFecha);
                citaOriginal.setHora(nuevaHora);
                db.citDAO().actualizarCita(citaOriginal.getFecha(),citaOriginal.getHora(), "Reprogramada", citaOriginal.getId() );

                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemChanged(position);

                        Toast.makeText(context, "Cita de  " + citaOriginal.getPaciente() + " reagendada con éxito.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}


