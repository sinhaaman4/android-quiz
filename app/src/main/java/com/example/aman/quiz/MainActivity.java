package com.example.aman.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonStartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartQuiz=(Button)findViewById(R.id.button_start_quiz);

    }

    public void start_quiz(View v)
    {
        Intent intent = new Intent(MainActivity.this,QuizActivity.class);
        startActivity(intent);
    }
}
