package com.quizapp.ui.screens.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.quizapp.data.model.QuizCategory
import com.quizapp.databinding.ActivityHomeBinding
import com.quizapp.ui.screens.quiz.QuizActivity
import com.quizapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadStats()
    }

    private fun setupUI() {
        categoryAdapter = CategoryAdapter { category -> openQuiz(category) }
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(this@HomeActivity, 1)
            setHasFixedSize(false)
        }
        animateHeader()
    }

    private fun animateHeader() {
        binding.headerContainer.apply {
            alpha = 0f
            translationY = -40f
            animate().alpha(1f).translationY(0f).setDuration(500).setStartDelay(100).start()
        }
    }

    private fun observeViewModel() {
        viewModel.categories.observe(this) { categories ->
            categoryAdapter.submitList(categories)
            animateCategoriesList()
        }

        viewModel.userStats.observe(this) { stats ->
            binding.tvStatQuizzes.text = stats.totalQuizzesTaken.toString()
            binding.tvStatBest.text = stats.bestScore.toString()
            val accuracy = if (stats.totalQuestions > 0)
                ((stats.totalCorrectAnswers * 100.0) / stats.totalQuestions).roundToInt()
            else 0
            binding.tvStatAccuracy.text = "$accuracy%"
        }
    }

    private fun animateCategoriesList() {
        binding.rvCategories.post {
            val manager = binding.rvCategories.layoutManager as? GridLayoutManager ?: return@post
            for (i in 0 until manager.childCount) {
                manager.getChildAt(i)?.let { child ->
                    child.alpha = 0f
                    child.translationY = 60f
                    child.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(400)
                        .setStartDelay(i * 80L)
                        .start()
                }
            }
        }
    }

    private fun openQuiz(category: QuizCategory) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(QuizActivity.EXTRA_CATEGORY_ID, category.id)
            putExtra(QuizActivity.EXTRA_CATEGORY_NAME, category.name)
        }
        startActivity(intent)
    }
}
