package com.example.happypet.util;

import com.example.happypet.activity.AddAppointmentActivity;
import com.example.happypet.activity.DoctorHomeActivity;
import com.example.happypet.activity.DoctorProfileActivity;
import com.example.happypet.activity.DoctorRegisterActivity;
import com.example.happypet.activity.FacebookLoginActivity;
import com.example.happypet.activity.HomeActivity;
import com.example.happypet.activity.LoginActivity;
import com.example.happypet.activity.MapsActivity;
import com.example.happypet.activity.MyAppointmentsDoctorActivity;
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
    void inject(ApplicationImpl applicationImpl);
    void inject(MapsActivity mapsActivity);
    void inject(FacebookLoginActivity facebookLoginActivity);
    void inject(AddAppointmentActivity activity);
    void inject(SeeAppointmentActivity seeAppointmentActivity);
    void inject(DoctorProfileActivity doctorProfileActivity);
    void inject(DoctorRegisterActivity doctorRegisterActivity);
    void inject(LoginActivity loginActivity);
    void inject(DoctorHomeActivity doctorHomeActivity);
    void inject(HomeActivity homeActivity);
    void inject(MyAppointmentsDoctorActivity myAppointmentsDoctorActivity);
}

