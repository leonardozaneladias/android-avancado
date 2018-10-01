package br.com.caelum.casadocodigo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.modelo.Livro;

public class LivroAdapter extends RecyclerView.Adapter {
    private List<Livro> livros;

    public LivroAdapter(List<Livro> livros) {
        this.livros = livros;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        int tipoDeLayout = R.layout.item_livro_par;
        if(viewType % 2 != 0){

            tipoDeLayout = R.layout.item_livro_impar;
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(tipoDeLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Livro livro = livros.get(position);
        viewHolder.nome.setText(livro.getNome());
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView foto;
        public ViewHolder(View view) {
            super(view);

            nome = (TextView) view.findViewById(R.id.item_livro_nome);
            foto = (ImageView) view.findViewById(R.id.item_livro_foto);
        }
    }
}
