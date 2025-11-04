package com.example.gerenciadortarefas.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerenciadortarefas.R;
import com.example.gerenciadortarefas.database.AppDatabase;
import com.example.gerenciadortarefas.model.Tarefa;

import java.util.List;

public class FragmentListaTarefas extends Fragment {

    private RecyclerView recyclerView;
    private EditText etBuscar;
    private Button btnNovaTarefa;
    private TarefaAdapter adapter;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_tarefas, container, false);

        recyclerView = view.findViewById(R.id.recyclerTarefas);
        etBuscar = view.findViewById(R.id.etBuscar);
        btnNovaTarefa = view.findViewById(R.id.btnNovaTarefa);
        db = AppDatabase.getInstance(requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        carregarTarefas();

        btnNovaTarefa.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_listaTarefas_to_novaTarefa)
        );

        // busca dinâmica
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarTarefas(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void carregarTarefas() {
        List<Tarefa> lista = db.tarefaDao().getAllTarefas();
        adapter = new TarefaAdapter(lista, tarefa -> {
            db.tarefaDao().delete(tarefa);
            Toast.makeText(requireContext(), "Tarefa excluída", Toast.LENGTH_SHORT).show();
            carregarTarefas();
        });
        recyclerView.setAdapter(adapter);
    }

    private void buscarTarefas(String termo) {
        List<Tarefa> lista = db.tarefaDao().searchByTitulo(termo);
        adapter = new TarefaAdapter(lista, tarefa -> {
            db.tarefaDao().delete(tarefa);
            carregarTarefas();
        });
        recyclerView.setAdapter(adapter);
    }
}