package com.company.flagquizgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewTotalCorrect, textViewTotalWrong, textViewTotalEmpty,textViewResults;
    private Button  buttonPlayAgain, buttonQuitGame; //variables to get the values passed from QuizActivity
    private int correct, wrong, empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewTotalCorrect = findViewById(R.id.textViewTotalCorrect);
        textViewTotalWrong = findViewById(R.id.textViewWrong);
        textViewTotalEmpty =findViewById(R.id.textViewEmpty);
        textViewResults = findViewById(R.id.textViewResults);

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);
        empty = getIntent().getIntExtra("empty", 0);

        textViewTotalCorrect.setText((String.format("Total Correct Answer: %s", correct)));
        textViewTotalWrong.setText((String.format("Total Wrong Answer: %s", wrong)));
        textViewTotalEmpty.setText((String.format("Total Empty Answer: %s", empty)));
        textViewResults.setText((String.format("Success Rate: %s", (correct *10))));


        buttonPlayAgain =findViewById(R.id.buttonPlayAgain);
        buttonQuitGame = findViewById(R.id.buttonQuit);

        buttonPlayAgain.setOnClickListener((v->{
            Intent intentPlayAgain = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intentPlayAgain);
            finish();

        }));

        //can simply use: finish() Method to close App/Game.
        buttonQuitGame.setOnClickListener((v->{
            Intent newIntent = new Intent(Intent.ACTION_MAIN);
            newIntent.addCategory(Intent.CATEGORY_HOME);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            finish();
        }));


    }
}