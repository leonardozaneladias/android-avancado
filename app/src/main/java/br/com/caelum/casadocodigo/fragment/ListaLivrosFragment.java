package br.com.caelum.casadocodigo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.adapter.LivroAdapter;
import br.com.caelum.casadocodigo.modelo.Autor;
import br.com.caelum.casadocodigo.modelo.Livro;
import br.com.caelum.casadocodigo.server.WebClient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaLivrosFragment extends Fragment {

    public static final String LIVROS = "livros";

    private List<Livro> livros = new ArrayList<>();

    @BindView(R.id.lista_livros)
    RecyclerView recyclerView;
    private boolean carregando;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_livros, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setAdapter(new LivroAdapter(livros));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BaseAttacher attacher = Mugen.with(recyclerView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                new WebClient().getLivros(livros.size(),5 );
                carregando = true;

                Snackbar.make(recyclerView, "Carregando mais livros", Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public boolean isLoading() {
                return carregando;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        });

        attacher.setLoadMoreEnabled(true);
        attacher.start();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setTitle("Catalogo");

    }

    public void populaListaCom(List<Livro> livros){

        carregando = false;

        this.livros.addAll(livros);
        recyclerView.getAdapter().notifyDataSetChanged();

    }
}
