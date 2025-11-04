package com.example.gerenciadortarefas.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gerenciadortarefas.model.Tarefa;

import java.util.List;

@Dao
public interface TarefaDao {

    // Inserir tarefa
    @Insert
    void insert(Tarefa tarefa);

    // Atualizar tarefa
    @Update
    void update(Tarefa tarefa);

    // Excluir tarefa
    @Delete
    void delete(Tarefa tarefa);

    // Listar todas as tarefas
    @Query("SELECT * FROM tarefas ORDER BY id DESC")
    List<Tarefa> getAllTarefas();

    // Buscar tarefas por título (para a funcionalidade de busca)
    @Query("SELECT * FROM tarefas WHERE titulo LIKE '%' || :titulo || '%' ORDER BY id DESC")
    List<Tarefa> searchByTitulo(String titulo);
}