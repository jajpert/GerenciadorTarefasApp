package com.example.gerenciadortarefas.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.gerenciadortarefas.model.Usuario;
import com.example.gerenciadortarefas.model.Tarefa;
import com.example.gerenciadortarefas.dao.UsuarioDao;
import com.example.gerenciadortarefas.dao.TarefaDao;

@Database(entities = {Usuario.class, Tarefa.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UsuarioDao usuarioDao();
    public abstract TarefaDao tarefaDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "gerenciador_tarefas_db"
            ).allowMainThreadQueries().build(); // por simplicidade, permitimos queries na main
        }
        return instance;
    }
}