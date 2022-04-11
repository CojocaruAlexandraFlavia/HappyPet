package com.example.happypet.util;

import com.example.happypet.activity.AddAppointmentActivity;
import com.example.happypet.activity.DoctorProfileActivity;
import com.example.happypet.activity.FacebookLoginActivity;
import com.example.happypet.activity.MapsActivity;
import com.example.happypet.activity.RegisterActivity;
import com.example.happypet.activity.SeeAppointmentActivity;
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
    void inject(MapsActivity mapsActivity);
    void inject(FacebookLoginActivity facebookLoginActivity);
    void inject(AddAppointmentActivity activity);
    void inject(SeeAppointmentActivity seeAppointmentActivity);
    void inject(DoctorProfileActivity doctorProfileActivity);
}

