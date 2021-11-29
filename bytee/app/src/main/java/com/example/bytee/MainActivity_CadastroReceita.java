package com.example.bytee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_CadastroReceita extends AppCompatActivity {

    Button btnCadastrar;
    EditText edtCategoria, edtNome, edtIngredientes, edtPreparo, edtRendimento, edtImagem;
    private ImageView imgvReceitas, imgvCadastro, imgvUser;

    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        btnCadastrar = (Button)findViewById(R.id.btnCadastrarRec);

        edtCategoria = (EditText)findViewById(R.id.edtCategoria);
        edtNome = (EditText)findViewById(R.id.edtNomeReceita);
        edtIngredientes = (EditText)findViewById(R.id.edtIngredientes);
        edtPreparo = (EditText)findViewById(R.id.edtPreparo);
        edtRendimento = (EditText)findViewById(R.id.edtRendimento);
        edtImagem = (EditText)findViewById(R.id.edtImagemReceita);

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
                Intent intent = new Intent(MainActivity_CadastroReceita.this, MainActivity_Menu.class);
                intent.putExtra("user", user);
                finish();
                startActivity(intent);
            }
        });

        imgvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_CadastroReceita.this, MainActivity_CadastroReceita.class);
                intent.putExtra("user", user);
                finish();
                startActivity(intent);
            }
        });

        imgvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_CadastroReceita.this, MainActivity_Usuario.class);
                intent.putExtra("user", user);
                finish();
                startActivity(intent);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirReceita();
            }
        });
    }

    public void inserirReceita() {

        int idCategoria = Integer.parseInt(edtCategoria.getText().toString());
        String strNome = edtNome.getText().toString();
        String strIngredientes = edtIngredientes.getText().toString();
        String strPreparo = edtPreparo.getText().toString();
        int qtdRendimento = Integer.parseInt(edtRendimento.getText().toString());
        String strImagem = edtImagem.getText().toString();

        // inserir no BD
        Receita receita = new Receita();
        receita.setCategoriaId(idCategoria);
        receita.setNome(strNome);
        receita.setIngredientes(strIngredientes);
        receita.setPreparo(strPreparo);
        receita.setRendimento(qtdRendimento);
        receita.setImagem(strImagem);

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Receita> call = service.incluirReceita(String.valueOf(user.getId()), receita);

        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Call<Receita> call, Response<Receita> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(MainActivity_CadastroReceita.this, MainActivity_Usuario.class);
                    intent.putExtra("user", user);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Receita> call, Throwable t) {
                Toast.makeText(MainActivity_CadastroReceita.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}