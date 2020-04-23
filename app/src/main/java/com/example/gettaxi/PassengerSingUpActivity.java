package com.example.gettaxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassengerSingUpActivity extends AppCompatActivity {

    private static final int NAME_LENGTH = 20;
    private static final int PASSWORD_LENGTH = 5;

    private static final String TAG = "PassengerActivity";

    private TextInputLayout emailTIL, nameTIL, passwordTIL, confirmPasswordTIL;

    private String nameInput, emailInput, passwordInput, confirmPasswordInput;

    private boolean isSingInActive;

    private Button singUpButton;

    private TextView singUpAsPassengerTV, tapToSingInTV;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sing_up);

        createAuth();

        createPassengerSinUpActivitySetup();
    }

    private void createPassengerSinUpActivitySetup() {
        singUpAsPassengerTV = findViewById(R.id.sing_up_as_passenger);

        emailTIL = findViewById(R.id.email_input_layout);
        nameTIL = findViewById(R.id.name_input_layout);
        passwordTIL = findViewById(R.id.password_input_layout);
        confirmPasswordTIL = findViewById(R.id.confirm_password_input_layout);

        singUpButton = findViewById(R.id.passenger_sing_up_button);

        tapToSingInTV = findViewById(R.id.tap_to_sing_in_text_view);
    }

    private void createAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void enterAsPassenger(View view) {

        getUserDataInputTextToValidate();

        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
        }
        if (isSingInActive) {
            singInUser();
        } else {
            if (!validateEmail() | !validateName() | !validatePassword() |
                    !validateConfirmPassword()) {
                return;
            }
            singUpUser();
        }
    }

    private void getUserDataInputTextToValidate() {
        if (emailTIL.getEditText() != null) {
            emailInput = emailTIL.getEditText().getText().toString().trim();
        }

        if (nameTIL.getEditText() != null) {
            nameInput = nameTIL.getEditText().getText().toString().trim();
        }

        if (passwordTIL.getEditText() != null) {
            passwordInput = passwordTIL.getEditText().getText().toString().trim();
        }

        if (confirmPasswordTIL.getEditText() != null) {
            confirmPasswordInput = confirmPasswordTIL.getEditText().getText().toString().trim();
        }
    }

    private void singUpUser() {
        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "SingUp -> SUCCESS");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            Log.w(TAG, "SingUp -> FAILURE", task.getException());
                            Toast.makeText(
                                    PassengerSingUpActivity.this,
                                    "Authentication failed.", Toast.LENGTH_SHORT)
                                    .show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void singInUser() {
        mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "SingIn -> SUCCESS");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            Log.w(TAG, "SingIn -> FAILURE", task.getException());
                            Toast.makeText(
                                    PassengerSingUpActivity.this,
                                    "Authentication failed.", Toast.LENGTH_SHORT)
                                    .show();
                            //updateUI(null);
                        }
                    }
                });
    }

    public void tapToSingIn(View view) {
        if (isSingInActive) {
            isSingInActive = false;
            singUpAsPassengerTV.setText(R.string.sing_up_as_passenger);
            confirmPasswordTIL.setVisibility(View.VISIBLE);
            singUpButton.setText(R.string.sing_up);
            tapToSingInTV.setText(R.string.tap_to_sing_in);
        } else {
            isSingInActive = true;
            singUpAsPassengerTV.setText(R.string.sing_in_as_passenger);
            confirmPasswordTIL.setVisibility(View.GONE);
            singUpButton.setText(R.string.sing_in);
            tapToSingInTV.setText(R.string.tap_to_sing_up);
        }
    }

    private boolean validateEmail() {
        if (emailInput.isEmpty()) {
            emailTIL.setError("Please, input your email");
            return false;
        } else {
            emailTIL.setError("");
            return true;
        }
    }

    private boolean validateName() {
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
        if (passwordInput.isEmpty()) {
            passwordTIL.setError("Please, input password");
            return false;
        } else if (passwordInput.length() <= PASSWORD_LENGTH) {
            passwordTIL.setError("Password length must be more than " + PASSWORD_LENGTH);
            return false;
        } else {
            confirmPasswordTIL.setError("");
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        if (!passwordInput.equals(confirmPasswordInput)) {
            confirmPasswordTIL.setError("Passwords must match");
            return false;
        } else {
            confirmPasswordTIL.setError("");
            return true;
        }
    }
}