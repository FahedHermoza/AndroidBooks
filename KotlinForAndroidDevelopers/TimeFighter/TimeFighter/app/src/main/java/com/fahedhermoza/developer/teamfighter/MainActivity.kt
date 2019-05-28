package com.fahedhermoza.developer.teamfighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var score = 0
    var gameStarted = false
    lateinit var countDownTimer: CountDownTimer
    val initialcountDown: Long = 60000
    val countDownInterval: Long = 1000
    val TAG = MainActivity::class.java.simpleName
    internal var timeLeftOnTimer: Long = 60000

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreated called. Store is: $score")     //Interpolación de cadenas

        if(savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        }else{
            resetGame()
        }

        buttonCaress.setOnClickListener { view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementStore() }

        buttonIntroduce.setOnClickListener { view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementStore() }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(SCORE_KEY,score)
        outState?.putLong(TIME_LEFT_KEY,timeLeftOnTimer)
        countDownTimer.cancel()
        Log.d(TAG,"onSaveInstanceState: Saving score: $score & Time Left: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy callled.")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_about)
            showInfo()
        return true
    }

    private fun showInfo(){
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

    private fun restoreGame(){
        textViewGameStore.text = getString(R.string.your_score, score.toString())
        val restoreTime = timeLeftOnTimer/1000
        textViewTimeLeft.text = getString(R.string.time_left, restoreTime.toString())

        countDownTimer = object: CountDownTimer(timeLeftOnTimer, countDownInterval){
            override fun onTick(millisUntilFinish: Long) {
                timeLeftOnTimer = millisUntilFinish
                val timeleft = millisUntilFinish / 1000
                textViewTimeLeft.text = getString(R.string.time_left,timeleft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    private fun resetGame(){
        score = 0
        textViewGameStore.text = getString(R.string.your_score,score.toString())
        val initialTimeLeft = initialcountDown /1000
        textViewTimeLeft.text = getString(R.string.time_left,initialTimeLeft.toString())

        countDownTimer = object: CountDownTimer(initialcountDown, countDownInterval){
            override fun onTick(millisUntilFinish: Long) {
                timeLeftOnTimer = millisUntilFinish
                val timeleft = millisUntilFinish / 1000
                textViewTimeLeft.text = getString(R.string.time_left,timeleft.toString())
            }
            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }

    private fun incrementStore() {
        if(!gameStarted)
            startGame()
        score = score + 1
        val newScore = getString(R.string.your_score,score.toString())
        textViewGameStore.text = newScore
        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        textViewGameStore.startAnimation(blinkAnimation)
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame(){
        if(score<500)
            Toast.makeText(this,getString(R.string.game_over_message_noob, score.toString()),Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this,getString(R.string.game_over_message_expert, score.toString()),Toast.LENGTH_LONG).show()
        resetGame()
    }


}
