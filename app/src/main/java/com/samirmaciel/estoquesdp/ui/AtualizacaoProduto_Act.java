package com.samirmaciel.estoquesdp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.samirmaciel.estoquesdp.R;
import com.samirmaciel.estoquesdp.model.Produto;
import com.samirmaciel.estoquesdp.model.ToastPersonalizado;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AtualizacaoProduto_Act extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Spinner spinnerAttCategoria, spinnerAttPuxada, spinnerAttPiso, spinnerAttPratileira;
    private Button btnAttProduto;
    private Button btnVoltarAtt;
    private ImageView imageAttCapturada;
    private TextView textCodigo;
    private List<String> listaCategorias, listaPiso, listaPuxada, listaPratileira;
    private ProgressBar progressBaratt;

    private String imageStringAttCapturada;
    private String categoria, piso, puxada, pratileira;
    private Produto produtoRecuperado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizacao_produto);

        progressBaratt = findViewById(R.id.progressBaratt);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        String codigo = getIntent().getExtras().getString("codigo");




        spinnerAttCategoria = findViewById(R.id.spinnerAtualizarCategoria);
        spinnerAttPiso = findViewById(R.id.spinnerAtualizarPiso);
        spinnerAttPuxada = findViewById(R.id.spinnerAtualizarPuxada);
        spinnerAttPratileira = findViewById(R.id.spinnerAtualizarPratileira);
        imageAttCapturada = findViewById(R.id.imageviewAtualizaCapturada);
        textCodigo = findViewById(R.id.textCodigo);
        btnVoltarAtt = findViewById(R.id.btnVoltarAtt);
        btnAttProduto = findViewById(R.id.btnAtualizarProduto);

        listaCategorias = new ArrayList<>();
        listaCategorias.add("CATEGORIA");
        listaCategorias.add("ANABELA");
        listaCategorias.add("BIRKEN");
        listaCategorias.add("BOLSA");
        listaCategorias.add("BOTA");
        listaCategorias.add("CONFORT");
        listaCategorias.add("CHINELO");
        listaCategorias.add("FLATFORM");
        listaCategorias.add("TAMANCO");
        listaCategorias.add("TÊNIS");
        listaCategorias.add("MOCASSIM");
        listaCategorias.add("MULE");
        listaCategorias.add("PLATAFORMA");
        listaCategorias.add("RASTEIRA");
        listaCategorias.add("SANDÁLIA BAIXA");
        listaCategorias.add("SANDÁLIA SALTO");
        listaCategorias.add("SAPATILHA");
        listaCategorias.add("SCARPIN");

        listaPuxada = new ArrayList<>();
        listaPuxada.add("PUXADA");
        listaPuxada.add("JULIANA A");
        listaPuxada.add("JULIANA B");
        listaPuxada.add("JÚLIA A");
        listaPuxada.add("JÚLIA B");
        listaPuxada.add("DIANA A");
        listaPuxada.add("DIANA B");
        listaPuxada.add("RUYARA A");
        listaPuxada.add("RUYARA B");
        listaPuxada.add("PAREDE A");
        listaPuxada.add("PAREDE B");
        listaPuxada.add("PAREDE C");
        listaPuxada.add("PAREDE D");
        listaPuxada.add("PAREDE E");
        listaPuxada.add("ILHA");

        listaPiso = new ArrayList<>();
        listaPiso.add("PISO");
        listaPiso.add("1");
        listaPiso.add("2");
        listaPiso.add("3");
        listaPiso.add("COZINHA");

        listaPratileira = new ArrayList<>();
        listaPratileira.add("PRATILEIRA");
        listaPratileira.add("1");
        listaPratileira.add("2");
        listaPratileira.add("3");
        listaPratileira.add("4");
        listaPratileira.add("5");
        listaPratileira.add("6");
        listaPratileira.add("7");
        listaPratileira.add("--");

        ArrayAdapter adapterCategoria = new ArrayAdapter(this, R.layout.spinner_item, listaCategorias);
        adapterCategoria.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerAttCategoria.setAdapter(adapterCategoria);

        ArrayAdapter adapterPuxada = new ArrayAdapter(this, R.layout.spinner_item, listaPuxada);
        adapterPuxada.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerAttPuxada.setAdapter(adapterPuxada);

        ArrayAdapter adapterPiso = new ArrayAdapter(this, R.layout.spinner_item, listaPiso);
        adapterPiso.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerAttPiso.setAdapter(adapterPiso);

        ArrayAdapter adapterPratileira = new ArrayAdapter(this, R.layout.spinner_item, listaPratileira);
        adapterPratileira.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerAttPratileira.setAdapter(adapterPratileira);



        spinnerAttCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoria = (String) parent.getSelectedItem();
            }
        });

        spinnerAttPuxada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                puxada = (String) parent.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                puxada = (String) parent.getSelectedItem();
            }
        });

        spinnerAttPiso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                piso = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                piso = (String) parent.getSelectedItem();
            }
        });

        spinnerAttPratileira.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pratileira = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pratileira = (String) parent.getSelectedItem();
            }
        });


        btnAttProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checarCampos()){
                    produtoRecuperado.setFoto(imageStringAttCapturada);
                    produtoRecuperado.setPratileira(pratileira);
                    produtoRecuperado.setPuxada(puxada);
                    produtoRecuperado.setCategoria(categoria);
                    produtoRecuperado.setPiso(piso);
                    databaseReference.child("Produtos").child(textCodigo.getText().toString()).setValue(produtoRecuperado);
                    ToastPersonalizado.showToast(1, "Produto atualizado!", AtualizacaoProduto_Act.this, getApplicationContext());
                    onBackPressed();
                }else{
                    ToastPersonalizado.showToast(2, "Preencha todos os campos!", AtualizacaoProduto_Act.this, getApplicationContext());
                }
            }
        });

        imageAttCapturada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarImagem();
            }
        });

        btnVoltarAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        procurarProdutoNoFireBase(codigo);

    }

    public void capturarImagem(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    public boolean checarCampos(){
        if(!categoria.equalsIgnoreCase("categoria")){
            if(!piso.equalsIgnoreCase("piso")){
                if(!puxada.equalsIgnoreCase("puxada")){
                    if(!pratileira.equalsIgnoreCase("pratileira")){
                        if (imageStringAttCapturada == null){
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            try{
                Bitmap imageCamera = (Bitmap) data.getExtras().get("data");
                imageAttCapturada.setImageBitmap(imageCamera);

                byte[] logoBytes;
                ByteArrayOutputStream streamImagem = new ByteArrayOutputStream();
                imageCamera.compress(Bitmap.CompressFormat.PNG, 100, streamImagem);
                logoBytes = streamImagem.toByteArray();
                imageStringAttCapturada = Base64.encodeToString(logoBytes, Base64.DEFAULT);

            }catch (Exception e){

            }
        }
    }

    private void setarInformacoesDoProduto(Produto produto){
        for (int x = 0; x <= listaCategorias.size(); x++){
            if(listaCategorias.get(x).equalsIgnoreCase(produto.getCategoria())){
                spinnerAttCategoria.setSelection(x);
                break;
            }
        }

        for (int x = 0; x <= listaPiso.size(); x++){
            if(listaPiso.get(x).equalsIgnoreCase(produto.getPiso())){
                spinnerAttPiso.setSelection(x);
                break;
            }
        }

        for (int x = 0; x <= listaPuxada.size(); x++){
            if(listaPuxada.get(x).equalsIgnoreCase(produto.getPuxada())){
                spinnerAttPuxada.setSelection(x);
                break;
            }
        }

        for (int x = 0; x <= listaPratileira.size(); x++){
            if(listaPratileira.get(x).equalsIgnoreCase(produto.getPratileira())){
                spinnerAttPratileira.setSelection(x);
                break;
            }
        }
        imageStringAttCapturada = produto.getFoto();
        textCodigo.setText(produto.getCodigo());
        byte[] fotoembyte;
        fotoembyte = Base64.decode(imageStringAttCapturada, Base64.DEFAULT);
        Bitmap imagemBitmap = BitmapFactory.decodeByteArray(fotoembyte, 0, fotoembyte.length);
        imageAttCapturada.setImageBitmap(imagemBitmap);

    }


    private void procurarProdutoNoFireBase(String codigo){
        exibirProgresso(true);
        Query query = databaseReference.child("Produtos").orderByChild("codigo").equalTo(codigo);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exibirProgresso(true);
                for(DataSnapshot objeto: snapshot.getChildren()){
                    produtoRecuperado = objeto.getValue(Produto.class);
                }
                for (int x = 0; x < listaCategorias.size(); x++){
                    if(listaCategorias.get(x).equalsIgnoreCase(produtoRecuperado.getCategoria())){
                        spinnerAttCategoria.setSelection(x);
                        break;
                    }
                }

                for (int x = 0; x < listaPiso.size(); x++){
                    if(listaPiso.get(x).equalsIgnoreCase(produtoRecuperado.getPiso())){
                        spinnerAttPiso.setSelection(x);
                        break;
                    }
                }

                for (int x = 0; x < listaPuxada.size(); x++){
                    if(listaPuxada.get(x).equalsIgnoreCase(produtoRecuperado.getPuxada())){
                        spinnerAttPuxada.setSelection(x);
                        break;
                    }
                }

                for (int x = 0; x < listaPratileira.size(); x++){
                    if(listaPratileira.get(x).equalsIgnoreCase(produtoRecuperado.getPratileira())){
                        spinnerAttPratileira.setSelection(x);
                        break;
                    }
                }
                imageStringAttCapturada = produtoRecuperado.getFoto();
                textCodigo.setText(produtoRecuperado.getCodigo());
                byte[] fotoembyte;
                fotoembyte = Base64.decode(imageStringAttCapturada, Base64.DEFAULT);
                Bitmap imagemBitmap = BitmapFactory.decodeByteArray(fotoembyte, 0, fotoembyte.length);
                imageAttCapturada.setImageBitmap(imagemBitmap);
                exibirProgresso(false);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    public void exibirProgresso(boolean progress){
        progressBaratt.setVisibility(progress ? View.VISIBLE : View.GONE);
        if(progress == true) {
            btnAttProduto.setVisibility(View.INVISIBLE);
            btnVoltarAtt.setVisibility(View.INVISIBLE);
        }else{
            btnAttProduto.setVisibility(View.VISIBLE);
            btnVoltarAtt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.puxar_direita, R.anim.mover_esquerda);
    }
}