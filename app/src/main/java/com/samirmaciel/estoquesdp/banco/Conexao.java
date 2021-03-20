package com.samirmaciel.estoquesdp.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {
    private static final String name = "banco.db";
    private static final int version = 5;


    public Conexao(Context context) {
        super( context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_produtos = "create table produtos (uid string primary key," +
                "foto string, categoria varchar(50), codigo varchar(5), piso varchar(2), puxada varchar(20), pratileira varchar(2));";

        String sql_historico = "create table historico (uid string primary key," +
                "foto string, categoria varchar(50), codigo varchar(5), piso varchar(2), puxada varchar(20), pratileira varchar(2));";

        db.execSQL(sql_produtos);
        db.execSQL(sql_historico);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists produtos;");
        db.execSQL("drop table if exists historico;");
        onCreate(db);
    }
}
