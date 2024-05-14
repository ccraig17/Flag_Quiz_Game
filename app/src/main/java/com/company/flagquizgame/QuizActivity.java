package com.company.flagquizgame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;

public class QuizActivity extends AppCompatActivity {
    private TextView textViewCorrect, textViewWrong, textViewEmpty, textViewQuestion;
    private ImageView imageViewFlag, imageViewNext;
    private Button buttonA, buttonB, buttonC, buttonD;
    private FlagsDatabase fdatabase;
    private ArrayList<FlagsModel> questionsList;
    int correct = 0;
    int wrong = 0;
    int empty = 0;
    int question = 0;

    private FlagsModel correctFlag;
    private ArrayList<FlagsModel> wrongOptionsList;
    HashSet<FlagsModel> mixOptions = new HashSet<>(); //HashSet hold items randomly
    ArrayList<FlagsModel> options = new ArrayList<>();
    boolean buttonControl = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        textViewCorrect = findViewById(R.id.textViewCorrect);
        textViewEmpty = findViewById(R.id. textViewEmpty);
        textViewWrong = findViewById(R.id.textViewWrong);
        textViewQuestion = findViewById(R.id.textViewQuestion);

        imageViewFlag = findViewById(R.id.imageViewFlag);
        imageViewNext = findViewById(R.id.imageViewNext);

        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        //getting the questions from the database.
        fdatabase = new FlagsDatabase(QuizActivity.this);
        questionsList = new FlagsDAO().getRandomTenQuestion(fdatabase);
        loadQuestions();

        buttonA.setOnClickListener((v->{
            answerControl(buttonA);
        }));

        buttonB.setOnClickListener((v->{
            answerControl(buttonB);
        }));

        buttonC.setOnClickListener((v->{
            answerControl(buttonC);
        }));

        buttonD.setOnClickListener((v->{
            answerControl(buttonD);

        }));

        imageViewNext.setOnClickListener((v->{
            question +=1;
            if(!buttonControl && question <10){
                empty+=1;
                textViewEmpty.setText(String.format("Empty: %s", empty));
                loadQuestions();
            } else if(buttonControl && question <10){
                loadQuestions();
                //resets ALL buttons back to being clickable
                buttonA.setClickable(true);
                buttonB.setClickable(true);
                buttonC.setClickable(true);
                buttonD.setClickable(true);

                //How to change the button background colour back to the set-point? deprecation method..
                buttonA.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));
                buttonB.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));
                buttonC.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));
                buttonD.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));

            }else if(question == 10){
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);
                intent.putExtra("empty", empty);
                startActivity(intent);
                finish();
            }
            buttonControl = false;
        }));
    }


    public void loadQuestions(){
        textViewQuestion.setText(String.format("Questions: %s ", (question +1)));
        correctFlag = questionsList.get(question);

        /* fix the depreciated method: getIdentifier() whats the replacement?  deprecation method..*/
       // imageViewFlag.setImageResource(getResources().getIdentifier(correctFlag.getFlag_image(), "drawable", getPackageName()));
        imageViewFlag.setImageResource(getResources().getIdentifier(correctFlag.getFlag_image(), "drawable", getPackageName()));

        wrongOptionsList = new FlagsDAO().getRandomThreeOptions(fdatabase,correctFlag.getFlag_id());
        mixOptions.clear();
        mixOptions.add(correctFlag);
        mixOptions.add(wrongOptionsList.get(0));
        mixOptions.add(wrongOptionsList.get(1));
        mixOptions.add(wrongOptionsList.get(2));

        options.clear();
        options.addAll(mixOptions);

        buttonA.setText(options.get(0).getFlag_name());
        buttonB.setText(options.get(1).getFlag_name());
        buttonC.setText(options.get(2).getFlag_name());
        buttonD.setText(options.get(3).getFlag_name());


    }
    public void answerControl(Button button){
        String buttonText = button.getText().toString();
        String correctAnswer = correctFlag.getFlag_name();
        if(buttonText.equals(correctAnswer)){
            correct +=1;
            button.setBackgroundColor(Color.GREEN);
        }else{
            wrong +=1;
            button.setBackgroundColor(Color.RED);

            if(buttonA.getText().toString().equals(correctAnswer)){
                buttonA.setBackgroundColor(Color.GREEN);
            }
            if(buttonB.getText().toString().equals(correctAnswer)){
                buttonB.setBackgroundColor(Color.GREEN);
            }
            if(buttonC.getText().toString().equals(correctAnswer)){
                buttonC.setBackgroundColor(Color.GREEN);
            }
            if(buttonD.getText().toString().equals(correctAnswer)){
                buttonD.setBackgroundColor(Color.GREEN);
            }

        }
        //disables the unselected buttons from being clickable after selection
        buttonA.setClickable(false);
        buttonB.setClickable(false);
        buttonC.setClickable(false);
        buttonD.setClickable(false);

        textViewCorrect.setText(String.format("Correct: %s", correct));
        textViewWrong.setText(String.format("Wrong: %s", wrong));

        buttonControl = true;
    }

}