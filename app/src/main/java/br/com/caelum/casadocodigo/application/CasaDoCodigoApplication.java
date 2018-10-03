package br.com.caelum.casadocodigo.application;

import android.app.Application;

import br.com.caelum.casadocodigo.dagger.CasaDoCodigoComponent;
import br.com.caelum.casadocodigo.dagger.DaggerCasaDoCodigoComponent;

public class CasaDoCodigoApplication extends Application {

    private CasaDoCodigoComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerCasaDoCodigoComponent.builder().build();
    }

    public CasaDoCodigoComponent getComponent() {
        return component;
    }
}
