package br.com.caelum.leozd.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.caelum.leozd.R;
import br.com.caelum.leozd.delegate.LivrosDelegate;
import br.com.caelum.leozd.modelo.Livro;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        Picasso.get().load(livro.getUrlFoto()).into(viewHolder.foto);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView (R.id.item_livro_nome) TextView nome;
        @BindView (R.id.item_livro_foto) ImageView foto;
        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_livro)
        public void clickItem(){

            Livro livro = livros.get(getAdapterPosition());
            LivrosDelegate delegate = (LivrosDelegate) itemView.getContext();
            delegate.lidaComLivrosSelecionado(livro);

        }
    }
}
