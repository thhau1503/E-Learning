package com.e_learning.taikhoan;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e_learning.R;

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

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int ID_User = sharedPreferences.getInt("ID_User", -1);
        NguoiDung user = getUserInfo(ID_User);

        if (user != null) {
            HoTen.setText(user.getHoTen());
            Email.setText(user.getEmail());
            Phone.setText(user.getSDT());
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = HoTen.getText().toString().trim();
                String phone = Phone.getText().toString().trim();
                String email = Email.getText().toString().trim();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                if (hoTen.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(EditProfile.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    if (dbHelper.updateProfile(username, hoTen, email, phone)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.apply();
                        Toast.makeText(EditProfile.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfile.this, ProfileActivity.class));
                    } else {
                        Toast.makeText(EditProfile.this, "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @SuppressLint("Range")
    public NguoiDung getUserInfo(Integer ID_User) {
        NguoiDung user = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] args = { ID_User.toString() };
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE ID_User = ?", args);
        if (cursor.moveToFirst()) {
            user = new NguoiDung();
            user.setID_User(Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID_User"))));
            user.setHoTen(cursor.getString(cursor.getColumnIndex("HoTen")));
            user.setPoint(cursor.getInt(cursor.getColumnIndex("Point")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
            user.setSDT(cursor.getString(cursor.getColumnIndex("SDT")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("Password")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("Username")));
        }
        cursor.close();
        return user;
    }
}