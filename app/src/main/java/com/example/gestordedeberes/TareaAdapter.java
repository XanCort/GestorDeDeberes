package com.example.gestordedeberes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder>{
    ArrayList<Tarea> coleccion;
    private OnItemClickListener listener;


    public TareaAdapter(ArrayList<Tarea> coleccion, OnItemClickListener listener) {
        this.coleccion = coleccion;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TareaAdapter.TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TareaAdapter.TareaViewHolder tareaViewHolder =
                new TareaViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_tarea,parent,false)
                );
        return tareaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TareaAdapter.TareaViewHolder holder, int position) {
        Tarea tarea = coleccion.get(position);
        if(tarea!=null){
            holder.tv_asignatura.setText(tarea.getAsignatura()+"");
            holder.tv_titulo.setText(tarea.getTitulo());
            holder.tv_descripcion.setText(tarea.getDescipcion());
            holder.tv_fecha.setText(tarea.getFecha());
            holder.tv_hora.setText(tarea.getHora());
            holder.tv_estado.setText(tarea.isEstado()?"Completado":"Pendiente");
        }
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(tarea);
            }
        });

    }

    public interface OnItemClickListener{
        public void onItemClick(Tarea tarea);
    }


    @Override
    public int getItemCount() {
        return coleccion.size();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder{
        TextView tv_asignatura;
        TextView tv_titulo;
        TextView tv_descripcion;
        TextView tv_fecha;
        TextView tv_hora;
        TextView tv_estado;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_asignatura = itemView.findViewById(R.id.textAsignatura);
            tv_titulo = itemView.findViewById(R.id.textTitulo);
            tv_descripcion = itemView.findViewById(R.id.textDescripcion);
            tv_fecha = itemView.findViewById(R.id.textFecha);
            tv_hora = itemView.findViewById(R.id.textHora);
            tv_estado = itemView.findViewById(R.id.textEstado);

        }
    }
}