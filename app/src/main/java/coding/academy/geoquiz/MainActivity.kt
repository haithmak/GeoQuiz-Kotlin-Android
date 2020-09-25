package coding.academy.geoquiz

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

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView


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
        questionTextView = findViewById(R.id.question_text_view)

        updateQuestion()

        trueButton.setOnClickListener{
            checkAnswer(true)
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
        }

        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }

        questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
        }


    }



    private fun updateQuestion() {
        //val questionTextResId = questionBank[currentIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        question_text_view.setText(questionTextResId)

        if(quizViewModel.questionBank[quizViewModel.currentIndex].answered <= 1){
            trueButton?.isEnabled = false
            falseButton?.isEnabled = false
        }
        else{
            trueButton?.isEnabled = true
            falseButton?.isEnabled = true
        }

      if(showFinalScore() != 0){
          var scorePercent =Math.round((showFinalScore().toFloat() /   quizViewModel.questionBank.size.toFloat()) * 100.0f);
            Toast.makeText(this, "Your Final Score = " + showFinalScore() +"\n "
                    +scorePercent + "%", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun checkAnswer(userAnswer: Boolean) {
       // val correctAnswer = questionBank[currentIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.questionBank[quizViewModel.currentIndex].answered = 1
            R.string.correct_msg
        } else {
            quizViewModel.questionBank[quizViewModel.currentIndex].answered = 0
            R.string.inco_msg
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        trueButton?.isEnabled = false
        falseButton?.isEnabled = false

    }


    private fun showFinalScore() : Int
    {
        var mScore = 0;
        for ( n in quizViewModel.questionBank )
        {
            if (n.answered==2) {
                mScore = 0;
                break
            }
            else {
                mScore+= n.answered
            }
        }
        return mScore ;
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