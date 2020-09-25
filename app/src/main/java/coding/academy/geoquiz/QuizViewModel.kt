package coding.academy.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    var currentIndex = 0

     val questionBank = listOf(
        Question(R.string.question_australia, true,2),
        Question(R.string.question_oceans, true,2),
        Question(R.string.question_mideast, false,2),
        Question(R.string.question_africa, false,2),
        Question(R.string.question_americas, true,2),
        Question(R.string.question_asia, true,2))


    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int  get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {

        currentIndex = (questionBank.size + (currentIndex - 1)) % questionBank.size

        /*
        if (currentIndex==0){
            currentIndex =questionBank.size-1
        }else {
            currentIndex = (currentIndex - 1) % questionBank.size
        } */
    }


}