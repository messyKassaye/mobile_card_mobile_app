package com.example.foragentss.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.example.foragentss.R;
import com.example.foragentss.auth.retailers.fragments.DeviceConfigurationFragment;
import com.example.foragentss.home.fragments.AgreementsFragment;
import com.example.foragentss.home.fragments.RegistrationFragment;


public class RegistrationActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Fragment fragment = new RegistrationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.registration_content_frame,fragment);
        transaction.commit();
    }

    public void showAddressFragment(int roleId,String password){
        Fragment fragment = new AgreementsFragment(roleId,"You are registered successfully. Now please select a phone that you are going to work on it. This phone will help you to store your cards and to print them at any time without the need of internet connection.","registration",password);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registration_content_frame,fragment);
        transaction.commit();
    }

    public void showDeviceConfig(){
        Fragment fragment = new DeviceConfigurationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registration_content_frame,fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
