package com.samirmaciel.estoquesdp.ui.cadastrar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.samirmaciel.estoquesdp.DAO.ProdutoDAO;
import com.samirmaciel.estoquesdp.R;
import com.samirmaciel.estoquesdp.model.Produto;
import com.samirmaciel.estoquesdp.model.ToastPersonalizado;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CadastroFragment extends Fragment {

    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private EditText inputCodigo;
    private Spinner spinnerCategoria;
    private Spinner spinnerPiso;
    private Spinner spinnerPuxada;
    private Spinner spinnerPratileira;
    private Button btnCapturarFoto;
    private Button btnSalvarProduto;
    private boolean checkCodigoDoFirebase = true;
    private String categoria, puxada, piso, pratileira;


    private ImageView imageviewCapturar;

    private String fotoCapturada;

    private CadastroViewModel cadastroViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cadastroViewModel =
                new ViewModelProvider(this).get(CadastroViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastrar, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        imageviewCapturar = (ImageView) view.findViewById(R.id.imageviewCapturada);
        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategoria);
        spinnerPiso = (Spinner) view.findViewById(R.id.spinnerPiso);
        spinnerPuxada = (Spinner) view.findViewById(R.id.spinnerPuxada);
        spinnerPratileira = (Spinner) view.findViewById(R.id.spinnerPratileira);
        btnSalvarProduto = (Button) view.findViewById(R.id.btnSalvarProduto);
        inputCodigo = (EditText) view.findViewById(R.id.inputCodigoCadastro);

        List<String> listaCategorias = new ArrayList<>();
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


        List<String> listaPuxada = new ArrayList<>();
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

        List<String> listaPiso = new ArrayList<>();
        listaPiso.add("PISO");
        listaPiso.add("1");
        listaPiso.add("2");
        listaPiso.add("3");
        listaPiso.add("COZINHA");

        List<String> listaPratileira = new ArrayList<>();
        listaPratileira.add("PRATILEIRA");
        listaPratileira.add("1");
        listaPratileira.add("2");
        listaPratileira.add("3");
        listaPratileira.add("4");
        listaPratileira.add("5");
        listaPratileira.add("6");
        listaPratileira.add("7");
        listaPratileira.add("--");



        ArrayAdapter adapterCategoria = new ArrayAdapter(getContext(), R.layout.spinner_item, listaCategorias);
        adapterCategoria.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerCategoria.setAdapter(adapterCategoria);

        ArrayAdapter adapterPuxada = new ArrayAdapter(getContext(), R.layout.spinner_item, listaPuxada);
        adapterPuxada.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerPuxada.setAdapter(adapterPuxada);

        ArrayAdapter adapterPiso = new ArrayAdapter(getContext(), R.layout.spinner_item, listaPiso);
        adapterPiso.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerPiso.setAdapter(adapterPiso);

        ArrayAdapter adapterPratileira = new ArrayAdapter(getContext(), R.layout.spinner_item, listaPratileira);
        adapterPratileira.setDropDownViewResource(R.layout.spinner_dropdown_itemp);
        spinnerPratileira.setAdapter(adapterPratileira);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoria = (String) parent.getSelectedItem();
            }
        });

        spinnerPuxada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                puxada = (String) parent.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                puxada = (String) parent.getSelectedItem();
            }
        });

        spinnerPiso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                piso = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                piso = (String) parent.getSelectedItem();
            }
        });

        spinnerPratileira.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pratileira = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pratileira = (String) parent.getSelectedItem();
            }
        });

        imageviewCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarImagem();
            }
        });

        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checarCampos()){
                    salvarProdutoNoFireBase(inputCodigo.getText().toString());
                }else{
                    ToastPersonalizado.showToast(3, "Preencha todos os campos!", getActivity(), getContext());
                }
            }
        });
    }

    public void capturarImagem(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }


    public void salvarProdutoNoFireBase(String codigo){
        exibirProgresso(true, false);
        Query query = databaseReference.child("Produtos").orderByChild("codigo").equalTo(codigo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    inputCodigo.setText("");
                    ToastPersonalizado.showToast(3, "PRODUTO JÁ CADASTRADO!", getActivity(), getContext());
                }else{
                    Produto produtoView = obterUsuarioView();
                    databaseReference.child("Produtos").child(produtoView.getCodigo()).setValue(produtoView);
                    limparCampos();
                    ToastPersonalizado.showToast(1, "PRODUTO CADASTRADO!", getActivity(), getContext());
                }
                exibirProgresso(false, true);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Produto obterUsuarioView(){
        String uID = UUID.randomUUID().toString();
        Produto produto = new Produto();
        produto.setUId(uID);
        produto.setFoto(fotoCapturada);
        produto.setCategoria(categoria);
        produto.setCodigo(inputCodigo.getText().toString());
        produto.setPiso(piso);
        produto.setPuxada(puxada);
        produto.setPratileira(pratileira);
        return produto;
    }

    public boolean checarCampos(){
        if(!categoria.equalsIgnoreCase("categoria")){
            if(!piso.equalsIgnoreCase("piso")){
                if(!puxada.equalsIgnoreCase("puxada")){
                    if(!pratileira.equalsIgnoreCase("pratileira")){
                        if(inputCodigo.getText().toString().length() == 5){
                            if (fotoCapturada == null){
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
                imageviewCapturar.setImageBitmap(imageCamera);

                byte[] logoBytes;
                ByteArrayOutputStream streamImagem = new ByteArrayOutputStream();
                imageCamera.compress(Bitmap.CompressFormat.PNG, 100, streamImagem);
                logoBytes = streamImagem.toByteArray();
                fotoCapturada = Base64.encodeToString(logoBytes, Base64.DEFAULT);
                
            }catch (Exception e){
                
            }
        }
    }

    private void limparCampos(){
        imageviewCapturar.setImageResource(R.drawable.icon_camera);
        inputCodigo.setText("");
        spinnerCategoria.setSelection(0);
        spinnerPiso.setSelection(0);
        spinnerPuxada.setSelection(0);
        spinnerPratileira.setSelection(0);
    }

    private void exibirProgresso(boolean progress, boolean btn){
        progressBar.setVisibility(progress ? View.VISIBLE : View.INVISIBLE);
        btnSalvarProduto.setEnabled(btn);
    }
}