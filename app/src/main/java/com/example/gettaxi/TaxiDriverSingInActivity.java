package com.example.gettaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class TaxiDriverSingInActivity extends AppCompatActivity {

    private static final int NAME_LENGTH = 20;
    private static final int PASSWORD_LENGTH = 5;


    private TextInputLayout emailTIL, nameTIL, passwordTIL, confirmPasswordTIL;

    private Button singUpButton;

    private TextView tapToSingInTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_driver_sing_up);

        emailTIL = findViewById(R.id.email_input_layout);
        nameTIL = findViewById(R.id.name_input_layout);
        passwordTIL = findViewById(R.id.password_input_layout);
        confirmPasswordTIL = findViewById(R.id.confirm_password_input_layout);

        singUpButton = findViewById(R.id.taxi_driver_sing_up_button);

        tapToSingInTV = findViewById(R.id.tap_to_sing_in_text_view);


    }

    public void enterAsTaxiDriver(View view) {

        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
        }
        Toast.makeText(this, "Sing Up was successful", Toast.LENGTH_SHORT)
                .show();

    }

    public void tapToSingIn(View view) {
    }

    private boolean validateEmail() {
        String emailInput = emailTIL.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            emailTIL.setError("Please, input your email");
            return false;
        } else {
            emailTIL.setError("");
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = nameTIL.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()) {
            nameTIL.setError("Please, input your name");
            return false;
        } else if (nameInput.length() > NAME_LENGTH) {
            nameTIL.setError("Name length must be less than " + NAME_LENGTH);
            return false;
        } else {
            nameTIL.setError("");
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = passwordTIL.getEditText().getText().toString().trim();
        String confirmPasswordInput =
                confirmPasswordTIL.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordTIL.setError("Please, input password");
            return false;
        } else if (passwordInput.length() < PASSWORD_LENGTH) {
            passwordTIL.setError("Password length must be more than " + PASSWORD_LENGTH);
            return false;
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            confirmPasswordTIL.setError("Passwords must match");
            return false;
        } else {
            confirmPasswordTIL.setError("");
            return true;
        }
    }
}
