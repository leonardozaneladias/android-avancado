package br.com.caelum.casadocodigo.delegate;

import java.util.List;

import br.com.caelum.casadocodigo.modelo.Livro;

public interface LivrosDelegate {

    public void lidaComLivrosSelecionado(Livro livro);

    public void lidaComSucesso(List<Livro> body);

    public void lidaComErro(Throwable erro);
}
