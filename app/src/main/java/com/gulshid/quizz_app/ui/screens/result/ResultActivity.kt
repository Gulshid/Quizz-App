package com.quizapp.ui.screens.result

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quizapp.data.model.QuizResultParcelable
import com.quizapp.databinding.ActivityResultBinding
import com.quizapp.ui.screens.home.HomeActivity
import com.quizapp.ui.screens.quiz.QuizActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }

    private lateinit var binding: ActivityResultBinding
    private var categoryId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getParcelableExtra<QuizResultParcelable>(EXTRA_RESULT)
        categoryId = result?.categoryId ?: ""

        result?.let { displayResult(it) } ?: finish()
        setupListeners()
    }

    private fun displayResult(result: QuizResultParcelable) {
        binding.tvCategoryName.text = "${result.categoryName} Quiz"
        binding.tvPercentage.text = "${result.percentage}%"
        binding.tvGrade.text = "Grade: ${result.grade}"

        binding.tvPerformanceMessage.text = when {
            result.percentage == 100 -> "🌟 Perfect Score!"
            result.percentage >= 80  -> "🎉 Great Job!"
            result.percentage >= 60  -> "👍 Good Effort!"
            result.percentage >= 50  -> "✅ Passed!"
            else                     -> "📚 Keep Practicing!"
        }

        val gradeColor = gradeColor(result.grade)
        binding.scoreCircleContainer.getChildAt(0)?.background?.setTint(gradeColor)

        binding.tvCorrectCount.text = result.correctAnswers.toString()
        binding.tvWrongCount.text = result.wrongAnswers.toString()
        binding.tvSkippedCount.text = result.skippedAnswers.toString()

        binding.tvScoreDetail.text = "${result.totalScore} / ${result.maxScore} pts"
        binding.tvTimeTaken.text = formatTime(result.timeTaken)
        binding.tvAccuracy.text = "${result.percentage}%"

        animateResults()
    }

    private fun gradeColor(grade: String): Int = when (grade) {
        "A+" -> Color.parseColor("#6C63FF")
        "A"  -> Color.parseColor("#2ECC71")
        "B"  -> Color.parseColor("#3498DB")
        "C"  -> Color.parseColor("#F39C12")
        "D"  -> Color.parseColor("#E67E22")
        else -> Color.parseColor("#E74C3C")
    }

    private fun formatTime(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        return if (minutes > 0) "${minutes}m ${seconds}s" else "${seconds}s"
    }

    private fun animateResults() {
        binding.scoreCircleContainer.apply {
            scaleX = 0f; scaleY = 0f
            animate().scaleX(1f).scaleY(1f).setDuration(500).setStartDelay(200)
                .setInterpolator(android.view.animation.OvershootInterpolator(1.5f)).start()
        }
        binding.btnPlayAgain.apply {
            alpha = 0f; animate().alpha(1f).setDuration(300).setStartDelay(700).start()
        }
        binding.btnHome.apply {
            alpha = 0f; animate().alpha(1f).setDuration(300).setStartDelay(800).start()
        }
    }

    private fun setupListeners() {
        binding.btnPlayAgain.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java).apply {
                putExtra(QuizActivity.EXTRA_CATEGORY_ID, categoryId)
            })
            finish()
        }
        binding.btnHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            })
            finish()
        }
    }
}
