package br.com.caelum.leozd.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import javax.inject.Inject;
import br.com.caelum.leozd.R;
import br.com.caelum.leozd.adapter.ItensAdapter;
import br.com.caelum.leozd.application.CasaDoCodigoApplication;
import br.com.caelum.leozd.modelo.Carrinho;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrinhoActivity extends AppCompatActivity {

    @BindView(R.id.lista_itens_carrinho)
    RecyclerView listaItens;

    @BindView(R.id.valor_carrinho)
    TextView valorTotal;

    @Inject
    Carrinho carrinho;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_carrinho);
        ButterKnife.bind(this);

        CasaDoCodigoApplication app = (CasaDoCodigoApplication) getApplication();
        app.getComponent().inject(this);
    }

    public void carregaLista(){
        listaItens.setAdapter(new ItensAdapter(carrinho.getItens(), this));
        listaItens.setLayoutManager(new LinearLayoutManager(this));

        valorTotal.setText(carrinho.getValor());
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }
}