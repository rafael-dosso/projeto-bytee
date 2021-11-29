package com.example.bytee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_Cadastro extends AppCompatActivity {

    Button btnLogin, btnCadastrar;
    EditText edtUsuario, edtEmail, edtSenha, edtConfSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button)findViewById(R.id.btnLog);
        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);

        edtUsuario = (EditText)findViewById(R.id.edtUsuario);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtConfSenha = (EditText)findViewById(R.id.edtConfSenha);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Cadastro.this, MainActivity_Login.class);
                startActivity(intent);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = edtEmail.getText().toString();

                Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Usuario user = new Usuario();
                user.setEmail(edtEmail.getText().toString());
                Call<Usuario> callUsuario = service.login(user.getEmail());
                callUsuario.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(MainActivity_Cadastro.this, "Email já foi utilizado!", Toast.LENGTH_LONG).show();
                            edtEmail.setText("");
                        } else {

                            inserirUsuario();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                    }
                });
            }
        });
    }

    public void inserirUsuario () {

        String strUser = edtUsuario.getText().toString();
        String strEmail = edtEmail.getText().toString();
        String strSenha = edtSenha.getText().toString();

        // inserir no BD
        Usuario user = new Usuario();
        user.setNome(strUser);
        user.setEmail(strEmail);
        user.setSenha(strSenha);

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Usuario> call = service.incluirUsuario(user);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()) {
                    // instância da classe Usuario recebe o id referente ao BD
                    Call<List<Usuario>> callUser = service.getUsuario();
                    callUser.enqueue(new Callback<List<Usuario>>() {
                        @Override
                        public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                            if(response.isSuccessful()){
                                getIdUsuario(response.body(), user);
                                //Toast.makeText(MainActivity3.this, user.toString(), Toast.LENGTH_LONG).show();
                            }else{
                                String errorMessage = response.errorBody().toString();
                                Toast.makeText(MainActivity_Cadastro.this, errorMessage, Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity_Cadastro.this, "entrou no else do response", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Usuario>> call, Throwable t) {
                            String messageProblem = t.getMessage().toString();
                            Toast.makeText(MainActivity_Cadastro.this, messageProblem, Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity_Cadastro.this, "entrou no else do Failure", Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(MainActivity_Cadastro.this, MainActivity_Menu.class);
                    intent.putExtra("user", user);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(MainActivity_Cadastro.this, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity_Cadastro.this, "entrou no else do Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getIdUsuario(List<Usuario> listaUsuario, Usuario user) {
        int tamanho = listaUsuario.size();
        user.setId(listaUsuario.get(tamanho-1).getId());
    }
}