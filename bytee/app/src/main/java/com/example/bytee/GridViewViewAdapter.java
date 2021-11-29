package com.example.bytee;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class GridViewViewAdapter extends BaseAdapter {

    private List<Receita> listaReceita;
    private Context context;

    public GridViewViewAdapter(Context context,List list) {
        this.listaReceita = list;
        this.context = context;
    }

    @Override
    public int getCount() { return listaReceita.size(); }

    @Override
    public Object getItem(int position) { return listaReceita.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_gridview, parent,false);
        }

        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvRendimento = view.findViewById(R.id.tvRendimento);
        TextView tvAutor = view.findViewById(R.id.tvAutor);
        ImageView receitaImageView = view.findViewById(R.id.receitaImageView);
        TextView tvIngredientes = view.findViewById(R.id.tvIngredientes);
        TextView tvPreparo = view.findViewById(R.id.tvPreparo);

        Receita receita = listaReceita.get(position);

        tvTitulo.setText(receita.getNome());
        tvRendimento.setText(String.valueOf(receita.getRendimento()) + "Pessoas");
        tvIngredientes.setText(receita.getIngredientes());
        tvPreparo.setText(receita.getPreparo());

        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Usuario> callUser = service.getUsuarioId(String.valueOf(receita.getUsuarioId()));
        callUser.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Usuario user = response.body();
                    tvAutor.setText(user.getNome());
                }else{
                    String errorMessage = response.errorBody().toString();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
            }
        });
        //tvAutor.setText(receita.get);

        if((receita.getImagem() != null) && (receita.getImagem().length()>0)){
            Picasso.get().load(receita.getImagem()).into(receitaImageView);
        }else{
            Toast.makeText(context, "Não carregou a imagem", Toast.LENGTH_LONG).show();
        }

        return view;
    }
}
