package br.com.caelum.leozd.dagger;

import javax.inject.Singleton;

import br.com.caelum.leozd.modelo.Carrinho;
import dagger.Module;
import dagger.Provides;

@Module
public class CasaDoCodigoModule {

    @Provides
    @Singleton
    public Carrinho getCarrinho() {
        return  new Carrinho();
    }
}
