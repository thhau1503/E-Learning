package com.e_learning;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortSentenceActivity extends AppCompatActivity {

    private TextView textViewWord1, textViewWord2, textViewWord3, textViewWord4;
    private TextView textViewWordChoice1, textViewWordChoice2, textViewWordChoice3, textViewWordChoice4;
    private LinearLayout linearLayoutWords, linearLayoutWordsToChoose;
    private Button buttonSubmit;
    private DatabaseHelper databaseHelper;
    private int currentSentenceId = 1;
    private String correctSentence = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_arrangement);

        textViewWord1 = findViewById(R.id.textViewWord1);
        textViewWord2 = findViewById(R.id.textViewWord2);
        textViewWord3 = findViewById(R.id.textViewWord3);
        textViewWord4 = findViewById(R.id.textViewWord4);
        textViewWordChoice1 = findViewById(R.id.textViewWordChoice1);
        textViewWordChoice2 = findViewById(R.id.textViewWordChoice2);
        textViewWordChoice3 = findViewById(R.id.textViewWordChoice3);
        textViewWordChoice4 = findViewById(R.id.textViewWordChoice4);
        linearLayoutWords = findViewById(R.id.linearLayoutWords);
        linearLayoutWordsToChoose = findViewById(R.id.linearLayoutWordsToChoose);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        databaseHelper = new DatabaseHelper(this);

        displaySentenceParts();
        correctSentence = getCorrectSentenceFromDatabase();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        // Thiết lập sự kiện touch để bắt đầu kéo từng từ
        textViewWord1.setOnTouchListener(new MyTouchListener());
        textViewWord2.setOnTouchListener(new MyTouchListener());
        textViewWord3.setOnTouchListener(new MyTouchListener());
        textViewWord4.setOnTouchListener(new MyTouchListener());

        // Thiết lập sự kiện drop để xử lý khi từ được thả vào ô trống
        textViewWordChoice1.setOnDragListener(new MyDragListener());
        textViewWordChoice2.setOnDragListener(new MyDragListener());
        textViewWordChoice3.setOnDragListener(new MyDragListener());
        textViewWordChoice4.setOnDragListener(new MyDragListener());
    }

    private String getCorrectSentenceFromDatabase() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT DapAn FROM SapXepCau WHERE IDCau = " + currentSentenceId, null);

        String correctSentence = "";

        if (cursor.moveToFirst()) {
            correctSentence = cursor.getString(0);
        }

        cursor.close();
        database.close();

        return correctSentence;
    }

    private void displaySentenceParts() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT Part1, Part2, Part3, Part4 FROM SapXepCau WHERE IDCau = " + currentSentenceId, null);

        List<String> partsList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            partsList.add(cursor.getString(0));
            partsList.add(cursor.getString(1));
            partsList.add(cursor.getString(2));
            partsList.add(cursor.getString(3));
        }

        cursor.close();
        database.close();

        // Randomize danh sách các phần
        Collections.shuffle(partsList);

        // Gán các phần đã randomize vào các TextView
        textViewWord1.setText(partsList.get(0));
        textViewWord2.setText(partsList.get(1));
        textViewWord3.setText(partsList.get(2));
        textViewWord4.setText(partsList.get(3));
    }



    private void checkAnswer() {
        String userSentence = textViewWordChoice1.getText().toString() + " "
                + textViewWordChoice2.getText().toString() + " "
                + textViewWordChoice3.getText().toString() + " "
                + textViewWordChoice4.getText().toString();

        if (userSentence.trim().equals(correctSentence.trim())) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            currentSentenceId++;
            clearAnswer();
            displaySentenceParts();
        } else {
            Toast.makeText(this, "Incorrect! Please try again.", Toast.LENGTH_SHORT).show();
            clearAnswer();
        }
    }


    private void clearAnswer() {

        textViewWordChoice1.setText("_________");
        textViewWordChoice2.setText("_________");
        textViewWordChoice3.setText("_________");
        textViewWordChoice4.setText("_________");


        displaySentenceParts();
    }



    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(null, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }
    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    if (owner.getId() == R.id.linearLayoutWordsToChoose) {
                        owner.removeView(view);
                    }
                    TextView target = (TextView) v;
                    target.setText(((TextView) view).getText());
                    view.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        }
    }
}
