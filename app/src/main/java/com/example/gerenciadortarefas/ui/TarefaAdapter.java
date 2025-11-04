package com.example.gerenciadortarefas.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadortarefas.R;
import com.example.gerenciadortarefas.model.Tarefa;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {

    public interface OnDeleteClickListener {
        void onDeleteClick(Tarefa tarefa);
    }

    private List<Tarefa> tarefas;
    private OnDeleteClickListener listener;

    public TarefaAdapter(List<Tarefa> tarefas, OnDeleteClickListener listener) {
        this.tarefas = tarefas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarefa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarefa tarefa = tarefas.get(position);
        holder.tvTitulo.setText(tarefa.getTitulo());
        holder.tvStatus.setText(tarefa.getStatus());
        holder.tvData.setText(tarefa.getData());
        holder.btnExcluir.setOnClickListener(v -> listener.onDeleteClick(tarefa));
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvStatus, tvData;
        ImageButton btnExcluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvData = itemView.findViewById(R.id.tvData);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }
    }
}