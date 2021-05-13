package com.samirmaciel.estoquesdp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samirmaciel.estoquesdp.banco.SqliteBanco;
import com.samirmaciel.estoquesdp.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private SqliteBanco sqliteBanco;
    private SQLiteDatabase banco;

    public ProdutoDAO(Context contexto) {
        sqliteBanco = new SqliteBanco(contexto);
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

        long num = banco.insert("produtos", null, values);
    }

    public Produto buscarProduto(String codigo){
        Cursor cursor = banco.query("produtos", new String[]{"uid", "foto", "categoria", "codigo", "piso", "puxada", "pratileira"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            if(cursor.getString(3).equals(codigo)){
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

    public List<Produto> obterProdutos(){
        Cursor cursor = banco.query("produtos", new String[]{"uid", "foto", "categoria", "codigo", "piso", "puxada", "pratileira"}, null, null, null, null, null);
        List<Produto> lista = new ArrayList<>();
        while (cursor.moveToNext()){
            Produto produto = new Produto();
            produto.setUId(cursor.getString(0));
            produto.setFoto(cursor.getString(1));
            produto.setCategoria(cursor.getString(2));
            produto.setCodigo(cursor.getString(3));
            produto.setPiso(cursor.getString(4));
            produto.setPuxada(cursor.getString(5));
            produto.setPratileira(cursor.getString(6));
            lista.add(produto);
        }
        return lista;
    }

    public void excluirProduto(String codigo){
        banco.delete("produtos", "codigo == ?", new String[]{codigo});
    }

    public Produto buscarProdutoPorUId(String uid){
        Cursor cursor = banco.query("produtos", new String[]{"uid", "foto", "categoria", "codigo", "piso", "puxada", "pratileira"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            if(cursor.getString(0).equalsIgnoreCase(uid)){
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

    public void atualizarProduto(Produto produto){
        ContentValues values = new ContentValues();
        values.put("uid", produto.getUId());
        values.put("foto", produto.getFoto());
        values.put("categoria", produto.getCategoria());
        values.put("codigo", produto.getCodigo());
        values.put("piso", produto.getPiso());
        values.put("puxada", produto.getPuxada());
        values.put("pratileira", produto.getPratileira());

        banco.update("produtos", values, "uid == ?", new String[]{String.valueOf(produto.getUId())});
    }

}
