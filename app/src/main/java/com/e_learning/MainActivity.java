package com.e_learning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e_learning.home.HomeActivity;
import com.e_learning.taikhoan.DatabaseHelper;
import com.e_learning.taikhoan.ForgotPasswordActivity;
import com.e_learning.taikhoan.SignActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        editTextUsername = findViewById(R.id.editLoginEmail);
        editTextPassword = findViewById(R.id.editLoginPassword);
        buttonLogin = findViewById(R.id.btnLogin);

        TextView textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        TextView textViewForgotPassword = (TextView) findViewById(R.id.textViewforgotPassword);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignActivity.class));
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick(v);
            }
        });
    }
    public void onLoginClick(View view) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên người dùng và mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkUser(username, password)) {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,     HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Tên người dùng hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
        }
    }
}