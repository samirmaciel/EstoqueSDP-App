package com.samirmaciel.estoquesdp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samirmaciel.estoquesdp.banco.SqliteBanco;
import com.samirmaciel.estoquesdp.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {
    private SqliteBanco sqliteBanco;
    private SQLiteDatabase banco;

    public HistoricoDAO(Context ct) {
        sqliteBanco = new SqliteBanco(ct);
        banco = sqliteBanco.getWritableDatabase();
    }

    public void inserirProduto(Produto produto){
        ContentValues values = new ContentValues();
        values.put("uid", produto.getUId());
        values.put("foto", produto.getFoto());
        values.put("categoria", produto.getCategoria());
        values.put("codigo", produto.getCodigo());
        values.put("piso", produto.getPiso());
        values.put("puxada", produto.getPuxada());
        values.put("pratileira", produto.getPratileira());
        long num = banco.insert("historico", null, values);
    }

   public Produto buscarProduto(String codigo){
       Cursor cursor = banco.query("historico", new String[]{"uid", "foto", "categoria", "codigo", "piso", "puxada", "pratileira"}, null, null, null, null, null);
       List<Produto> produtos = new ArrayList<>();
       while (cursor.moveToNext()){
           if(cursor.getString(3).equalsIgnoreCase(codigo)){
               Produto produto = new Produto();
               produto.setUId(cursor.getString(0));
               produto.setFoto(cursor.getString(1));
               produto.setCategoria(cursor.getString(2));
               produto.setCodigo(cursor.getString(3));
               produto.setPiso(cursor.getString(4));
               produto.setPuxada(cursor.getString(5));
               produto.setPratileira(cursor.getString(6));
               return produto;
           }
       }

       return null;
   }

    public void excluirProduto(String codigo){
        banco.delete("historico", "codigo == ?", new String[]{codigo});
    }

    public List<Produto> obterTodos(){
        Cursor cursor = banco.query("historico", new String[]{"uid", "foto", "categoria", "codigo", "piso", "puxada", "pratileira"}, null, null, null, null, null);
        List<Produto> produtos = new ArrayList<>();
        while (cursor.moveToNext()){
            Produto produto = new Produto();
            produto.setUId(cursor.getString(0));
            produto.setFoto(cursor.getString(1));
            produto.setCategoria(cursor.getString(2));
            produto.setCodigo(cursor.getString(3));
            produto.setPiso(cursor.getString(4));
            produto.setPuxada(cursor.getString(5));
            produto.setPratileira(cursor.getString(6));
            produtos.add(produto);
        }

        return produtos;
    }

    public void limparHistorico(){
        banco.delete("historico", null, null);
    }
}
