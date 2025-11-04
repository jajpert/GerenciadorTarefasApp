package com.example.gerenciadortarefas.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerenciadortarefas.R;
import com.example.gerenciadortarefas.database.AppDatabase;
import com.example.gerenciadortarefas.model.Tarefa;

public class FragmentNovaTarefa extends Fragment {

    private EditText etTitulo, etDescricao, etData;
    private Spinner spStatus;
    private Button btnSalvar;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nova_tarefa, container, false);

        etTitulo = view.findViewById(R.id.etTitulo);
        etDescricao = view.findViewById(R.id.etDescricao);
        etData = view.findViewById(R.id.etData);
        spStatus = view.findViewById(R.id.spStatus);
        btnSalvar = view.findViewById(R.id.btnSalvarTarefa);

        String[] statusList = {"Pendente", "Concluída"};
        spStatus.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, statusList));

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.som_salvar);

        btnSalvar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString();
            String descricao = etDescricao.getText().toString();
            String data = etData.getText().toString();
            String status = spStatus.getSelectedItem().toString();

            if (titulo.isEmpty() || descricao.isEmpty() || data.isEmpty()) {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Tarefa tarefa = new Tarefa();
            tarefa.setTitulo(titulo);
            tarefa.setDescricao(descricao);
            tarefa.setData(data);
            tarefa.setStatus(status);

            AppDatabase.getInstance(requireContext()).tarefaDao().insert(tarefa);

            mediaPlayer.start();
            Toast.makeText(requireContext(), "Tarefa salva!", Toast.LENGTH_SHORT).show();

            Navigation.findNavController(v).navigateUp();
        });

        return view;
    }
}