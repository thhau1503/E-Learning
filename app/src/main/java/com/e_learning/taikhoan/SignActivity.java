package com.e_learning.taikhoan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignActivity extends AppCompatActivity {
    EditText editPassword;
    EditText editPasswordConfirm;
    EditText editSignUsername;
    EditText editSignEmail;

    TextView textViewLogIn;
    Button buttonNext;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbHelper = new DatabaseHelper(this);

        editPassword = findViewById(R.id.editSignPassword);
        editPasswordConfirm = findViewById(R.id.editSignPasswordConfirm);
        editSignUsername = findViewById(R.id.editSignUsername);
        editSignEmail = findViewById(R.id.editSignEmail);
        textViewLogIn = findViewById(R.id.textViewSignUp);
        buttonNext = findViewById(R.id.btnSignUpNext);

        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignActivity.this, LoginActivity.class));
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editPassword.getText().toString();
                String confirmPassword = editPasswordConfirm.getText().toString();
                String username = editSignUsername.getText().toString();
                String hoten = "Người dùng";
                String email = editSignEmail.getText().toString();
                String phone = "";
                if (!isValidGmail(email)) {
                    Toast.makeText(getApplicationContext(), email + " là địa chỉ Gmail không hợp lệ.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<8){
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 8 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty() || hoten.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (dbHelper.checkEmailExist(email)) {
                    Toast.makeText(getApplicationContext(), "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (password.equals(confirmPassword)) {
                        boolean isAdded = dbHelper.addUser(username, password, hoten, email, phone);
                        if (isAdded) {
                            Toast.makeText(getApplicationContext(), "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu không khớp nhau", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    public static boolean isValidGmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
