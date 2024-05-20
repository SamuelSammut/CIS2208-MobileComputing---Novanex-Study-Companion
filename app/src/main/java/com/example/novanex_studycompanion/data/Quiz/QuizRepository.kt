package com.example.novanex_studycompanion.data.Quiz

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

// Repository class to manage Quiz data operations
class QuizRepository(private val quizDao: QuizDao) {

    // Get quizzes for a specific subject
    fun getQuizzesForSubject(subjectTitle: String): LiveData<List<Quiz>> {
        return quizDao.getQuizzesForSubject(subjectTitle)
    }

    // Get all quizzes
    fun getAllQuizzes(): LiveData<List<Quiz>> {
        return quizDao.getAllQuizzes()
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun insert(quiz: Quiz) {
        quizDao.insert(quiz) // Insert a new quiz
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun update(quiz: Quiz) {
        quizDao.update(quiz) // Update an existing quiz
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun delete(quiz: Quiz) {
        quizDao.delete(quiz) // Delete a specific quiz
    }
}
