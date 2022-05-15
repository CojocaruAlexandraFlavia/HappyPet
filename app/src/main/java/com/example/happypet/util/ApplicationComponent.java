package com.example.happypet.util;

import com.example.happypet.activity.AddAppointmentActivity;
import com.example.happypet.activity.ClientProfileActivity;
import com.example.happypet.activity.DoctorHomeActivity;
import com.example.happypet.activity.DoctorProfileActivity;
import com.example.happypet.activity.DoctorRegisterActivity;
import com.example.happypet.activity.DrawerBaseDoctorActivity;
import com.example.happypet.activity.FacebookLoginActivity;
import com.example.happypet.activity.HomeActivity;
import com.example.happypet.activity.LoginActivity;
import com.example.happypet.activity.MapsActivity;
import com.example.happypet.activity.MyAppointmentsActivity;
import com.example.happypet.activity.MyAppointmentsDoctorActivity;
import com.example.happypet.activity.RegisterActivity;
import com.example.happypet.activity.SeeAppointmentActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(RegisterActivity registerActivity);
    void inject(ApplicationImpl applicationImpl);
    void inject(MapsActivity mapsActivity);
    void inject(FacebookLoginActivity facebookLoginActivity);
    void inject(AddAppointmentActivity activity);
    void inject(SeeAppointmentActivity seeAppointmentActivity);
    void inject(DoctorProfileActivity doctorProfileActivity);
    void inject(HomeActivity homeActivity);
    void inject(ClientProfileActivity clientProfileActivity);

    void inject(DoctorHomeActivity doctorHomeActivity);

    void inject(DoctorRegisterActivity doctorRegisterActivity);

    void inject(LoginActivity loginActivity);

    void inject(MyAppointmentsActivity myAppointmentsActivity);

    void inject(MyAppointmentsDoctorActivity myAppointmentsDoctorActivity);
    void inject(DrawerBaseDoctorActivity myDrawerBaseDoctorActivity);

}
