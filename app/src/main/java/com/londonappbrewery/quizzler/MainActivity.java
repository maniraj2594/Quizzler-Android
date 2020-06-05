package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT = 8;

    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mIndex = 0;
    int mQuestion;
    int mScore = 0;


    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestionTextView.setText(mQuestion);

        View.OnClickListener trueListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quizzler","True clicked!!");
                //Anonymous Toast message
                //Toast.makeText(getApplicationContext(),"True pressed!!!",Toast.LENGTH_SHORT).show();
                checkAnswer(true);
                updateQuestion();
            }
        };
        mTrueButton.setOnClickListener(trueListener);

        //Anonymous OnCLickListener
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quizzler","False clicked!!");
                //Toast myToast = Toast.makeText(getApplicationContext(),"False pressed!!!",Toast.LENGTH_SHORT);
                //myToast.show();
                checkAnswer(false);
                updateQuestion();
            }
        });
    }

    private void updateQuestion(){
        mIndex = (mIndex + 1) % mQuestionBank.length;
        if(mIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over!!");
            alert.setCancelable(false);
            alert.setMessage("You scored "+mScore+" points!");
            alert.setPositiveButton("Close application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy((int)Math.ceil(100.0/mQuestionBank.length));
        mScoreTextView.setText("Score " + mScore + "/" +mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();
        if(userSelection == correctAnswer){
            mScore++;
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }

}
