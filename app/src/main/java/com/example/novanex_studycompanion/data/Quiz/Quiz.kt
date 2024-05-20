package com.example.novanex_studycompanion.data.Quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity representing a Quiz in the database
@Entity(tableName = "quizzes_table")
data class Quiz(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primary key with auto-increment
    val subjectTitle: String, // The title of the subject this quiz belongs to
    val question: String, // The quiz question
    val answer1: String, // First answer option
    val answer2: String, // Second answer option
    val answer3: String, // Third answer option
    val answer4: String, // Fourth answer option
    val correctAnswerIndex: Int, // Index of the correct answer (0-3)
    var bestScore: Int = 0 // Best score achieved on this quiz
)
