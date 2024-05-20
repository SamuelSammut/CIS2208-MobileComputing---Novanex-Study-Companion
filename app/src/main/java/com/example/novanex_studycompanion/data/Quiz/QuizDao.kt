package com.example.novanex_studycompanion.data.Quiz

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao // Data Access Object for Quiz entity
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quiz: Quiz) // Insert a new quiz, ignore if it conflicts

    @Update
    suspend fun update(quiz: Quiz) // Update an existing quiz

    @Query("DELETE FROM quizzes_table")
    suspend fun deleteAll() // Delete all quizzes from the table

    @Delete
    suspend fun delete(quiz: Quiz) // Delete a specific quiz

    @Query("SELECT * FROM quizzes_table WHERE subjectTitle = :subjectTitle ORDER BY id ASC")
    fun getQuizzesForSubject(subjectTitle: String): LiveData<List<Quiz>> // Get quizzes for a specific subject

    @Query("SELECT * FROM quizzes_table ORDER BY id ASC")
    fun getAllQuizzes(): LiveData<List<Quiz>> // Get all quizzes
}
