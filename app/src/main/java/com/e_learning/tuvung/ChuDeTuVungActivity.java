package com.e_learning.tuvung;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.e_learning.R;

import java.util.ArrayList;
import java.util.List;

public class ChuDeTuVungActivity extends AppCompatActivity {
    private ListView listView;
    private ChuDeTuVungAdapter adapter;
    private List<ChuDeTuVung> listItems;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        listView = findViewById(R.id.listView);
        listItems = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);
        listItems = getDataFromDB();

        adapter = new ChuDeTuVungAdapter(this, listItems);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChuDeTuVung clickedItem = listItems.get(position);
                int idChuDe = clickedItem.getIdchude();
                String tenChuDe = clickedItem.getTenchude();

                Intent intent = new Intent(ChuDeTuVungActivity.this, TuVungActivity.class);
                intent.putExtra("ID_CHU_DE", idChuDe);
                intent.putExtra("TEN_CHU_DE", tenChuDe);
                startActivity(intent);
            }
        });
    }

    private List<ChuDeTuVung> getDataFromDB() {
        List<ChuDeTuVung> items = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ChuDeTuVung", null);

        // Duyệt qua kết quả truy vấn
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idchude = cursor.getInt(cursor.getColumnIndex("IDChuDe"));
                @SuppressLint("Range") String tenchude = cursor.getString(cursor.getColumnIndex("TenChuDe"));
                @SuppressLint("Range") byte[] hinhanh = cursor.getBlob(cursor.getColumnIndex("ChuDeImg"));

                ChuDeTuVung item = new ChuDeTuVung(idchude,tenchude, hinhanh);

                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return items;
    }
}