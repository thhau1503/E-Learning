package com.e_learning;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class EditProfile extends AppCompatActivity{
    private DatabaseHelper dbHelper;
    Button buttonSave;
    EditText HoTen, Phone, Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        dbHelper = new DatabaseHelper(this);
        buttonSave = (Button) findViewById(R.id.btnEditProfileSave);
        HoTen = (EditText) findViewById(R.id.editEditProfileName);
        Phone = (EditText) findViewById(R.id.editEditProfilePhone);
        Email = (EditText) findViewById(R.id.editEditProfileEmail);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = HoTen.getText().toString().trim();
                String phone = Phone.getText().toString().trim();
                String email = Email.toString().trim();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                if (hoTen.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(EditProfile.this, "Vui lòng nhập đầy đủ mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    if (dbHelper.updateProfile(username, hoTen, email, phone)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.apply();
                        Toast.makeText(EditProfile.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfile.this, LoginActivity.class));
                    } else {
                        Toast.makeText(EditProfile.this, "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}