package com.samirmaciel.estoquesdp.ui.estoque;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samirmaciel.estoquesdp.R;
import com.samirmaciel.estoquesdp.model.RecycleAdapter;
import com.samirmaciel.estoquesdp.model.RecycleViewClick;
import com.samirmaciel.estoquesdp.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class EstoqueFragment extends Fragment implements RecycleViewClick {

    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private TextView textQuantidadeDeProdutos;
    private RecyclerView estoqueRecycleView;
    private List<Produto> produtosFirebase = new ArrayList<>();
    private RecycleAdapter adapter;
    private Spinner spinnerCategoria;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estoque, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        estoqueRecycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        eventoDataBase();
        adapter = new RecycleAdapter(getContext(), produtosFirebase, this);
        estoqueRecycleView.setAdapter(adapter);
        estoqueRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        textQuantidadeDeProdutos = (TextView) view.findViewById(R.id.textQuantidadeDeProdutos);
        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategoria);

        List<String> listaCategorias = new ArrayList<>();
        listaCategorias.add("TODAS");
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

        ArrayAdapter adapterCategoria = new ArrayAdapter(getContext(), R.layout.spinner_item, listaCategorias);
        adapterCategoria.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerCategoria.setAdapter(adapterCategoria);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelecionado = (String) parent.getSelectedItem();
                if(itemSelecionado.equalsIgnoreCase("TODAS")){
                    eventoDataBase();
                }else{
                    eventoDataBaseFiltro(itemSelecionado);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void eventoDataBase() {
        exibirProgresso(true);
        databaseReference.child("Produtos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produtosFirebase.clear();
                for(DataSnapshot objeto: snapshot.getChildren()){
                    Produto p = objeto.getValue(Produto.class);
                    produtosFirebase.add(p);
                    adapter.notifyDataSetChanged();
                }
                exibirProgresso(false);
                textQuantidadeDeProdutos.setText(String.valueOf(produtosFirebase.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void eventoDataBaseFiltro(String categoria) {
        exibirProgresso(true);
        databaseReference.child("Produtos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produtosFirebase.clear();
                for(DataSnapshot objeto: snapshot.getChildren()){
                    Produto p = objeto.getValue(Produto.class);
                    if(categoria.equalsIgnoreCase(p.getCategoria())) {
                        produtosFirebase.add(p);
                        adapter.notifyDataSetChanged();
                    }
                }
                adapter.notifyDataSetChanged();
                exibirProgresso(false);
                textQuantidadeDeProdutos.setText(String.valueOf(produtosFirebase.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public void onItemClick(int position) {
        Produto p = produtosFirebase.get(position);
        mostrarImagem(p.getFoto());
    }


    private  void mostrarImagem(String foto){
        byte[] imagemembytes;
        imagemembytes = Base64.decode(foto, Base64.DEFAULT);
        Bitmap imagemdecodificada = BitmapFactory.decodeByteArray(imagemembytes, 0, imagemembytes.length);

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.mostrar_imagem, null);
        ImageView img = (ImageView) v.findViewById(R.id.fotoProduto);
        img.setImageBitmap(imagemdecodificada);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(v);
        dialog.show();
    }
    public void exibirProgresso(boolean progress){
        progressBar.setVisibility(progress ? View.VISIBLE : View.GONE);

    }

}