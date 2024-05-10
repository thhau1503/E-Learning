package com.e_learning.taikhoan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.e_learning.R;

public class CheckCodeActivity extends AppCompatActivity {
    TextView textViewLogIn;
    Button buttonNext;
    EditText editTextCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        String receivedEmail = getIntent().getStringExtra("EMAIL_EXTRA");
        textViewLogIn = (TextView) findViewById(R.id.textViewSignUp);
        buttonNext = (Button) findViewById(R.id.btnDigitsCodeNext);
        editTextCode = (EditText) findViewById(R.id.editCodeYourEmail);

        String savedCode = getIntent().getStringExtra("CODE_EXTRA");


        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckCodeActivity.this, LoginActivity.class));
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextCode.getText().toString().equals(savedCode)){
                    Intent intent = new Intent(CheckCodeActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("EMAIL_EXTRA", receivedEmail);
                    startActivity(intent);
                }
                if(!editTextCode.getText().toString().equals(savedCode)){
                    editTextCode.setError("Mã xác nhận không đúng");
                }
                if(editTextCode.getText().toString().isEmpty()){
                    editTextCode.setError("Vui lòng nhập mã xác nhận");
                }
            }
        });

    }

}
