package com.e_learning.dienkhuyet;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.e_learning.R;
import com.e_learning.tuvung.DatabaseHelper;

public class DienKhuyetActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Cursor cursor;
    private TextView questionTextView;
    private EditText answerEditText;
    private Button submitButton;
    private String currentAnswer;
    private TextView hintTextView;
    private int currentExerciseId;
    private TextView sencencesTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_khuyet);

        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.buttonSubmit);
        hintTextView = findViewById(R.id.hintTextView);
        Button previousButton = findViewById(R.id.previousButton);
        Button nextButton = findViewById(R.id.nextButton);
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getDatabase();


        currentExerciseId = 1;
        loadExercise(currentExerciseId);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = answerEditText.getText().toString().trim();
                if (userAnswer.equalsIgnoreCase(currentAnswer)) {
                    Toast.makeText(DienKhuyetActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    currentExerciseId++;
                    loadExercise(currentExerciseId);
                } else {
                    Toast.makeText(DienKhuyetActivity.this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentExerciseId > 1) {
                    currentExerciseId--;
                    loadExercise(currentExerciseId);
                } else {
                    Toast.makeText(DienKhuyetActivity.this, "This is the first exercise.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxExerciseId = getMaxExerciseId();
                if (currentExerciseId < maxExerciseId) {
                    currentExerciseId++;
                    loadExercise(currentExerciseId);
                } else {
                    Toast.makeText(DienKhuyetActivity.this, "This is the last exercise.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("Range")
    private void loadExercise(int exerciseId) {
        cursor = database.rawQuery("SELECT * FROM DienKhuyet WHERE ID_Cau = ?", new String[]{String.valueOf(exerciseId)});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex("NoiDung"));
            @SuppressLint("Range") String hint = cursor.getString(cursor.getColumnIndex("GoiY"));
            currentAnswer = cursor.getString(cursor.getColumnIndex("DapAn"));
            questionTextView.setText(question);
            hintTextView.setText("Hint: " + hint);
            answerEditText.setText("");
        } else {
            Toast.makeText(this, "Exercise not found.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private int getMaxExerciseId() {
        int maxId = -1;
        Cursor cursor = database.rawQuery("SELECT MAX(ID_Cau) AS max_id FROM DienKhuyet", null);
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(cursor.getColumnIndex("max_id"));
        }
        cursor.close();
        return maxId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        if (database != null) {
            database.close();
        }
    }
}
