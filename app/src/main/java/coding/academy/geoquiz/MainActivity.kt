package coding.academy.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.GRAY
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Duration

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var  questionTextView: TextView
    private lateinit var scoreTextView: TextView

    //Using by lazy allows you to make the
    //quizViewModel property a val instead of a var. This
    //is great, because you only need (and want) to grab and
    //store the QuizViewModel when the activity instance is
    //created – so quizViewModel should only be assigned a
    //value one time.
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG ,"onCreate() called"  )
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

        quizViewModel.currentIndex = currentIndex
        trueButton = findViewById(R.id.t_Button)
        falseButton = findViewById(R.id.f_Button)
        nextButton = findViewById(R.id.next_Button)
        prevButton= findViewById(R.id.prev_Button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        scoreTextView = findViewById(R.id.score_text_view)
        updateQuestion()

        trueButton.setOnClickListener{
            checkAnswer(true)
            updateQuestion()
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
            updateQuestion()
        }

        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
        }
        questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
           // val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)

        }


    }



    private fun updateQuestion() {

        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        scoreTextView.setText(quizViewModel.showScore())

        if(quizViewModel.currentQuestionisAnswerd == 0 ) {
            buttonEnabled(false)
        }
        else{
            buttonEnabled(true)
        }

    /*  if(showFinalScore() != 0){
          var scorePercent =Math.round((showFinalScore().toFloat() /   quizViewModel.questionBank.size.toFloat()) * 100.0f);
            Toast.makeText(this, "Your Final Score = " + showFinalScore() +"\n "
                    +scorePercent + "%", Toast.LENGTH_SHORT)
                .show()
        }

     */


    }

    private fun checkAnswer(userAnswer: Boolean) {

        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when {
            quizViewModel.isCheater -> {
                quizViewModel.score += 0
                quizViewModel.questionBank[quizViewModel.currentIndex].answered =  0
                R.string.judgment_toast
            }
            userAnswer == correctAnswer -> {
                quizViewModel.score += quizViewModel.questionBank[quizViewModel.currentIndex].answered
                quizViewModel.questionBank[quizViewModel.currentIndex].answered =  0
                R.string.correct_msg
            }
            else -> {
                quizViewModel.score += 0
                quizViewModel.questionBank[quizViewModel.currentIndex].answered =  0
                R.string.incorrect_toast
            }


        }

        Toast.makeText(this, messageResId  , Toast.LENGTH_SHORT).show()
        updateQuestion()


    }



    private fun buttonEnabled(BState: Boolean) {

        trueButton?.isEnabled = BState
        falseButton?.isEnabled = BState
        cheatButton?.isEnabled = BState


        when (BState) {
            false -> {
                trueButton.setBackgroundColor(getResources().getColor(R.color.gray))
                falseButton.setBackgroundColor(getResources().getColor(R.color.gray))
                cheatButton.setBackgroundColor(getResources().getColor(R.color.gray))
            }
            true -> {
                trueButton.setBackgroundColor(getResources().getColor(R.color.green))
                falseButton.setBackgroundColor(getResources().getColor(R.color.red))
                cheatButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary))
            }


        }
    }


    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
                 checkAnswer(quizViewModel.isCheater)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.d(TAG, "onSaveInstanceState  "  + quizViewModel.currentIndex)
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)

    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG ,"onStart() called "  )
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG ,"onPause() called "  )
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG ,"onRestart() called "  )
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG ,"onResume() called " )
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG ,"onStop() called "   )

        if(this.isFinishing==true)
        {
            //the activity is being destroyed because the user finished the activity
            Log.d(TAG ,"isFinishing = True" )
        }else{
            //the activity is being destroyed by the system because of a configuration change.
            Log.d(TAG ,"isFinishing = False " )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG ,"onDestroy() called" )
    }


}