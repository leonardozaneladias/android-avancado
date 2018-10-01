package br.com.caelum.casadocodigo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.adapter.LivroAdapter;
import br.com.caelum.casadocodigo.modelo.Autor;
import br.com.caelum.casadocodigo.modelo.Livro;

public class ListaLivrosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_livros, container, false);

        List<Livro> livros = new ArrayList<>();
        for(int i = 1; i < 10; i++){
        
            Autor autor = new Autor();
            autor.setNome("Autor " + i);
            Livro livro = new Livro("Livro " + i, "Descrição " + i, Arrays.asList(autor));
            livros.add(livro);

        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_livros);
        recyclerView.setAdapter(new LivroAdapter(livros));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
