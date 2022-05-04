package com.example.happypet.util;

import android.app.Application;

public class ApplicationImpl extends Application{

    private static ApplicationImpl app;
    ApplicationComponent applicationComponent;

    public static ApplicationImpl getApp(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}