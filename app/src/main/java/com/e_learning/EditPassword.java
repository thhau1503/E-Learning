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

public class EditPassword  extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    Button buttonNext;
    EditText oldPassword, newPassword, confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        dbHelper = new DatabaseHelper(this);
        buttonNext = (Button) findViewById(R.id.btnEditPasswordNext);
        oldPassword = (EditText) findViewById(R.id.editCheckOldPassword);
        newPassword = (EditText) findViewById(R.id.editNewPassword);
        confirmPassword = (EditText) findViewById(R.id.editCheckNewPassword);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Oldpassword = oldPassword.getText().toString().trim();
                String Newpassword = newPassword.getText().toString().trim();
                String ConfirmPassword = confirmPassword.toString().trim();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", ""); // Nếu không tìm thấy, trả về giá trị mặc định là ""
                String password = sharedPreferences.getString("password", ""); // Nếu không tìm thấy, trả về giá trị mặc định là ""
                if (Oldpassword.isEmpty() || Newpassword.isEmpty() || ConfirmPassword.isEmpty()) {
                    Toast.makeText(EditPassword.this, "Vui lòng nhập đầy đủ mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Oldpassword.equals(password)){
                    Toast.makeText(EditPassword.this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                }
                if(Newpassword.length()<8){
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 8 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Newpassword.equals(ConfirmPassword)){
                    if (dbHelper.updatePassword(username, Newpassword)) {
                        Toast.makeText(EditPassword.this, "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPassword.this, LoginActivity.class));
                    } else {
                        Toast.makeText(EditPassword.this, "Cập nhật mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(EditPassword.this, "Hai mật khẩu nhập không trùng nhau.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
