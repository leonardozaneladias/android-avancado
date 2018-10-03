package br.com.caelum.leozd.event;

import java.util.List;

import br.com.caelum.leozd.modelo.Livro;

public class LivroEvent {

    private final List<Livro> livros;

    public LivroEvent(List<Livro> livros){

        this.livros = livros;
    }

    public List<Livro> getLivros(){
        return livros;
    }
}
