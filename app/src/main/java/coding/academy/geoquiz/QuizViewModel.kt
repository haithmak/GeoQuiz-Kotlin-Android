package coding.academy.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    //The onCleared() function is called just before a
    //ViewModel is destroyed. This is a useful place to
    //perform any cleanup, such as un-observing a data
    //source.
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}