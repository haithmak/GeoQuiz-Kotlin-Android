package coding.academy.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.ArrayList
import kotlin.random.Random

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var isCheater = false
    var score=0
    private val simpleQuestionBank= listOf(
        Question(R.string.questiom_E_Yemne, false,2),
        Question(R.string.question_E_Cairo, false,2),
        Question(R.string.question_E_Europe, true,2),
        Question(R.string.question_E_Istanbul, true,2),
        Question(R.string.question_E_Japan, false,2),
        Question(R.string.question_E_Turkey, false,2)

    )
    private val midQuestionBank= listOf(
        Question(R.string.question_M_seas, true,4),
        Question(R.string.question_M_city, true,4),
        Question(R.string.question_M_island, false,4),
        Question(R.string.question_M_mountain, false,4),
        Question(R.string.question_M_reserve, false,4),
        Question(R.string.question_M_waterFall, true,4)
    )


    val difficultQuestionBank = listOf(
        Question(R.string.question_australia, true,6),
        Question(R.string.question_oceans, true,6),
        Question(R.string.question_mideast, false,6),
        Question(R.string.question_africa, false,6),
        Question(R.string.question_americas, true,6),
        Question(R.string.question_asia, true,6)
    )


    var questionBank = listOf(
        simpleQuestionBank.random(),
        simpleQuestionBank.random(),
        midQuestionBank.random(),
        midQuestionBank.random(),
        difficultQuestionBank.random(),
        difficultQuestionBank.random()
    )


    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int  get() = questionBank[currentIndex].textResId
    var currentQuestionisAnswerd : Int = 0
        get() = questionBank[currentIndex].answered
        set(value) {
            field = value
        }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (questionBank.size + (currentIndex - 1)) % questionBank.size
        /*  if (currentIndex==0){
              currentIndex =questionBank.size-1
          }else {
              currentIndex = currentIndex - 1
          } */
    }

    fun showScore() : String
    {
        return "Score : $score  / 24"
    }




}