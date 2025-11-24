package com.example.your_clinic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cita;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<Cita> localDataSet;
    public int opcionImpres = 1;

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
        holder.getTextView().setText("Cita "+(position+1)+": \n"+localDataSet.get(position).seeall(opcionImpres));
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
