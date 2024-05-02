package com.e_learning.tracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.e_learning.R;
import com.e_learning.tuvung.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TracNghiemActivity extends AppCompatActivity {
    private List<TracNghiem> questions;
    private int currentQuestionIndex;
    private DatabaseHelper dbHelper;
    private RadioButton op1, op2, op3, op4;

    private RadioButton lastSelectedRadioButton;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracnghiem);
        dbHelper = new DatabaseHelper(this);
        questions = getAllQuestions();

        Button btnNext = findViewById(R.id.btnNextLN);
        Button btnPrevious = findViewById(R.id.btnpreviousLN);
        Button btnConfirm = findViewById(R.id.btnconfirmLN);

        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        op4 = findViewById(R.id.op4);

        radioGroup = findViewById(R.id.radiochoices);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (lastSelectedRadioButton != null) {
                    lastSelectedRadioButton.setBackgroundResource(R.drawable.bg_lis);
                }
                lastSelectedRadioButton = findViewById(checkedId);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRadioButtonBackgrounds();
                radioGroup.clearCheck();

                currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
                displayQuestion();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRadioButtonBackgrounds();
                radioGroup.clearCheck();

                currentQuestionIndex = (currentQuestionIndex - 1 + questions.size()) % questions.size();
                displayQuestion();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedAnswer = selectedRadioButton.getText().toString();

                TracNghiem currentQuestion = questions.get(currentQuestionIndex);
                String correctAnswer = null;
                switch (currentQuestion.getCorrectAnswer()) {
                    case "1":
                        correctAnswer = currentQuestion.getOptionA();
                        break;
                    case "2":
                        correctAnswer = currentQuestion.getOptionB();
                        break;
                    case "3":
                        correctAnswer = currentQuestion.getOptionC();
                        break;
                    case "4":
                        correctAnswer = currentQuestion.getOptionD();
                        break;
                }

                if (selectedAnswer.equals(correctAnswer)) {
                    selectedRadioButton.setBackgroundResource(R.drawable.bg_lis_true);
                } else {
                    selectedRadioButton.setBackgroundResource(R.drawable.bg_lis_false);
                }
            }
        });

        displayQuestion();
    }

    @SuppressLint("Range")
    public List<TracNghiem> getAllQuestions() {
        List<TracNghiem> questions = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TracNghiem", null);
        if (cursor.moveToFirst()) {
            do {
                TracNghiem question = new TracNghiem();
                question.setId(cursor.getInt(cursor.getColumnIndex("ID_Cau")));
                question.setSetId(cursor.getInt(cursor.getColumnIndex("ID_Bo")));
                question.setContent(cursor.getString(cursor.getColumnIndex("NoiDung")));
                question.setOptionA(cursor.getString(cursor.getColumnIndex("DapAn_A")));
                question.setOptionB(cursor.getString(cursor.getColumnIndex("DapAn_B")));
                question.setOptionC(cursor.getString(cursor.getColumnIndex("DapAn_C")));
                question.setOptionD(cursor.getString(cursor.getColumnIndex("DapAn_D")));
                question.setCorrectAnswer(cursor.getString(cursor.getColumnIndex("DapAn_True")));
                questions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questions;
    }

    private void displayQuestion() {
        TracNghiem currentQuestion = questions.get(currentQuestionIndex);
        TextView txtQuestion = findViewById(R.id.txtquestionTN);

        txtQuestion.setText(currentQuestion.getContent());
        op1.setText(currentQuestion.getOptionA());
        op2.setText(currentQuestion.getOptionB());
        op3.setText(currentQuestion.getOptionC());
        op4.setText(currentQuestion.getOptionD());
    }

    private void resetRadioButtonBackgrounds() {
        op1.setBackgroundResource(R.drawable.bg_lis);
        op2.setBackgroundResource(R.drawable.bg_lis);
        op3.setBackgroundResource(R.drawable.bg_lis);
        op4.setBackgroundResource(R.drawable.bg_lis);
    }
}