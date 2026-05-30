package com.quizapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Parcelable wrappers for intent passing

@Parcelize
data class QuizResultParcelable(
    val sessionId: String,
    val categoryId: String,
    val categoryName: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedAnswers: Int,
    val totalScore: Int,
    val maxScore: Int,
    val timeTaken: Long,
    val timestamp: Long,
    val percentage: Int,
    val grade: String,
    val isPassed: Boolean
) : Parcelable {
    companion object {
        fun from(result: QuizResult) = QuizResultParcelable(
            sessionId = result.sessionId,
            categoryId = result.categoryId,
            categoryName = result.categoryName,
            totalQuestions = result.totalQuestions,
            correctAnswers = result.correctAnswers,
            wrongAnswers = result.wrongAnswers,
            skippedAnswers = result.skippedAnswers,
            totalScore = result.totalScore,
            maxScore = result.maxScore,
            timeTaken = result.timeTaken,
            timestamp = result.timestamp,
            percentage = result.percentage,
            grade = result.grade,
            isPassed = result.isPassed
        )
    }
}
