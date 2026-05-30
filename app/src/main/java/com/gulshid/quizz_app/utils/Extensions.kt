package com.quizapp.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.quizapp.R
import com.quizapp.data.model.Difficulty

fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun Float.dpToPx(context: Context): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

fun Difficulty.color(context: Context): Int = when (this) {
    Difficulty.EASY   -> ContextCompat.getColor(context, R.color.correct)
    Difficulty.MEDIUM -> ContextCompat.getColor(context, R.color.warning)
    Difficulty.HARD   -> ContextCompat.getColor(context, R.color.wrong)
}

fun Difficulty.label(): String = when (this) {
    Difficulty.EASY   -> "Easy"
    Difficulty.MEDIUM -> "Medium"
    Difficulty.HARD   -> "Hard"
}

fun Long.toFormattedTime(): String {
    val seconds = this / 1000
    val minutes = seconds / 60
    val secs = seconds % 60
    return if (minutes > 0) "${minutes}m ${secs}s" else "${secs}s"
}

fun Int.toPercentageString(): String = "$this%"
