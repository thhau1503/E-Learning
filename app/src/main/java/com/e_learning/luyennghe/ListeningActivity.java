package com.e_learning.luyennghe;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.e_learning.R;
import com.e_learning.tuvung.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListeningActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private MediaPlayer mediaPlayer;
    private Button btnConfirm, btnNext, btnPrevious;
    private ImageButton btnPlayAudio;
    private RadioButton op1, op2, op3, op4;
    private List<Listening> listenings;
    private int currentQuestionIndex = 0;
    private ImageView imgHinh;
    private RadioGroup rdChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyennghe);
        dbHelper = new DatabaseHelper(this);
        listenings = getAllListenings();
        btnConfirm = findViewById(R.id.btnconfirmLN);
        btnNext = findViewById(R.id.btnNextLN);
        btnPrevious = findViewById(R.id.btnpreviousLN);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        op4 = findViewById(R.id.op4);
        imgHinh = findViewById(R.id.imgHinh);
        btnPlayAudio = findViewById(R.id.ImgBT);
        rdChoices = findViewById(R.id.radiochoices);

        displayQuestion();

        btnPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                mediaPlayer = new MediaPlayer();
                try {
                    AssetFileDescriptor afd = getAssets().openFd(listenings.get(currentQuestionIndex).getAudio());
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    op1.setBackgroundResource(R.drawable.bg_lis);
                    op2.setBackgroundResource(R.drawable.bg_lis);
                    op3.setBackgroundResource(R.drawable.bg_lis);
                    op4.setBackgroundResource(R.drawable.bg_lis);
                }
            }
        };

        op1.setOnCheckedChangeListener(listener);
        op2.setOnCheckedChangeListener(listener);
        op3.setOnCheckedChangeListener(listener);
        op4.setOnCheckedChangeListener(listener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                resetRadioButtonBackgrounds();

                rdChoices.clearCheck();

                currentQuestionIndex = (currentQuestionIndex + 1) % listenings.size();
                displayQuestion();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                resetRadioButtonBackgrounds();

                rdChoices.clearCheck();

                currentQuestionIndex = (currentQuestionIndex - 1 + listenings.size()) % listenings.size();
                displayQuestion();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = "";
                if (op1.isChecked()) {
                    userAnswer = op1.getText().toString();
                } else if (op2.isChecked()) {
                    userAnswer = op2.getText().toString();
                } else if (op3.isChecked()) {
                    userAnswer = op3.getText().toString();
                } else if (op4.isChecked()) {
                    userAnswer = op4.getText().toString();
                }

                String correctAnswer = listenings.get(currentQuestionIndex).getDapAn_True();
                if (userAnswer.equals(correctAnswer)) {
                    // If the user's answer is correct, set the RadioButton's background to green
                    if (op1.isChecked()) {
                        op1.setBackgroundResource(R.drawable.bg_lis_true);
                    } else if (op2.isChecked()) {
                        op2.setBackgroundResource(R.drawable.bg_lis_true);
                    } else if (op3.isChecked()) {
                        op3.setBackgroundResource(R.drawable.bg_lis_true);
                    } else if (op4.isChecked()) {
                        op4.setBackgroundResource(R.drawable.bg_lis_true);
                    }
                } else {
                    // If the user's answer is incorrect, set the RadioButton's background to red
                    if (op1.isChecked()) {
                        op1.setBackgroundResource(R.drawable.bg_lis_false);
                    } else if (op2.isChecked()) {
                        op2.setBackgroundResource(R.drawable.bg_lis_false);
                    } else if (op3.isChecked()) {
                        op3.setBackgroundResource(R.drawable.bg_lis_false);
                    } else if (op4.isChecked()) {
                        op4.setBackgroundResource(R.drawable.bg_lis_false);
                    }
                }
            }
        });

    }

    @SuppressLint("Range")
    public List<Listening> getAllListenings() {
        List<Listening> listenings = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LuyenNghe", null);
        if (cursor.moveToFirst()) {
            do {
                Listening listening = new Listening();
                listening.setID_Bai(cursor.getInt(cursor.getColumnIndex("ID_Bai")));
                listening.setID_Bo(cursor.getInt(cursor.getColumnIndex("ID_Bo")));
                listening.setDapAn_A(cursor.getString(cursor.getColumnIndex("DapAn_A")));
                listening.setDapAn_B(cursor.getString(cursor.getColumnIndex("DapAn_B")));
                listening.setDapAn_C(cursor.getString(cursor.getColumnIndex("DapAn_C")));
                listening.setDapAn_D(cursor.getString(cursor.getColumnIndex("DapAn_D")));
                listening.setDapAn_True(cursor.getString(cursor.getColumnIndex("DapAn_True")));
                listening.setHinhAnh(cursor.getBlob(cursor.getColumnIndex("HinhAnh")));
                listening.setAudio(cursor.getString(cursor.getColumnIndex("Audio")));
                listenings.add(listening);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listenings;
    }

    private void displayQuestion() {
        Listening currentQuestion = listenings.get(currentQuestionIndex);
        op1.setText(currentQuestion.getDapAn_A());
        op2.setText(currentQuestion.getDapAn_B());
        op3.setText(currentQuestion.getDapAn_C());
        op4.setText(currentQuestion.getDapAn_D());

        // Display image
        byte[] imageBytes = currentQuestion.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imgHinh.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    private void resetRadioButtonBackgrounds() {
        op1.setBackgroundResource(R.drawable.bg_lis);
        op2.setBackgroundResource(R.drawable.bg_lis);
        op3.setBackgroundResource(R.drawable.bg_lis);
        op4.setBackgroundResource(R.drawable.bg_lis);
    }

}