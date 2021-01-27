package com.example.foragentss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.foragentss.home.fragments.HomePageFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static {
        System.loadLibrary("keys");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showHome();

    }

    public void showHome(){
        Fragment fragment =new HomePageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_content_frame,fragment);
        transaction.commit();
    }

    public  native String getNativeKey();

    @Override
    public void onClick(View view) {

    }
}
