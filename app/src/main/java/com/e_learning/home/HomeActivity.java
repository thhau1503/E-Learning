package com.e_learning.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.e_learning.R;
import com.e_learning.dienkhuyet.DienKhuyetActivity;
import com.e_learning.luyennghe.ListeningActivity;
import com.e_learning.sapxepcau.SortSentenceActivity;
import com.e_learning.taikhoan.ProfileActivity;
import com.e_learning.tracnghiem.TracNghiemActivity;
import com.e_learning.tuvung.ChuDeTuVungActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class HomeActivity extends AppCompatActivity {
    private CardView cardViewTuVung, cardViewSapXep,cardViewTracNghiem,cardViewDienKhuyet,cardViewLuyenNghe, cardViewProfile;
    private BottomNavigationItemView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        cardViewTuVung = findViewById(R.id.cardViewHocTuVung);
        cardViewSapXep = findViewById(R.id.cardViewSapXepCau);
        cardViewTracNghiem = findViewById(R.id.cardViewTracNghiem);
        cardViewDienKhuyet = findViewById(R.id.cardViewDienKhuyet);
        cardViewLuyenNghe = findViewById(R.id.cardViewLuyenNghe);
        cardViewProfile = findViewById(R.id.cardViewProfile);
        cardViewTuVung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChuDeTuVungActivity.class);
                startActivity(intent);
            }
        });

        cardViewSapXep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SortSentenceActivity.class);
                startActivity(intent);
            }
        });

        cardViewDienKhuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DienKhuyetActivity.class);
                startActivity(intent);
            }
        });

        cardViewLuyenNghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListeningActivity.class);
                startActivity(intent);
            }
        });

        cardViewTracNghiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TracNghiemActivity.class);
                startActivity(intent);
            }
        });

        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}