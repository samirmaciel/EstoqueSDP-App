package com.samirmaciel.estoquesdp.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samirmaciel.estoquesdp.HomeActivity;
import com.samirmaciel.estoquesdp.R;
import com.samirmaciel.estoquesdp.ui.AtualizacaoProduto_Act;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private List<Produto> produtos;
    private Context context;
    private Activity activity;
    private DatabaseReference databaseReference;
    private RecycleViewClick recycleViewClick;

    public RecycleAdapter(Context ct, Activity activity, List lista, RecycleViewClick click) {
        recycleViewClick = click;
        produtos = lista;
        context = ct;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_produto_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        byte[] imagemembytes;
        imagemembytes = Base64.decode(produtos.get(position).getFoto(), Base64.DEFAULT);
        Bitmap imagemdecodificada = BitmapFactory.decodeByteArray(imagemembytes, 0, imagemembytes.length);
        holder.imageProduto.setImageBitmap(imagemdecodificada);
        holder.textCategoria.setText(produtos.get(position).getCategoria());
        holder.textPiso.setText(produtos.get(position).getPiso());
        holder.textPuxada.setText(produtos.get(position).getPuxada());
        holder.textPratileira.setText(produtos.get(position).getPratileira());
        holder.textCodigo.setText(produtos.get(position).getCodigo());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView textPiso, textPuxada, textPratileira, textCategoria, textCodigo;
        ImageView imageProduto;
        CardView cardviewProduto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardviewProduto = itemView.findViewById(R.id.cardviewProduto);
            textPiso = itemView.findViewById(R.id.textPisoCard);
            textPuxada = itemView.findViewById(R.id.textPuxada);
            textPratileira = itemView.findViewById(R.id.textPratileira);
            textCategoria = itemView.findViewById(R.id.textCategoria);
            textCodigo = itemView.findViewById(R.id.textCodigo);
            imageProduto = itemView.findViewById(R.id.imageProdutoCard);
            cardviewProduto.setOnCreateContextMenuListener(this);

            cardviewProduto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycleViewClick.onItemClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem atualizar = menu.add("Atualizar");
            MenuItem deletar = menu.add("Excluir");

            atualizar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(context, AtualizacaoProduto_Act.class);
                    intent.putExtra("codigo", produtos.get(getAdapterPosition()).getCodigo());
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.puxar_direita, R.anim.mover_esquerda);
                    context.startActivity(intent);
                    return false;
                }
            });

            deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Produto produto = produtos.get(getAdapterPosition());

                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Atenção")
                            .setMessage("Deseja excluir o produto?")
                            .setNegativeButton("NÃO", null)
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseReference.child("Produtos").child(produto.getCodigo()).removeValue();
                                    produtos.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            }).create();
                    dialog.show();

                    return false;
                }
            });
        }
    }

}
