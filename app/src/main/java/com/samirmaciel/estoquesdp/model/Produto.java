package com.samirmaciel.estoquesdp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Produto implements Parcelable {
    private String uid;
    private String foto;
    private String categoria;
    private String codigo;
    private String piso;
    private String puxada;
    private String pratileira;

    public Produto() {
    }

    protected Produto(Parcel in) {
        uid = in.readString();
        foto = in.readString();
        categoria = in.readString();
        codigo = in.readString();
        piso = in.readString();
        puxada = in.readString();
        pratileira = in.readString();
    }

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    public String getUId() {
        return uid;
    }

    public void setUId(String uid) {
        this.uid = uid;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPuxada() {
        return puxada;
    }

    public void setPuxada(String puxada) {
        this.puxada = puxada;
    }

    public String getPratileira() {
        return pratileira;
    }

    public void setPratileira(String pratileira) {
        this.pratileira = pratileira;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(foto);
        dest.writeString(categoria);
        dest.writeString(codigo);
        dest.writeString(piso);
        dest.writeString(puxada);
        dest.writeString(pratileira);
    }
}

