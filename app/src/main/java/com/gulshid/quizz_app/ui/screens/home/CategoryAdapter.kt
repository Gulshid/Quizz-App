package com.quizapp.ui.screens.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quizapp.data.model.Difficulty
import com.quizapp.data.model.QuizCategory
import com.quizapp.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategoryClick: (QuizCategory) -> Unit
) : ListAdapter<QuizCategory, CategoryAdapter.CategoryViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<QuizCategory>() {
        override fun areItemsTheSame(a: QuizCategory, b: QuizCategory) = a.id == b.id
        override fun areContentsTheSame(a: QuizCategory, b: QuizCategory) = a == b
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: QuizCategory) = with(binding) {
            tvIcon.text = category.icon
            tvCategoryName.text = category.name
            tvDescription.text = category.description
            tvQuestionCount.text = "${category.questionCount} Questions"
            tvDifficulty.text = category.difficulty.name.lowercase().replaceFirstChar { it.uppercase() }

            // Apply category color
            val color = try { Color.parseColor(category.colorHex) } catch (e: Exception) { 0xFF6C63FF.toInt() }

            // Accent bar color
            accentBar.setBackgroundColor(color)

            // Icon background tint
            val alphaColor = Color.argb(30, Color.red(color), Color.green(color), Color.blue(color))
            iconBg.background?.let {
                iconBg.setBackgroundColor(alphaColor)
                iconBg.background = iconBg.context.getDrawable(com.quizapp.R.drawable.bg_circle)
                iconBg.background.setTint(alphaColor)
            }

            // Difficulty color
            val diffColor = when (category.difficulty) {
                Difficulty.EASY -> Color.parseColor("#2ECC71")
                Difficulty.MEDIUM -> Color.parseColor("#F39C12")
                Difficulty.HARD -> Color.parseColor("#E74C3C")
            }
            tvDifficulty.setTextColor(diffColor)

            cardRoot.setOnClickListener {
                it.animate().scaleX(0.96f).scaleY(0.96f).setDuration(80).withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).setDuration(80).start()
                    onCategoryClick(category)
                }.start()
            }
        }
    }
}
