package com.fahedhermoza.developer.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

/***
 * Autor Fahed Hermoza
 * Facebook: https://www.facebook.com/fahedhermoza/
 * GitHub: https://github.com/fahedhermoza
 * Medium: https://medium.com/@FahedHermoza
 * Twiter: @FahedHermoza
 * Blog Personal: http://fahedhermoza.com/
 */

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheapActivity";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.fahedhermoza.developer.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.fahedhermoza.developer.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    private boolean mStateCheap;
    private static final String STATE_CHEAP = "state_cheap";

    int apiLevel = Build.VERSION.SDK_INT;

    private  TextView mApiLevel;


    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;

    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
        mApiLevel = (TextView) findViewById(R.id.apiLevelTextView);

        if(savedInstanceState != null){
            mStateCheap = savedInstanceState.getBoolean(STATE_CHEAP,false);
            if(mStateCheap){
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                mStateCheap = true;
                setExtraAnswerShownResult(mStateCheap);
            }
        }

        @SuppressLint("StringFormatMatches")
        final String apiLevelString = getString(R.string.api_level, apiLevel);
        mApiLevel.setText(apiLevelString);


        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                mStateCheap = true;
                setExtraAnswerShownResult(mStateCheap);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    int cx= mShowAnswer.getWidth()/2;
                    int cy= mShowAnswer.getHeight()/2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();

                }else{
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private  void setExtraAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedIntanceState) {
        super.onSaveInstanceState(savedIntanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedIntanceState.putBoolean(STATE_CHEAP,mStateCheap);
    }
}
