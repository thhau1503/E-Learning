package com.e_learning;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DienKhuyet extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Cursor cursor;
    private TextView questionTextView;
    private EditText answerEditText;
    private Button submitButton;
    private String currentAnswer;
    private TextView hintTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_khuyet);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.submitButton);
        hintTextView =findViewById(R.id.hintTextView);


        // Initialize database
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getDatabase();

        // Load first exercise
        loadNextExercise();

        // Submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = answerEditText.getText().toString().trim();
                if (userAnswer.equalsIgnoreCase(currentAnswer)) {
                    // Correct answer
                    Toast.makeText(DienKhuyet.this, "Correct!", Toast.LENGTH_SHORT).show();
                    // Load next exercise
                    loadNextExercise();
                } else {
                    // Incorrect answer
                    Toast.makeText(DienKhuyet.this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadNextExercise() {

        int currentExerciseId = -1;
        if (cursor != null && !cursor.isClosed()) {
            currentExerciseId = cursor.getInt(cursor.getColumnIndex("ID_Cau"));
            cursor.close();
        }
        cursor = database.rawQuery("SELECT * FROM DienKhuyet WHERE ID_Cau > ? LIMIT 1", new String[]{String.valueOf(currentExerciseId)});
        if (cursor.moveToFirst()) {
            String question = cursor.getString(cursor.getColumnIndex("NoiDung"));
            String hint = cursor.getString(cursor.getColumnIndex("GoiY"));
            currentAnswer = cursor.getString(cursor.getColumnIndex("DapAn"));
            questionTextView.setText(question);
            hintTextView.setText("Hint: " + hint);
            answerEditText.setText("");
        } else {
            Toast.makeText(this, "No more exercises.", Toast.LENGTH_SHORT).show();
            finish();
        }
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