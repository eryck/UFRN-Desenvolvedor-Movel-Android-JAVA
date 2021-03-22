package com.example.gerenciadordelivros.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String BD_NAME = "livrosbd";
    public static final int BD_VERSION = 1;

    private static DBHelper instance;

    //Cria a tabela
    private static String SQL_CREATE = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL)",
            LivroContract.TABLE_NAME,
            LivroContract.Colums._ID,
            LivroContract.Colums.titulo,
            LivroContract.Colums.autor,
            LivroContract.Colums.editora,
            LivroContract.Colums.emprestado
    );

    //Apaga a Tabela
    private static String SQL_DROP = "DROP TABLE IF EXISTS " + LivroContract.TABLE_NAME;

    private DBHelper(Context context){
        super(context, BD_NAME, null, BD_VERSION);
    }

    //Cria uma instancia da classe caso não exista
    public static DBHelper getInstance(Context context){
        if(instance == null){
            instance = new DBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(SQL_DROP); //Exclui uma tabela de uma instalação anterior
        bd.execSQL(SQL_CREATE); //Cria a nova tabela
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        onCreate(bd);
    }
}
