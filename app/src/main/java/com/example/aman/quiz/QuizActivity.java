package com.example.aman.quiz;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aman on 06-04-2018.
 */

public class QuizActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;
    private ColorStateList textColorDefaultRb;
    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = (TextView) findViewById(R.id.text_view_question);
        textViewScore = (TextView) findViewById(R.id.text_view_score);
        textViewQuestionCount = (TextView) findViewById(R.id.text_view_question_count);
        rbGroup = (RadioGroup) findViewById(R.id.radio_group);
        rb1 = (RadioButton) findViewById(R.id.radio_button1);
        rb2 = (RadioButton) findViewById(R.id.radio_button2);
        rb3 = (RadioButton) findViewById(R.id.radio_button3);
        buttonConfirmNext = (Button) findViewById(R.id.button_confirm_next);

        textColorDefaultRb=rb1.getTextColors();

        QuizdbHelper dbHelper = new QuizdbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();
         buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!answered){
                if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked())
                {
                    checkAnswer();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select an answer",Toast.LENGTH_SHORT).show();
                }
            }
            else{showNextQuestion();}
        }
    });
    }
        private void showNextQuestion(){
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if(questionCounter < questionCountTotal)
        {
            currentQuestion=questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: "+ questionCounter+"/"+questionCountTotal);
            answered=false;
            buttonConfirmNext.setText("confirm");
        }
        else{
            finishQuiz();
        }

    }

    private void checkAnswer(){
        answered=true;

        RadioButton rbSelected = (RadioButton) findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected)+1;

        if(answerNr==currentQuestion.getAnswerNr()){
            score++;
            textViewScore.setText("Score: "+score);
        }
        showSolution();
    }

    private void showSolution(){
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch(currentQuestion.getAnswerNr()){

            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
        }
        if(questionCounter < questionCountTotal){
            buttonConfirmNext.setText("Next");
        }
        else{
            buttonConfirmNext.setText("Finish");
        }
    }
   private void finishQuiz() {
        finish();
    }

}
