package com.example.happypet.util;

import com.example.happypet.activity.RegisterActivity;
import com.example.happypet.data.repository.UserRepository;
import com.example.happypet.model.view_model.UserViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={ApplicationModule.class})
public interface ApplicationComponent{

    void inject(RegisterActivity registerActivity);
    void inject(UserViewModel userViewModel);
    void inject(UserRepository userRepository);
    void inject(MyApplication myApplication);
}

