package com.e_learning;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    TextView textViewLogIn;
    EditText editNewPassword, editCheckNewPassword;
    Button buttonNext;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        textViewLogIn = (TextView) findViewById(R.id.textViewSignUp);
        buttonNext = (Button) findViewById(R.id.btnResetPasswordNext);
        editNewPassword = (EditText) findViewById(R.id.editNewPassword);
        editCheckNewPassword = (EditText) findViewById(R.id.editCheckNewPassword);
        dbHelper = new DatabaseHelper(this);

        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewPassword = editNewPassword.getText().toString().trim();
                String CheckNewPassword = editCheckNewPassword.getText().toString().trim();
                String receivedEmail = getIntent().getStringExtra("EMAIL_EXTRA");
                if (NewPassword.isEmpty() || CheckNewPassword.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Vui lòng nhập đầy đủ mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(NewPassword.length()<8){
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 8 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(NewPassword.equals(CheckNewPassword)){
                    if (dbHelper.updatePasswordByEmail(receivedEmail, CheckNewPassword)) {
                        Toast.makeText(ResetPasswordActivity.this, "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Cập nhật mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ResetPasswordActivity.this, "Hai mật khẩu nhập không trùng nhau.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}