package com.example.foragentss.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foragentss.MainActivity;
import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.retailers.RetailersDashboard;
import com.example.foragentss.auth.retailers.fragments.DeviceConfigurationFragment;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.home.fragments.AgreementsFragment;
import com.example.foragentss.home.fragments.LoginFragment;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.example.foragentss.security.CardEncryptor;
import com.example.foragentss.security.KeyCastor;

import java.net.SocketTimeoutException;

import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ECard login");

        showHome();

    }

    public void showHome(){
        Fragment fragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_content_frame,fragment);
        transaction.commit();
    }

    public void showAddressFragment(int roleId,String password){
        Fragment fragment = new AgreementsFragment(roleId,"You are logged in successfully. Now please select a phone that you are going to work on it. This phone will help you to store your cards and to print them at any time without the need of internet connection.","registration",password);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_content_frame,fragment);
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
