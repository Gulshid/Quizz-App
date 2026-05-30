package com.gulshid.quizz_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulshid.quizz_app.data.model.*
import com.gulshid.quizz_app.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── Home ViewModel ───────────────────────────────────────────────────────────

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<QuizCategory>>()
    val categories: LiveData<List<QuizCategory>> = _categories

    private val _userStats = MutableLiveData<UserStats>()
    val userStats: LiveData<UserStats> = _userStats

    init {
        loadCategories()
        loadStats()
    }

    private fun loadCategories() {
        _categories.value = repository.getCategories()
    }

    fun loadStats() {
        viewModelScope.launch {
            _userStats.value = repository.getUserStats()
        }
    }
}

// ─── Quiz ViewModel ───────────────────────────────────────────────────────────

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    companion object {
        const val TIME_PER_QUESTION = 30 // seconds
    }

    private var quizSession: QuizSession? = null
    private val answeredQuestions = mutableListOf<AnsweredQuestion>()
    private var questionStartTime = 0L
    private var timerJob: Job? = null

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question> = _currentQuestion

    private val _timeLeft = MutableLiveData(TIME_PER_QUESTION)
    val timeLeft: LiveData<Int> = _timeLeft

    private val _selectedAnswer = MutableLiveData<Int?>()
    val selectedAnswer: LiveData<Int?> = _selectedAnswer

    private val _answerState = MutableLiveData<AnswerState>(AnswerState.IDLE)
    val answerState: LiveData<AnswerState> = _answerState

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _correctCount = MutableLiveData(0)
    val correctCount: LiveData<Int> = _correctCount

    private val _quizState = MutableLiveData<QuizState>(QuizState.LOADING)
    val quizState: LiveData<QuizState> = _quizState

    private val _quizResult = MutableLiveData<QuizResult?>()
    val quizResult: LiveData<QuizResult?> = _quizResult

    val totalQuestions get() = quizSession?.totalQuestions ?: 0
    val progress get() = ((_currentQuestionIndex.value ?: 0) * 100) / maxOf(totalQuestions, 1)

    fun startQuiz(categoryId: String) {
        viewModelScope.launch {
            quizSession = repository.createQuizSession(categoryId)
            _quizState.value = QuizState.RUNNING
            loadCurrentQuestion()
        }
    }

    private fun loadCurrentQuestion() {
        val session = quizSession ?: return
        val index = _currentQuestionIndex.value ?: 0
        if (index < session.questions.size) {
            _currentQuestion.value = session.questions[index]
            _selectedAnswer.value = null
            _answerState.value = AnswerState.IDLE
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        questionStartTime = System.currentTimeMillis()
        _timeLeft.value = TIME_PER_QUESTION
        timerJob = viewModelScope.launch {
            for (i in TIME_PER_QUESTION downTo 0) {
                _timeLeft.value = i
                if (i == 0) {
                    onTimeOut()
                    break
                }
                delay(1000L)
            }
        }
    }

    private fun onTimeOut() {
        if (_answerState.value == AnswerState.IDLE) {
            val question = _currentQuestion.value ?: return
            answeredQuestions.add(
                AnsweredQuestion(
                    question = question,
                    selectedAnswerIndex = -1,
                    isCorrect = false,
                    timeTaken = System.currentTimeMillis() - questionStartTime
                )
            )
            _answerState.value = AnswerState.TIMEOUT
        }
    }

    fun selectAnswer(index: Int) {
        if (_answerState.value != AnswerState.IDLE) return
        timerJob?.cancel()
        val question = _currentQuestion.value ?: return
        val isCorrect = index == question.correctAnswerIndex
        _selectedAnswer.value = index
        _answerState.value = if (isCorrect) AnswerState.CORRECT else AnswerState.WRONG
        if (isCorrect) {
            _score.value = (_score.value ?: 0) + question.points
            _correctCount.value = (_correctCount.value ?: 0) + 1
        }
        answeredQuestions.add(
            AnsweredQuestion(
                question = question,
                selectedAnswerIndex = index,
                isCorrect = isCorrect,
                timeTaken = System.currentTimeMillis() - questionStartTime
            )
        )
    }

    fun nextQuestion() {
        val session = quizSession ?: return
        val nextIndex = (_currentQuestionIndex.value ?: 0) + 1
        if (nextIndex >= session.questions.size) {
            finishQuiz()
        } else {
            _currentQuestionIndex.value = nextIndex
            loadCurrentQuestion()
        }
    }

    private fun finishQuiz() {
        timerJob?.cancel()
        val session = quizSession ?: return
        val correct = _correctCount.value ?: 0
        val wrong = answeredQuestions.count { !it.isCorrect && it.selectedAnswerIndex != -1 }
        val skipped = answeredQuestions.count { it.selectedAnswerIndex == -1 }
        val maxScore = session.questions.sumOf { it.points }
        val result = QuizResult(
            categoryId = session.categoryId,
            categoryName = session.categoryName,
            totalQuestions = session.totalQuestions,
            correctAnswers = correct,
            wrongAnswers = wrong,
            skippedAnswers = skipped,
            totalScore = _score.value ?: 0,
            maxScore = maxScore,
            timeTaken = answeredQuestions.sumOf { it.timeTaken },
            answeredQuestions = answeredQuestions.toList()
        )
        _quizResult.value = result
        _quizState.value = QuizState.FINISHED
        viewModelScope.launch { repository.saveResult(result) }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

enum class AnswerState { IDLE, CORRECT, WRONG, TIMEOUT }
enum class QuizState { LOADING, RUNNING, FINISHED }
