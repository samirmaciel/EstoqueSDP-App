package com.samirmaciel.estoquesdp.ui.inicio;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.samirmaciel.estoquesdp.DAO.HistoricoDAO;
import com.samirmaciel.estoquesdp.R;
import com.samirmaciel.estoquesdp.model.RecycleAdapter;
import com.samirmaciel.estoquesdp.model.RecycleViewClick;
import com.samirmaciel.estoquesdp.model.Produto;
import com.samirmaciel.estoquesdp.model.ToastPersonalizado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InicioFragment extends Fragment implements RecycleViewClick {

    private ProgressBar progressBusca;
    private DatabaseReference databaseReference;

    private Produto produtoBuscado;
    private InicioViewModel inicioViewModel;
    private RecyclerView recyclerViewHistorico;
    private List<Produto> listaHistorico = new ArrayList<>();
    private List<Produto> listaBack = new ArrayList<>();
    private HistoricoDAO hdao;
    private RecycleAdapter adapter;

    private List<Produto> listaPreCarregada = new ArrayList<>();

    private EditText inputCodigo;
    private ImageView btnInfo;
    private Button btnBuscar;
    private Button btnLimparHistorico;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        inicioViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnInfo = (ImageView) view.findViewById(R.id.btnInfo);
        progressBusca = (ProgressBar) view.findViewById(R.id.progressBuscarProduto);
        btnLimparHistorico = (Button) view.findViewById(R.id.btnLimpar);
        inputCodigo = (EditText) view.findViewById(R.id.inputCodigo);
        hdao = new HistoricoDAO(getContext());
        inicarListaHistorico();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
        recyclerViewHistorico = (RecyclerView) view.findViewById(R.id.recyclerviewHistorico);
        adapter = new RecycleAdapter(getContext(), getActivity(), listaHistorico, this);
        recyclerViewHistorico.setAdapter(adapter);
        recyclerViewHistorico.setLayoutManager(new LinearLayoutManager(getContext()));
        botaoLimpar();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputCodigo.getText().toString().equalsIgnoreCase("") && inputCodigo.getText().toString().length() == 5) {
                    if(checarCodigoNalista(inputCodigo.getText().toString())) {
                        procurarProdutonoFirebase2(inputCodigo.getText().toString());
                    }else{
                        ToastPersonalizado.showToast(2, "Produto está no histórico!", getActivity(), getContext());
                    }
                }else{
                    ToastPersonalizado.showToast(2, "Digite o código do produto!", getActivity(), getContext());
                }
            }
        });

        btnLimparHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaHistorico.clear();
                listaBack.clear();
                hdao.limparHistorico();
                botaoLimpar();
                adapter.notifyDataSetChanged();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("")
                        .setMessage("Aplicativo para auxiliar na localização e indentificação, dos produtos relacionados a loja Sonho dos pés Angra." + "\n\nDesenvolvido por @SamirMaciel.   "+
                                "Android Studio" +
                                "\nJava" +
                                "\n\nBeta 1.6.4v")
                        .setPositiveButton("Fechar", null).create();
                dialog.show();

            }
        });
    }

    private void procurarProdutoNoFireBase(String codigo){
        exibirProgresso(true, false);
        Query query = databaseReference.child("Produtos").orderByChild("codigo").equalTo(codigo);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Produto p = null;
                    for(DataSnapshot objeto: snapshot.getChildren()){
                        p = objeto.getValue(Produto.class);
                    }
                    adicionarProdutoNoHistorico(p);
                    adapter.notifyDataSetChanged();
                    ToastPersonalizado.showToast(1, "PRODUTO ENCONTRADO!", getActivity(), getContext());
                    exibirProgresso(false, true);
                    inputCodigo.setText("");

                }else{
                    ToastPersonalizado.showToast(3, "PRODUTO NÃO ENCONTRADO!", getActivity(), getContext());
                    exibirProgresso(false, true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void procurarProdutonoFirebase2(String codigo) {
        exibirProgresso(true, false);
        databaseReference.child("Produtos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objeto: snapshot.getChildren()){
                    Produto p = objeto.getValue(Produto.class);
                    if(codigo.equalsIgnoreCase(p.getCodigo())){
                        adicionarProdutoNoHistorico(p);
                        adapter.notifyDataSetChanged();
                        ToastPersonalizado.showToast(1, "PRODUTO ENCONTRADO!", getActivity(), getContext());
                        exibirProgresso(false, true);
                        inputCodigo.setText("");
                        return;
                    }
                }
                ToastPersonalizado.showToast(3, "PRODUTO NÃO ENCONTRADO!", getActivity(), getContext());
                exibirProgresso(false, true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private boolean checarCodigoNalista(String codigo){
        for (Produto p: listaHistorico){
            if(p.getCodigo().equalsIgnoreCase(codigo)){
                return false;
            }
        }

        return true;
    }
    private void inicarListaHistorico(){
        listaBack.clear();
        listaBack = hdao.obterTodos();
        listaHistorico.clear();
        listaHistorico.addAll(listaBack);
        Collections.reverse(listaHistorico);
    }

    public void adicionarProdutoNoHistorico(Produto p){
        hdao.inserirProduto(p);
        listaBack.clear();
        listaBack = hdao.obterTodos();
        listaHistorico.clear();
        listaHistorico.addAll(listaBack);
        botaoLimpar();
        inicarListaHistorico();
        if(listaHistorico.size() > 5) {
            Produto produtoE = listaHistorico.get(listaHistorico.size() - 1);
            hdao.excluirProduto(produtoE.getCodigo());
            listaHistorico.remove(listaHistorico.size() - 1);
        }
    }


    private void botaoLimpar(){
        if(listaHistorico.size() > 0){
            btnLimparHistorico.setVisibility(View.VISIBLE);
        }else{
            btnLimparHistorico.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemClick(int position) {
        Produto p = listaHistorico.get(position);
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


    public void exibirProgresso(boolean progress, boolean btn){
        progressBusca.setVisibility(progress ? View.VISIBLE : View.GONE);
        btnBuscar.setEnabled(btn);
    }

}