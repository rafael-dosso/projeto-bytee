package com.example.bytee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface Service {

    // usuarios
    @GET("/usuarios")
    Call<List<Usuario>> getUsuario();

    @GET("/usuarios/{id}")
    Call<Usuario> getUsuarioId(@Path("id") String id);

    @POST("/usuarios")
    Call<Usuario> incluirUsuario(@Body Usuario user);

    @GET("/login/{email}")
    Call<Usuario> login(@Path("email") String email);


    // receitas
    @GET("/receitas")
    Call<List<Receita>> getReceita();

    @GET("/usuarios/{id}/receitas")
    Call<List<Receita>> getReceitaId(@Path("id") String id);

    @POST("/usuarios/{id}/receitas")
    Call<Receita> incluirReceita(@Path("id") String id, @Body Receita rec);

    @GET("/categorias/{id}/receitas")
    Call<List<Receita>> getReceitaCategoria(@Path("id") String id);
}
