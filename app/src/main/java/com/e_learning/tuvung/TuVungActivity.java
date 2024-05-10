package com.e_learning.tuvung;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.e_learning.R;

import java.util.ArrayList;
import java.util.List;

public class TuVungActivity extends AppCompatActivity {
    private ListView listView;
    private TuVungAdapter adapter;
    private List<TuVung> listItems;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);
        TextView txtTenChuDe = findViewById(R.id.textView4);

        listView = findViewById(R.id.listView);
        listItems = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        int idChuDe = getIntent().getIntExtra("ID_CHU_DE", -1);
        String tenChuDe = getIntent().getStringExtra("TEN_CHU_DE");
        txtTenChuDe.setText(tenChuDe);
        if (idChuDe != -1) {
            listItems = getDataFromDB(idChuDe);
        }

        adapter = new TuVungAdapter(this, listItems);
        listView.setAdapter(adapter);
    }

    private List<TuVung> getDataFromDB(int idChuDe) {
        List<TuVung> items = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM TuVung Where ID_ChuDe = " + idChuDe,null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID_Tu"));
                @SuppressLint("Range") int id_bo = cursor.getInt(cursor.getColumnIndex("ID_Tu"));
                @SuppressLint("Range") String tu = cursor.getString(cursor.getColumnIndex("DapAn"));
                @SuppressLint("Range") String nghia = cursor.getString(cursor.getColumnIndex("DichNghia"));
                @SuppressLint("Range") String loai = cursor.getString(cursor.getColumnIndex("LoaiTu"));
                @SuppressLint("Range") String audio = cursor.getString(cursor.getColumnIndex("Audio"));
                @SuppressLint("Range") byte[] hinhanh = cursor.getBlob(cursor.getColumnIndex("HinhAnh"));
                @SuppressLint("Range") int id_chude = cursor.getInt(cursor.getColumnIndex("ID_ChuDe"));
                @SuppressLint("Range") String vidu = cursor.getString(cursor.getColumnIndex("ViDu"));

                TuVung item = new TuVung(id,id_bo,tu,nghia,loai,audio,hinhanh,id_chude,vidu);

                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return items;
    }
}