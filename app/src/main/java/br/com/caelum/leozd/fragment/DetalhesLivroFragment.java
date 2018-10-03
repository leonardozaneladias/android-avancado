package br.com.caelum.leozd.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.caelum.leozd.R;
import br.com.caelum.leozd.application.CasaDoCodigoApplication;
import br.com.caelum.leozd.modelo.Autor;
import br.com.caelum.leozd.modelo.Carrinho;
import br.com.caelum.leozd.modelo.Item;
import br.com.caelum.leozd.modelo.Livro;
import br.com.caelum.leozd.modelo.TipoDeCompra;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalhesLivroFragment extends Fragment {

    @BindView(R.id.detalhes_livro_foto)
    ImageView foto;

    @BindView(R.id.detalhes_livro_nome)
    TextView nome;

    @BindView(R.id.detalhes_livro_autores)
    TextView autor;

    @BindView(R.id.detalhes_livro_descricao)
    TextView descricao;

    @BindView(R.id.detalhes_livro_num_paginas)
    TextView numPaginas;

    @BindView(R.id.detalhes_livro_isbn)
    TextView isbn;

    @BindView(R.id.detalhes_livro_data_publicacao)
    TextView dataPublicacao;

    @BindView(R.id.detalhes_livro_comprar_fisico)
    TextView botaoComprarFisico;

    @BindView(R.id.detalhes_livro_comprar_ebook)
    TextView botaoComprarEbook;

    @BindView(R.id.detalhes_livro_comprar_ambos)
    TextView botaoComprarAmbos;

    @Inject
    Carrinho carrinho;

    private Livro livro;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_livro, container, false);
        ButterKnife.bind(this, view);
        populaCamposCom(livro);

        CasaDoCodigoApplication app = (CasaDoCodigoApplication) getActivity().getApplication();
        app.getComponent().inject(this);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        livro = (Livro) arguments.getSerializable("livro");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(livro.getNome());
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick({R.id.detalhes_livro_comprar_fisico, R.id.detalhes_livro_comprar_ebook, R.id.detalhes_livro_comprar_ambos})
    public void comprar(View view) {

        switch (view.getId()){
            case R.id.detalhes_livro_comprar_fisico:
                Toast.makeText(getActivity(), "Livro Adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
                carrinho.adiciona(new Item(livro, TipoDeCompra.FISICO));
                break;

            case R.id.detalhes_livro_comprar_ebook:
                Toast.makeText(getActivity(), "Livro Adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
                carrinho.adiciona(new Item(livro, TipoDeCompra.VIRTUAL));
                break;

            case R.id.detalhes_livro_comprar_ambos:
                Toast.makeText(getActivity(), "Livro Adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
                carrinho.adiciona(new Item(livro, TipoDeCompra.JUNTOS));
                break;
        }
    }

    private void populaCamposCom(Livro livro) {
        nome.setText(livro.getNome());

        String listaDeAutores = "";
        for (Autor autor : livro.getAutores()){
            if(!listaDeAutores.isEmpty()){
                listaDeAutores += ", ";
            }
            listaDeAutores += autor.getNome();
        }

        autor.setText(listaDeAutores);
        descricao.setText(livro.getDescricao());
        numPaginas.setText(String.valueOf(livro.getNumPaginas()));
        isbn.setText(livro.getISBN());
        dataPublicacao.setText(livro.getDataPublicacao());

        String textoComprarFisico = String.format("Comprar livro Físico - R$ %.2f", livro.getValorFisico());
        botaoComprarFisico.setText(textoComprarFisico);

        String textoComprarEbook = String.format("Comprar Ebook - R$ %.2f", livro.getValorVirtual());
        botaoComprarEbook.setText(textoComprarEbook);

        String textoComprarAmbos = String.format("Comprar ambos - R$ %.2f", livro.getValorDoisJuntos());
        botaoComprarAmbos.setText(textoComprarAmbos);

        Picasso.get().load(livro.getUrlFoto()).into(foto);

    }
}
