package com.e_learning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HocNgonNgu.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE User (ID_User INTEGER PRIMARY KEY AUTOINCREMENT, HoTen TEXT, Point INTEGER, Email TEXT, SDT TEXT, Password TEXT, Username TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    public boolean checkUser(String usernameOrEmail, String password) {
        return checkUsernamePassword(usernameOrEmail, password) || checkEmailPassword(usernameOrEmail, password);
    }
    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM User WHERE Email = ? AND Password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }


    public NguoiDung getUserInfo(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        NguoiDung user = null;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("ID_User");
            int hoTenIndex = cursor.getColumnIndex("HoTen");
            int pointIndex = cursor.getColumnIndex("Point");
            int emailIndex = cursor.getColumnIndex("Email");
            int sdtIndex = cursor.getColumnIndex("SDT");

            if (idIndex != -1 && hoTenIndex != -1 && pointIndex != -1 && emailIndex != -1 && sdtIndex != -1) {
                Integer ID_User = cursor.getInt(idIndex);
                String HoTen = cursor.getString(hoTenIndex);
                Integer Point = cursor.getInt(pointIndex);
                String Email = cursor.getString(emailIndex);
                String SDT = cursor.getString(sdtIndex);

                user = new NguoiDung(ID_User, HoTen, Point, Email, SDT, username, password);
            }
        }

        cursor.close();
        return user;
    }



    public boolean addUser(String username, String password, String hoTen, String email, String sdt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Password", password);
        values.put("HoTen", hoTen);
        values.put("Email", email);
        values.put("SDT", sdt);

        long result = db.insert("User", null, values);
        db.close();
        return result != -1;
    }
    public boolean updatePassword(String usernameOrEmail, String password) {
        return updatePasswordByEmail(usernameOrEmail, password) || updatePasswordByUsername(usernameOrEmail, password);
    }
    public boolean updatePasswordByEmail(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Password", newPassword);

        int affectedRows = db.update("User", values, "Email = ?", new String[]{email});
        db.close();

        return affectedRows > 0;
    }
    public boolean updatePasswordByUsername(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Password", newPassword);

        int affectedRows = db.update("User", values, "Username = ?", new String[]{username});
        db.close();

        return affectedRows > 0;
    }
    public boolean checkEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User WHERE Email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean emailExists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return emailExists;
    }
    public boolean updateProfile(String usernameOrEmail, String newHoTen, String newEmail, String newSDT) {
        return updateUserInfoByEmail(usernameOrEmail, newHoTen, newEmail, newSDT) || updateUserInfoByUsername(usernameOrEmail, newHoTen, newEmail, newSDT);
    }
    public boolean updateUserInfoByUsername(String username, String newHoTen, String newEmail, String newSDT) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTen", newHoTen);
        values.put("Email", newEmail);
        values.put("SDT", newSDT);

        int affectedRows = db.update("User", values, "Username = ?", new String[]{username});
        db.close();

        return affectedRows > 0;
    }

    public boolean updateUserInfoByEmail(String email, String newHoTen, String newEmail, String newSDT) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTen", newHoTen);
        values.put("Email", newEmail);
        values.put("SDT", newSDT);

        int affectedRows = db.update("User", values, "Email = ?", new String[]{email});
        db.close();

        return affectedRows > 0;
    }


}
