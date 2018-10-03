package br.com.caelum.leozd.fragment;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.leozd.R;
import br.com.caelum.leozd.adapter.LivroAdapter;
import br.com.caelum.leozd.adapter.LivroInvertidoAdapter;
import br.com.caelum.leozd.modelo.Livro;
import br.com.caelum.leozd.server.WebClient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaLivrosFragment extends Fragment {

    public static final String LIVROS = "livros";

    private List<Livro> livros = new ArrayList<>();

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @BindView(R.id.lista_livros)
    RecyclerView recyclerView;
    private boolean carregando;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config);

        mFirebaseRemoteConfig.fetch(15).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mFirebaseRemoteConfig.activateFetched();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_livros, container, false);

        ButterKnife.bind(this, view);



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

        if(mFirebaseRemoteConfig.getBoolean("idioma")){
            recyclerView.setAdapter(new LivroInvertidoAdapter(livros));
        }else {
            recyclerView.setAdapter(new LivroAdapter(livros));
        }

    }

    public void populaListaCom(List<Livro> livros){

        carregando = false;

        this.livros.addAll(livros);
        recyclerView.getAdapter().notifyDataSetChanged();

    }
}
