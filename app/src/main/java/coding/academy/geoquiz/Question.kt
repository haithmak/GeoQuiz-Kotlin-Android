package coding.academy.geoquiz

import androidx.annotation.StringRes



/*
1- We use the data keyword for all model classes in this
book. Doing so clearly indicates that the class is meant to
hold data. Also, the compiler does extra work for data
classes that makes your life easier, such as automatically
defining useful functions like equals(), hashCode(),
and a nicely formatted toString().

2- The @StringRes annotation is not required, but we
recommend you include it for two reasons:
First, the annotation helps the code inspector built into Android
Studio (named Lint) verify at compile time that usages of
the constructor provide a valid string resource ID. This
prevents runtime crashes where the constructor is used
with an invalid resource ID (such as an ID that points to
some resource other than a string).
Second, the annotation makes your code more readable for other
developers.

3-Why is textResId an Int and not a String? The
textResId variable will hold the resource ID (always an
Int) of the string resource for a question.
*/
data class Question(@StringRes val textResId: Int, val answer: Boolean , var answered: Int) {




}