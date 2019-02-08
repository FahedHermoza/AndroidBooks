package com.fahedhermoza.developer.geoquiz;

/***
 * Autor Fahed Hermoza
 * Blog Personal: https://www.facebook.com/fahedhermoza/
 * GitHub: https://github.com/fahedhermoza
 * Medium: https://medium.com/@FahedHermoza
 * Twiter: @FahedHermoza
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsCheater;

    public Question(int TextResId, boolean AnswerTrue, boolean IsCheater){
        mTextResId = TextResId;
        mAnswerTrue = AnswerTrue;
        mIsCheater = IsCheater;
    }



    public int getTextResId() {
        return mTextResId;

    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isCheater() {
        return mIsCheater;
    }

    public void setCheater(boolean cheater) {
        mIsCheater = cheater;
    }
}
