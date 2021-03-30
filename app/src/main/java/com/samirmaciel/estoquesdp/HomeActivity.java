package com.samirmaciel.estoquesdp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samirmaciel.estoquesdp.ui.cadastrar.CadastroFragment;
import com.samirmaciel.estoquesdp.ui.estoque.EstoqueFragment;
import com.samirmaciel.estoquesdp.ui.inicio.InicioFragment;

public class HomeActivity extends AppCompatActivity {

    private int fragment_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.nav_bottomview);
        Menu menu = navView.getMenu();

        MenuItem buscar = (MenuItem) menu.findItem(R.id.navigation_buscar);
        MenuItem estoque = (MenuItem) menu.findItem(R.id.navigation_estoque);
        MenuItem cadastrar = (MenuItem) menu.findItem(R.id.navigation_cadastrar);

        buscar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(fragment_index != 0){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.puxar_esquerda, R.anim.mover_direita)
                            .replace(R.id.container_frame, new InicioFragment())
                            .commit();
                    fragment_index = 0;
                }
                return false;
            }
        });

        estoque.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(fragment_index != 1){
                    if(fragment_index == 0) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.puxar_direita, R.anim.mover_esquerda)
                                .replace(R.id.container_frame, new EstoqueFragment())
                                .commit();
                        fragment_index = 1;
                    }else if(fragment_index == 2){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.puxar_esquerda, R.anim.mover_direita)
                                .replace(R.id.container_frame, new EstoqueFragment())
                                .commit();
                        fragment_index = 1;
                    }
                }
                return false;
            }
        });

        cadastrar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(fragment_index != 2){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.puxar_direita, R.anim.mover_esquerda)
                            .replace(R.id.container_frame, new CadastroFragment())
                            .commit();
                    fragment_index = 2;
                }
                return false;
            }
        });


        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_frame, new InicioFragment())
                    .commit();
        }
    }
}