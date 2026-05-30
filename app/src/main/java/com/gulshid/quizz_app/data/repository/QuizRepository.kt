package com.quizapp.data.repository

import com.quizapp.data.local.QuestionDataSource
import com.quizapp.data.local.QuizDatabase
import com.quizapp.data.local.QuizResultEntity
import com.quizapp.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepository @Inject constructor(
    private val database: QuizDatabase
) {
    private val dao = database.quizResultDao()

    fun getCategories(): List<QuizCategory> = QuestionDataSource.categories

    fun getQuestionsForCategory(categoryId: String): List<Question> =
        QuestionDataSource.getQuestionsForCategory(categoryId)

    fun createQuizSession(categoryId: String): QuizSession {
        val category = QuestionDataSource.categories.first { it.id == categoryId }
        val questions = QuestionDataSource.getQuestionsForCategory(categoryId)
        return QuizSession(
            categoryId = categoryId,
            categoryName = category.name,
            questions = questions,
            totalQuestions = questions.size
        )
    }

    suspend fun saveResult(result: QuizResult) {
        val entity = QuizResultEntity(
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
            answeredQuestionsJson = "",
            timestamp = result.timestamp
        )
        dao.insertResult(entity)
    }

    fun getAllResults(): Flow<List<QuizResult>> = dao.getAllResults().map { entities ->
        entities.map { it.toQuizResult() }
    }

    suspend fun getUserStats(): UserStats {
        val total = dao.getTotalQuizzesTaken()
        val correct = dao.getTotalCorrectAnswers() ?: 0
        val questions = dao.getTotalQuestions() ?: 0
        val best = dao.getBestScore() ?: 0
        val totalScore = dao.getTotalScore() ?: 0
        return UserStats(
            totalQuizzesTaken = total,
            totalCorrectAnswers = correct,
            totalQuestions = questions,
            bestScore = best,
            totalScore = totalScore,
            averageScore = if (total > 0) totalScore.toDouble() / total else 0.0
        )
    }

    private fun QuizResultEntity.toQuizResult() = QuizResult(
        sessionId = sessionId,
        categoryId = categoryId,
        categoryName = categoryName,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        skippedAnswers = skippedAnswers,
        totalScore = totalScore,
        maxScore = maxScore,
        timeTaken = timeTaken,
        answeredQuestions = emptyList(),
        timestamp = timestamp
    )
}
