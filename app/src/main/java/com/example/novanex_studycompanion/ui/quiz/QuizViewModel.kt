package com.example.novanex_studycompanion.ui.quiz

import android.app.Application
import androidx.lifecycle.*
import com.example.novanex_studycompanion.data.Quiz.GroupedQuiz
import com.example.novanex_studycompanion.data.Quiz.Quiz
import com.example.novanex_studycompanion.data.Quiz.QuizRepository
import com.example.novanex_studycompanion.data.Subject.SubjectRoomDatabase
import kotlinx.coroutines.launch

// ViewModel for managing quiz data
class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QuizRepository

    init {
        val quizDao = SubjectRoomDatabase.getDatabase(application).quizDao()
        repository = QuizRepository(quizDao)
    }

    // Get quizzes for a specific subject
    fun getQuizzesForSubject(subjectTitle: String): LiveData<List<Quiz>> {
        return repository.getQuizzesForSubject(subjectTitle)
    }

    // Get all quizzes grouped by subject
    fun getAllQuizzes(): LiveData<List<GroupedQuiz>> {
        return repository.getAllQuizzes().map { quizzes ->
            quizzes.groupBy { it.subjectTitle }.map { (subject, quizzes) ->
                GroupedQuiz(subjectTitle = subject, questions = quizzes)
            }
        }
    }

    // Insert a new quiz into the database
    fun insert(quiz: Quiz) = viewModelScope.launch {
        repository.insert(quiz)
    }

    // Update an existing quiz in the database
    fun update(quiz: Quiz) = viewModelScope.launch {
        repository.update(quiz)
    }

    // Delete a quiz from the database
    fun delete(quiz: Quiz) = viewModelScope.launch {
        repository.delete(quiz)
    }
}
