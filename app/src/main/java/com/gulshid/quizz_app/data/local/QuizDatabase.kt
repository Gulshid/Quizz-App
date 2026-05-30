package com.quizapp.data.local

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quizapp.data.model.AnsweredQuestion
import kotlinx.coroutines.flow.Flow

// ─── Entity ───────────────────────────────────────────────────────────────────

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey val sessionId: String,
    val categoryId: String,
    val categoryName: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedAnswers: Int,
    val totalScore: Int,
    val maxScore: Int,
    val timeTaken: Long,
    val answeredQuestionsJson: String,
    val timestamp: Long
)

// ─── Type Converters ──────────────────────────────────────────────────────────

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromAnsweredQuestions(value: List<AnsweredQuestion>): String =
        gson.toJson(value)

    @TypeConverter
    fun toAnsweredQuestions(value: String): List<AnsweredQuestion> {
        val type = object : TypeToken<List<AnsweredQuestion>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }
}

// ─── DAO ──────────────────────────────────────────────────────────────────────

@Dao
interface QuizResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: QuizResultEntity)

    @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<QuizResultEntity>>

    @Query("SELECT * FROM quiz_results WHERE categoryId = :categoryId ORDER BY timestamp DESC")
    fun getResultsByCategory(categoryId: String): Flow<List<QuizResultEntity>>

    @Query("SELECT COUNT(*) FROM quiz_results")
    suspend fun getTotalQuizzesTaken(): Int

    @Query("SELECT SUM(correctAnswers) FROM quiz_results")
    suspend fun getTotalCorrectAnswers(): Int

    @Query("SELECT SUM(totalQuestions) FROM quiz_results")
    suspend fun getTotalQuestions(): Int

    @Query("SELECT MAX(totalScore) FROM quiz_results")
    suspend fun getBestScore(): Int

    @Query("SELECT SUM(totalScore) FROM quiz_results")
    suspend fun getTotalScore(): Int

    @Query("DELETE FROM quiz_results")
    suspend fun clearAll()
}

// ─── Database ─────────────────────────────────────────────────────────────────

@Database(
    entities = [QuizResultEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        const val DATABASE_NAME = "quiz_database"
    }
}
