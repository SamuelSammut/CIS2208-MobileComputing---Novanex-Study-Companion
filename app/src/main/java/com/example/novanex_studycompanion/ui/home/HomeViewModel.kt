package com.example.novanex_studycompanion.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.novanex_studycompanion.data.Quiz.Quiz
import com.example.novanex_studycompanion.data.Quiz.QuizRepository
import com.example.novanex_studycompanion.data.Subject.Subject
import com.example.novanex_studycompanion.data.Subject.SubjectRepository
import com.example.novanex_studycompanion.data.Subject.SubjectRoomDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

// ViewModel for the HomeFragment
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    // Data class to represent an exam item
    data class ExamItem(
        val title: String,
        val date: String,
        val daysLeft: Int,
        val preparationProgress: Int
    )

    private val subjectRepository: SubjectRepository
    private val quizRepository: QuizRepository

    // Initialize the repositories and DAOs
    init {
        val subjectDao = SubjectRoomDatabase.getDatabase(application).subjectDao()
        val quizDao = SubjectRoomDatabase.getDatabase(application).quizDao()
        subjectRepository = SubjectRepository(subjectDao)
        quizRepository = QuizRepository(quizDao)
    }

    private val _examItems = MediatorLiveData<List<ExamItem>>()
    val examItems: LiveData<List<ExamItem>> = _examItems

    init {
        loadExamItems()
    }

    // Load exam items from the repositories
    private fun loadExamItems() {
        val allSubjects = subjectRepository.allSubjects
        val allQuizzes = quizRepository.getAllQuizzes()

        _examItems.addSource(allSubjects) { subjects ->
            val quizzes = allQuizzes.value ?: emptyList()
            _examItems.value = combineSubjectsAndQuizzes(subjects, quizzes)
        }

        _examItems.addSource(allQuizzes) { quizzes ->
            val subjects = allSubjects.value ?: emptyList()
            _examItems.value = combineSubjectsAndQuizzes(subjects, quizzes)
        }
    }

    // Combine subjects and quizzes to create a list of ExamItem
    private fun combineSubjectsAndQuizzes(subjects: List<Subject>, quizzes: List<Quiz>): List<ExamItem> {
        return subjects.map { subject ->
            val quizzesForSubject = quizzes.filter { it.subjectTitle == subject.title }
            val bestScore = quizzesForSubject.maxOfOrNull { it.bestScore } ?: 0
            val totalQuestions = quizzesForSubject.size
            val maxQuestions = totalQuestions // Total number of questions across all quizzes for the subject

            val progress = if (maxQuestions > 0) {
                (bestScore.toDouble() / maxQuestions * 100).roundToInt()
            } else {
                0
            }

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val examDate = dateFormat.parse(subject.examDate)
            val currentDate = Date()
            val diff = examDate.time - currentDate.time
            val daysLeft = (diff / (1000 * 60 * 60 * 24)).toInt()

            ExamItem(
                title = subject.title,
                date = subject.examDate,
                daysLeft = daysLeft,
                preparationProgress = progress
            )
        }.sortedBy { it.date }
    }
}
