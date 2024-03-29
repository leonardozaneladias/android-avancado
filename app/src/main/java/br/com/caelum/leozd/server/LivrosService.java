package br.com.caelum.leozd.server;

import java.util.List;

import br.com.caelum.leozd.modelo.Livro;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LivrosService {

    @GET("listarLivros")
    Call<List<Livro>> listaLivros(
            @Query("indicePrimeiroLivro") int indicePrimeiroLivro,
            @Query("qtdLivros") int qtdLivros
    );



}
