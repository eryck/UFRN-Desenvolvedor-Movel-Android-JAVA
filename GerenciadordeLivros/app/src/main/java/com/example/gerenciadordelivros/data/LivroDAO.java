package com.example.gerenciadordelivros.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gerenciadordelivros.dominio.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private static SQLiteDatabase bd;
    private static LivroDAO instance;

    private LivroDAO(Context context){
        DBHelper dbHelper = DBHelper.getInstance(context);
        bd = dbHelper.getWritableDatabase();
    }

    public static LivroDAO getInstance(Context context){
        if(instance == null){
            instance = new LivroDAO(context.getApplicationContext());
        }
        return instance;
    }

    //Lista os dados do banco
    public static List<Livro> list(){
        String[] columns = {
                LivroContract.Colums._ID,
                LivroContract.Colums.titulo,
                LivroContract.Colums.autor,
                LivroContract.Colums.editora,
                LivroContract.Colums.emprestado,
        };

        List<Livro> livros = new ArrayList<>();

        try(
                Cursor c = bd.query(LivroContract.TABLE_NAME, columns,
                        null,
                        null,
                        null,
                        null,
                        LivroContract.Colums.titulo);
                ){
            if(c.moveToFirst()){
                do {
                    Livro l = LivroDAO.fromCursor(c);
                    livros.add(l);
                }while (c.moveToNext());
            }
        }

        return livros;
    }

    private static Livro fromCursor(Cursor c){
        Long id = c.getLong(c.getColumnIndex(LivroContract.Colums._ID));
        String titulo = c.getString(c.getColumnIndex(LivroContract.Colums.titulo));
        String autor = c.getString(c.getColumnIndex(LivroContract.Colums.autor));
        String editora = c.getString(c.getColumnIndex(LivroContract.Colums.editora));
        int emprestado = c.getInt(c.getColumnIndex(LivroContract.Colums.emprestado));

        return new Livro(id, titulo, autor, editora, emprestado);
    }

    //Insere
    public void save(Livro livro){

        ContentValues values = new ContentValues();
        values.put(LivroContract.Colums.titulo, livro.getTitulo());
        values.put(LivroContract.Colums.autor, livro.getAutor());
        values.put(LivroContract.Colums.editora, livro.getEditora());
        values.put(LivroContract.Colums.emprestado, livro.getEmprestado());

        Long id = bd.insert(LivroContract.TABLE_NAME, null, values);
        livro.setId(id);
    }

    //Atualiza
    public void update(Livro livro){
        ContentValues values = new ContentValues();
        values.put(LivroContract.Colums.titulo, livro.getTitulo());
        values.put(LivroContract.Colums.autor, livro.getAutor());
        values.put(LivroContract.Colums.editora, livro.getEditora());
        values.put(LivroContract.Colums.emprestado, livro.getEmprestado());

        bd.update(LivroContract.TABLE_NAME, values,
                LivroContract.Colums._ID + "=?",
                new String[]{String.valueOf(livro.getId())});
    }

    //Delete
    public void  delete(Livro livro){
        bd.delete(LivroContract.TABLE_NAME,
                LivroContract.Colums._ID + "=?",
                new String[]{String.valueOf(livro.getId())});
    }
}
