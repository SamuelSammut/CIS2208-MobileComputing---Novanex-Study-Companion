package com.example.novanex_studycompanion.data.Quiz

// Data class to group quizzes by subject title
data class GroupedQuiz(
    val subjectTitle: String, // The title of the subject
    val questions: List<Quiz> // A list of quizzes related to the subject
)
