package com.gulshid.quizz_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: String,
    val difficulty: Difficulty,
    val points: Int = 10
) : Parcelable

@Parcelize
data class QuizCategory(
    val id: String,
    val name: String,
    val icon: String,
    val description: String,
    val questionCount: Int,
    val colorHex: String,
    val difficulty: Difficulty
) : Parcelable

enum class Difficulty {
    EASY, MEDIUM, HARD
}

data class QuizSession(
    val categoryId: String,
    val categoryName: String,
    val questions: List<Question>,
    val totalQuestions: Int,
    val timePerQuestion: Int = 30 // seconds
)

data class QuizResult(
    val sessionId: String = java.util.UUID.randomUUID().toString(),
    val categoryId: String,
    val categoryName: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedAnswers: Int,
    val totalScore: Int,
    val maxScore: Int,
    val timeTaken: Long, // milliseconds
    val answeredQuestions: List<AnsweredQuestion>,
    val timestamp: Long = System.currentTimeMillis()
) {
    val percentage: Int get() = if (totalQuestions > 0) (correctAnswers * 100) / totalQuestions else 0
    val grade: String get() = when {
        percentage >= 90 -> "A+"
        percentage >= 80 -> "A"
        percentage >= 70 -> "B"
        percentage >= 60 -> "C"
        percentage >= 50 -> "D"
        else -> "F"
    }
    val isPassed: Boolean get() = percentage >= 50
}

data class AnsweredQuestion(
    val question: Question,
    val selectedAnswerIndex: Int, // -1 if skipped
    val isCorrect: Boolean,
    val timeTaken: Long
)

data class UserStats(
    val totalQuizzesTaken: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val bestScore: Int = 0,
    val totalScore: Int = 0,
    val averageScore: Double = 0.0,
    val streak: Int = 0
)
