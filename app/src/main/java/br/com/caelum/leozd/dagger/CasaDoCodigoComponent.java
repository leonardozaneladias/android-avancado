package br.com.caelum.leozd.dagger;

import javax.inject.Singleton;

import br.com.caelum.leozd.activity.CarrinhoActivity;
import br.com.caelum.leozd.fragment.DetalhesLivroFragment;
import dagger.Component;

@Singleton
@Component(modules = CasaDoCodigoModule.class)
public interface CasaDoCodigoComponent {

    void inject(CarrinhoActivity activity);
    void inject(DetalhesLivroFragment fragment);


}
