package com.example.bytee;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requisicao
{
    private String url;

    public Requisicao(String url) {
        this.url = url;
    }

    public Usuario getUsuario(String endpoint) throws Exception {
        if (endpoint.charAt(0) != '/') {
            throw new Exception("O endpoint precisa ter uma / no comeco.");
        }

        try {
            InputStream response = this.getInpuStream(url + endpoint);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response, Usuario.class);
        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Usuario postUsuario(String endpoint, Usuario usuar) throws Exception {
        if (endpoint.charAt(0) != '/') {
            throw new Exception("O endpoint precisa ter uma / no comeco.");
        }

        try {
            OutputStream response = this.postOutputStream(url + endpoint, usuar);

            return usuar;
        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Receita getReceita(String endpoint) throws Exception {
        if (endpoint.charAt(0) != '/') {
            throw new Exception("O endpoint precisa ter uma / no comeco.");
        }

        try {
            InputStream response = this.getInpuStream(url + endpoint);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response, Receita.class);
        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private InputStream getInpuStream(String qualUrl) {
        try {
            URL endereco = new URL(qualUrl);
            HttpURLConnection connection = (HttpURLConnection) endereco.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            return (InputStream) connection.getInputStream();
        } catch (Exception e) {
            System.out.println("Erro na conexao");
        }
        return null;
    }

    private OutputStream postOutputStream(String qualUrl, Usuario usuar) {
        try {
            URL endereco = new URL(qualUrl);
            HttpURLConnection connection = (HttpURLConnection) endereco.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");

            JSONObject user = new JSONObject();
            JSONObject auth = new JSONObject();
            JSONObject parent = new JSONObject();

            user.put("nome", usuar.getNome());
            user.put("email", usuar.getEmail());
            user.put("senha", usuar.getSenha());
            // createdAt
            // updatedAt
            parent.put("auth", auth);

            OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
            wr.write(parent.toString());
            wr.flush();
        } catch (Exception e) {
            System.out.println("Erro na conexao");
        }
        return null;
    }
}

