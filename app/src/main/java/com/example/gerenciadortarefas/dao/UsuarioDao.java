package com.example.gerenciadortarefas.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gerenciadortarefas.model.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    // Inserir novo usuário
    @Insert
    void insert(Usuario usuario);

    // Atualizar usuário existente
    @Update
    void update(Usuario usuario);

    // Excluir usuário
    @Delete
    void delete(Usuario usuario);

    // Listar todos os usuários
    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsuarios();

    // Buscar usuário por e-mail e senha (para login)
    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario login(String email, String senha);
}