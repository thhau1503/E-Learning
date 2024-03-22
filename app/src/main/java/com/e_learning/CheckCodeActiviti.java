package com.e_learning;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckCodeActiviti extends AppCompatActivity {
    TextView textViewLogIn;
    Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        textViewLogIn = (TextView) findViewById(R.id.textViewSignUp);
        buttonNext = (Button) findViewById(R.id.btnDigitsCodeNext);
        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckCodeActiviti.this, LoginActivity.class));
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckCodeActiviti.this, ResetPasswordActivity.class));
            }
        });
    }
}
