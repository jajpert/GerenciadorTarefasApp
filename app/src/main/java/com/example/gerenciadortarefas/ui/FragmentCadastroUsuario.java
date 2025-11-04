package com.example.gerenciadortarefas.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gerenciadortarefas.R;
import com.example.gerenciadortarefas.database.AppDatabase;
import com.example.gerenciadortarefas.model.Usuario;

import java.io.IOException;

public class FragmentCadastroUsuario extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imgFoto;
    private EditText etNome, etEmail, etSenha;
    private Button btnSalvar, btnFoto;
    private Uri fotoUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_usuario, container, false);

        imgFoto = view.findViewById(R.id.imgFoto);
        etNome = view.findViewById(R.id.etNome);
        etEmail = view.findViewById(R.id.etEmail);
        etSenha = view.findViewById(R.id.etSenha);
        btnSalvar = view.findViewById(R.id.btnSalvar);
        btnFoto = view.findViewById(R.id.btnFoto);

        btnFoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        });

        btnSalvar.setOnClickListener(v -> {
            String nome = etNome.getText().toString();
            String email = etEmail.getText().toString();
            String senha = etSenha.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || fotoUri == null) {
                Toast.makeText(requireContext(), "Preencha todos os campos e tire uma foto.", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setFotoUri(fotoUri.toString());

            AppDatabase.getInstance(requireContext()).usuarioDao().insert(usuario);

            Toast.makeText(requireContext(), "Usuário cadastrado!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_cadastro_to_login);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            imgFoto.setImageBitmap(imageBitmap);

            // salva a imagem na galeria e pega a URI
            String path = MediaStore.Images.Media.insertImage(
                    requireActivity().getContentResolver(),
                    imageBitmap,
                    "foto_usuario",
                    null
            );
            fotoUri = Uri.parse(path);
        }
    }
}