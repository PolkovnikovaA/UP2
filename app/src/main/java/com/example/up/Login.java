package com.example.up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText email;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        email = findViewById(R.id.EditText_Email);
        button = findViewById(R.id.Button_SignIn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailAddress(email);
            }
        });
    }

    private boolean EmailAddress(EditText email) {
        String emailInput = email.getText().toString();
        if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Intent intent = new Intent(Login.this, Glavnaya.class);
            startActivity(intent);
            Toast.makeText(this, "Все хорошо", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Введен неправильный Email", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onClickRegister(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
