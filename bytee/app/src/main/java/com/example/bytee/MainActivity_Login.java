package com.example.bytee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_Login extends AppCompatActivity {

    Button btnCadastro, btnLogin;
    EditText edtEmail, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnCadastro = (Button)findViewById(R.id.btnCadastro);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Login.this, MainActivity_Cadastro.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Usuario user = new Usuario();
                user.setEmail(edtEmail.getText().toString());
                Call<Usuario> callUsuario = service.login(user.getEmail());
                callUsuario.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful()){
                            user.setSenha(edtSenha.getText().toString());

                            Usuario userRetorno = (Usuario)response.body();

                            if (user.getSenha().equals(userRetorno.getSenha())) {
                                Intent intent = new Intent(MainActivity_Login.this, MainActivity_Menu.class);
                                intent.putExtra("user", userRetorno);
                                finish();
                                startActivity(intent);
                            }
                            else {
                                edtSenha.setText("");
                                Toast.makeText(MainActivity_Login.this, "Senha incorreta", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            String errorMessage = response.errorBody().toString();
                            Toast.makeText(MainActivity_Login.this, errorMessage, Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity_Login.this, "Usuário não existente", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        String messageProblem = t.getMessage().toString();
                        Toast.makeText(MainActivity_Login.this, messageProblem, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity_Login.this, "Usuário não existente", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}