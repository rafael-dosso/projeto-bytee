package com.example.bytee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_Menu extends AppCompatActivity {

    private GridView receitaGridView;
    private GridViewViewAdapter adapter;
    private ImageView imgvReceitas, imgvCadastro, imgvUser;

    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        imgvReceitas = (ImageView)findViewById(R.id.imgvReceitas);
        imgvCadastro = (ImageView)findViewById(R.id.imgvCadastro);
        imgvUser = (ImageView)findViewById(R.id.imgvUser);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                user = null;
            } else {
                user = (Usuario) extras.get("user");
            }
        } else {
            user = (Usuario) savedInstanceState.getSerializable("user");
        }

        imgvReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Menu.this, MainActivity_Menu.class);
                intent.putExtra("user", user);
                finish();
                startActivity(intent);
            }
        });

        imgvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Menu.this, MainActivity_CadastroReceita.class);
                intent.putExtra("user", user);
                finish();
                startActivity(intent);
            }
        });

        imgvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Menu.this, MainActivity_Usuario.class);
                intent.putExtra("user", user);
                finish();
                startActivity(intent);
            }
        });

        //Download JSON via Retrofit
        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
        //Pegar a rota do Json
        // LEMBRETE: MUDAR O METODO GET PARA ESPECIFICO DE USUARIO
        Call<List<Receita>> callReceita = service.getReceita();
        callReceita.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Call<List<Receita>> call, Response<List<Receita>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity_Menu.this, "deu certo", Toast.LENGTH_LONG).show();
                    populateGridView(response.body());
                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(MainActivity_Menu.this, errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity_Menu.this, "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Receita>> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(MainActivity_Menu.this, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity_Menu.this, "entrou no else do Failure", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void populateGridView(List<Receita> listaReceita){
        receitaGridView = (GridView) findViewById(R.id.receitaGridView);
        adapter = new GridViewViewAdapter(this,listaReceita);
        receitaGridView.setAdapter(adapter);
    }

}