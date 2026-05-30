package com.gulshid.quizz_app.ui.screens.quiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.gulshid.quizz_app.R
import com.gulshid.quizz_app.data.model.Difficulty
import com.gulshid.quizz_app.data.model.Question
import com.gulshid.quizz_app.databinding.ActivityQuizBinding
import com.gulshid.quizz_app.databinding.ItemOptionBinding
import com.gulshid.quizz_app.ui.screens.result.ResultActivity
import com.gulshid.quizz_app.viewmodel.AnswerState
import com.gulshid.quizz_app.viewmodel.QuizState
import com.gulshid.quizz_app.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY_ID = "extra_category_id"
        const val EXTRA_CATEGORY_NAME = "extra_category_name"
    }

    private lateinit var binding: ActivityQuizBinding
    private val viewModel: QuizViewModel by viewModels()
    private val optionBindings = mutableListOf<ItemOptionBinding>()

    private val optionLetters = listOf("A", "B", "C", "D")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID) ?: run { finish(); return }

        setupBackPress()
        setupListeners()
        observeViewModel()

        viewModel.startQuiz(categoryId)
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = showQuitDialog()
        })
        binding.btnClose.setOnClickListener { showQuitDialog() }
    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            binding.btnNext.animate().scaleX(0.95f).scaleY(0.95f).setDuration(60).withEndAction {
                binding.btnNext.animate().scaleX(1f).scaleY(1f).setDuration(60).start()
                viewModel.nextQuestion()
            }.start()
        }
    }

    private fun observeViewModel() {
        viewModel.currentQuestion.observe(this) { question ->
            displayQuestion(question)
        }

        viewModel.currentQuestionIndex.observe(this) { index ->
            val total = viewModel.totalQuestions
            binding.tvQuestionCounter.text = "Question ${index + 1} of $total"
            binding.progressBar.apply {
                progress = ((index + 1) * 100) / maxOf(total, 1)
            }
        }

        viewModel.timeLeft.observe(this) { seconds ->
            binding.tvTimer.text = "${seconds}s"
            val maxTime = QuizViewModel.TIME_PER_QUESTION
            val progress = (seconds * 100) / maxTime
            binding.timerBar.setProgressCompat(progress, true)

            val color = when {
                seconds > 15 -> ContextCompat.getColor(this, R.color.timer_safe)
                seconds > 8  -> ContextCompat.getColor(this, R.color.timer_warning)
                else         -> ContextCompat.getColor(this, R.color.timer_danger)
            }
            binding.tvTimer.setTextColor(color)
            binding.timerBar.setIndicatorColor(color)

            if (seconds <= 5 && seconds > 0) {
                binding.tvTimer.animate().scaleX(1.15f).scaleY(1.15f).setDuration(200).withEndAction {
                    binding.tvTimer.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
                }.start()
            }
        }

        viewModel.score.observe(this) { score ->
            binding.tvScore.text = score.toString()
        }

        viewModel.answerState.observe(this) { state ->
            when (state) {
                AnswerState.CORRECT -> onAnswerRevealed(true)
                AnswerState.WRONG, AnswerState.TIMEOUT -> onAnswerRevealed(false)
                AnswerState.IDLE -> { /* waiting */ }
            }
        }

        viewModel.quizState.observe(this) { state ->
            if (state == QuizState.FINISHED) {
                binding.btnNext.text = "See Results 🎉"
                binding.btnNext.setOnClickListener { navigateToResult() }
            }
        }
    }

    private fun displayQuestion(question: Question) {
        // Animate question card in
        binding.cardQuestion.apply {
            alpha = 0f
            translationX = 80f
            animate().alpha(1f).translationX(0f).setDuration(300).start()
        }

        binding.tvCategoryBadge.text = question.category.uppercase()
        binding.tvQuestion.text = question.question
        binding.tvPointsBadge.text = "+${question.points} pts"

        // Explanation hidden
        binding.explanationContainer.visibility = View.GONE
        binding.btnNext.visibility = View.GONE

        // Build options
        binding.optionsContainer.removeAllViews()
        optionBindings.clear()

        question.options.forEachIndexed { index, optionText ->
            val optBinding = ItemOptionBinding.inflate(
                LayoutInflater.from(this), binding.optionsContainer, true
            )
            optBinding.tvOptionLetter.text = optionLetters[index]
            optBinding.tvOptionText.text = optionText
            optBinding.ivResultIcon.visibility = View.GONE

            // Animate stagger
            optBinding.optionRoot.apply {
                alpha = 0f
                translationY = 30f
                animate().alpha(1f).translationY(0f).setDuration(300).setStartDelay(index * 70L).start()
            }

            optBinding.optionRoot.setOnClickListener {
                viewModel.selectAnswer(index)
            }

            optionBindings.add(optBinding)
        }
    }

    private fun onAnswerRevealed(isCorrect: Boolean) {
        val question = viewModel.currentQuestion.value ?: return
        val selectedIndex = viewModel.selectedAnswer.value ?: -1

        // Update each option appearance
        optionBindings.forEachIndexed { index, optBinding ->
            optBinding.optionRoot.isClickable = false
            optBinding.optionRoot.isFocusable = false

            when {
                index == question.correctAnswerIndex -> {
                    optBinding.optionRoot.background = ContextCompat.getDrawable(this, R.drawable.bg_option_correct)
                    optBinding.tvOptionLetter.setTextColor(ContextCompat.getColor(this, R.color.correct))
                    optBinding.tvOptionText.setTextColor(ContextCompat.getColor(this, R.color.correct))
                    optBinding.ivResultIcon.visibility = View.VISIBLE
                    optBinding.ivResultIcon.setImageResource(android.R.drawable.checkbox_on_background)
                    optBinding.ivResultIcon.setColorFilter(ContextCompat.getColor(this, R.color.correct))

                    if (index == selectedIndex) {
                        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
                        optBinding.optionRoot.startAnimation(bounce)
                    }
                }
                index == selectedIndex && index != question.correctAnswerIndex -> {
                    optBinding.optionRoot.background = ContextCompat.getDrawable(this, R.drawable.bg_option_wrong)
                    optBinding.tvOptionLetter.setTextColor(ContextCompat.getColor(this, R.color.wrong))
                    optBinding.tvOptionText.setTextColor(ContextCompat.getColor(this, R.color.wrong))
                    optBinding.ivResultIcon.visibility = View.VISIBLE
                    optBinding.ivResultIcon.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
                    optBinding.ivResultIcon.setColorFilter(ContextCompat.getColor(this, R.color.wrong))

                    // Shake animation
                    optBinding.optionRoot.animate()
                        .translationX(-12f).setDuration(60).withEndAction {
                            optBinding.optionRoot.animate()
                                .translationX(12f).setDuration(60).withEndAction {
                                    optBinding.optionRoot.animate()
                                        .translationX(-8f).setDuration(50).withEndAction {
                                            optBinding.optionRoot.animate()
                                                .translationX(0f).setDuration(50).start()
                                        }.start()
                                }.start()
                        }.start()
                }
                else -> {
                    optBinding.optionRoot.alpha = 0.5f
                }
            }
        }

        // Show explanation
        binding.tvExplanation.text = question.explanation
        binding.explanationContainer.visibility = View.VISIBLE
        binding.explanationContainer.apply {
            alpha = 0f
            animate().alpha(1f).setDuration(300).setStartDelay(200).start()
        }

        // Show next button
        binding.btnNext.visibility = View.VISIBLE
        binding.btnNext.apply {
            alpha = 0f
            animate().alpha(1f).setDuration(300).setStartDelay(350).start()
        }

        val isLastQuestion = (viewModel.currentQuestionIndex.value ?: 0) >= viewModel.totalQuestions - 1
        binding.btnNext.text = if (isLastQuestion) "See Results 🎉" else "Next Question →"
    }

    private fun navigateToResult() {
        val result = viewModel.quizResult.value ?: return
        val intent = Intent(this, ResultActivity::class.java).apply {

            putExtra(ResultActivity.EXTRA_RESULT, com.gulshid.quizz_app.data.model.QuizResultParcelable.from(result))
        }
        startActivity(intent)
        finish()
    }

    private fun showQuitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Quit Quiz")
            .setMessage("Are you sure you want to quit? Your progress will be lost.")
            .setPositiveButton("Quit") { _, _ -> finish() }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
