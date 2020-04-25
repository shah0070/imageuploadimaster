package com.devtides.androidarchitectures.di;


import com.devtides.androidarchitectures.viewmodel.Imageview_viewmodel;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules ={ApiModule.class})
public interface ApiComponent {
    void inject(Imageview_viewmodel imageview_viewmodel);
}
