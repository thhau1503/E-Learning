package com.e_learning.taikhoan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.e_learning.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtHoTen, txtEmail, txtSDT, txtUsername;
    private DatabaseHelper dbHelper;

    private Button btnEditProfile, btnLogout,btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtHoTen = findViewById(R.id.tvName);
        txtEmail = findViewById(R.id.tvEmail);
        txtSDT = findViewById(R.id.tvMobile);
        txtUsername = findViewById(R.id.tvUsername);
        btnLogout = findViewById(R.id.btnLogout);
        btnEditProfile = findViewById(R.id.btnUpdate);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        dbHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int ID_User = sharedPreferences.getInt("ID_User", -1);
        NguoiDung user = getUserInfo(ID_User);

        if (user != null) {
            txtHoTen.setText(user.getHoTen());
            txtEmail.setText(user.getEmail());
            txtSDT.setText(user.getSDT());
            txtUsername.setText(user.getUsername());
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chuyển sang edit profile activity
                Intent intent = new Intent(ProfileActivity.this, EditProfile.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditPassword.class);
                startActivity(intent);
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