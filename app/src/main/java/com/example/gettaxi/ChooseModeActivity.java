package com.example.gettaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mod);
    }

    public void goToPassengerSingIn(View view) {
        startActivity(new Intent(ChooseModeActivity.this,
                                    PassengerSingInActivity.class));
    }

    public void goToTaxiDriverSingIn(View view) {
        startActivity(new Intent(ChooseModeActivity.this,
                                    TaxiDriverSingInActivity.class));
    }
}