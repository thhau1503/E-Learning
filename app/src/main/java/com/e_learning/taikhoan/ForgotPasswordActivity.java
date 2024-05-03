package com.e_learning.taikhoan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;

import com.e_learning.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView textViewLogIn;
    Button buttonSendEmail;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textViewLogIn = findViewById(R.id.textViewSignUp);
        buttonSendEmail = findViewById(R.id.btnSendEmail);
        dbHelper = new DatabaseHelper(this);

        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            }
        });

        buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editLoginEmail = findViewById(R.id.editLoginEmail);
                String email = editLoginEmail.getText().toString();
                if (!isValidGmail(email)) {
                    Toast.makeText(getApplicationContext(), email + " là địa chỉ Gmail không hợp lệ.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.checkEmailExist(email)) {
                    
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Email không tồn tại.", Toast.LENGTH_SHORT).show();
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
