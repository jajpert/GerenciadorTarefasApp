package com.example.gerenciadortarefas.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerenciadortarefas.R;
import com.example.gerenciadortarefas.database.AppDatabase;
import com.example.gerenciadortarefas.model.Usuario;

public class FragmentLogin extends Fragment {

    private EditText etEmail, etSenha;
    private Button btnLogin, btnCadastro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etSenha = view.findViewById(R.id.etSenha);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnCadastro = view.findViewById(R.id.btnCadastro);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String senha = etSenha.getText().toString();

            Usuario usuario = AppDatabase.getInstance(requireContext())
                    .usuarioDao()
                    .login(email, senha);

            if (usuario != null) {
                Toast.makeText(requireContext(), "Login realizado!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.action_login_to_listaTarefas);
            } else {
                Toast.makeText(requireContext(), "Email ou senha incorretos.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastro.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_login_to_cadastro)
        );

        return view;
    }
}