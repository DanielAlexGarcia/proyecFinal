package com.example.your_clinic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entidades.Afiliado;
import db.ClinicaBD;

public class classActivityGestionAfil extends AppCompatActivity {

    private ClinicaBD db;
    private RecyclerView listAfiliados;
    private CustomAdapter2 adapter;
    private List<Afiliado> listaAfiliados;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_afiliados);

        db = ClinicaBD.getAppDatabase((getBaseContext()));
        listAfiliados = findViewById(R.id.listasAfiliados);
        listAfiliados.setLayoutManager(new LinearLayoutManager(this));

        cargarAfiliados();
    }
    private void cargarAfiliados() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listaAfiliados = db.afilDAO().allAfiliados();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Inicializa el CustomAdapter2 aquí
                        adapter = new CustomAdapter2(classActivityGestionAfil.this, listaAfiliados, db);
                        listAfiliados.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public void addAfil(View v){
        Intent i = new Intent(this, addAfiliado.class);
        startActivity(i);
    }

}

class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder> { // Corregido: usa CustomAdapter2.ViewHolder

    private List<Afiliado> listaDeDatos;
    private final ClinicaBD db; // Referencia a la BD
    private final Context context; // Para usar Toast y runOnUiThread

    // Constructor para inicializar los datos, la DB y el contexto (Activity)
    public CustomAdapter2(Context context, List<Afiliado> listaDeDatos, ClinicaBD db) {
        this.context = context;
        this.listaDeDatos = listaDeDatos;
        this.db = db;
    }

    // 1. Creación del ViewHolder (Infla el layout del ítem)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CORREGIR: Usa el nuevo layout que contiene el TextView y el ImageButton
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_afiliado, parent, false); // <--- Usa tu nuevo layout aquí
        return new ViewHolder(view);
    }

    // 2. Vinculación de datos a las vistas (El corazón de la lógica)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Usamos 'final int pos = position' si la API es antigua, pero con Java 8+ se puede usar 'holder.getAdapterPosition()'
        final Afiliado entidadActual = listaDeDatos.get(position);

        // Mostrar el dato en el TextView
        // Usamos el meatodo que tengas para mostrar el texto completo del afiliado
        holder.textViewDato.setText(entidadActual.seeAll());

        if(position == 0){
            holder.imageButtonEliminar.setVisibility(View.GONE);
        }else {
            holder.imageButtonEliminar.setVisibility(View.VISIBLE);
            // Configurar el Click Listener para el botón de eliminar
            holder.imageButtonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Confirmación opcional antes de eliminar (¡buena práctica!)
                    mostrarDialogoConfirmacion(entidadActual, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaDeDatos.size();
    }

    /**
     * Clase ViewHolder: Almacena y proporciona acceso a todas las vistas de un ítem.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDato;
        public ImageButton imageButtonEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            // CORREGIR: Inicializa las vistas con sus IDs
            textViewDato = itemView.findViewById(R.id.itemAfiliado); // <--- ID de tu TextView en item_afiliado_eliminar.xml
            imageButtonEliminar = itemView.findViewById(R.id.btnDeletAfiliado); // <--- ID de tu ImageButton en item_afiliado_eliminar.xml
        }
    }


    /**
     * Muestra un diálogo de confirmación antes de eliminar.
     */
    private void mostrarDialogoConfirmacion(final Afiliado entidad, final int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar Eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar a " + entidad.getNombres()+" "+entidad.getPrimerAP()+" "+entidad.getSegundoAP() + "?")
                .setPositiveButton("Sí, Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarDato(entidad, position);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    /**
     * Método para eliminar el dato de la BD y del RecyclerView.
     */
    private void eliminarDato(final Afiliado entidad, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 1. ELIMINAR DE LA BASE DE DATOS
                db.afilDAO().DeletAfiliado(entidad);

                // 2. ACTUALIZAR LA INTERFAZ DE USUARIO (RecyclerView)
                ((classActivityGestionAfil) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Eliminar de la lista local
                        listaDeDatos.remove(position);

                        // Notificar al RecyclerView con eficiencia
                        notifyItemRemoved(position);

                        // Opcional: Para asegurar que la animación se vea bien y los índices se recalcuen
                        // Si se usa notifyItemRemoved, no es necesario notifyDataSetChanged()
                        // notifyDataSetChanged();

                        Toast.makeText(context, "Afiliado eliminado con éxito.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}