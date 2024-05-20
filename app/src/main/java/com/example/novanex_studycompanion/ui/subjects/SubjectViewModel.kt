package com.example.novanex_studycompanion.ui.subjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.novanex_studycompanion.data.Subject.Subject
import com.example.novanex_studycompanion.data.Subject.SubjectRepository
import com.example.novanex_studycompanion.data.Subject.SubjectRoomDatabase
import kotlinx.coroutines.launch

// ViewModel for managing subject data and operations
class SubjectViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SubjectRepository  // Repository for interacting with the database
    val allSubjects: LiveData<List<Subject>>  // LiveData list of all subjects

    init {
        // Initialize the DAO and repository
        val subjectsDao = SubjectRoomDatabase.getDatabase(application).subjectDao()
        repository = SubjectRepository(subjectsDao)
        allSubjects = repository.allSubjects  // Get all subjects from the repository
    }

    // Delete a subject using a coroutine
    fun deleteSubject(subject: Subject) = viewModelScope.launch {
        repository.delete(subject)
    }

    // Insert a subject using a coroutine
    fun insert(subject: Subject) = viewModelScope.launch {
        repository.insert(subject)
    }

    // Add a new subject with the given title and exam date
    fun addNewSubject(title: String, examDate: String) {
        val newSubject = Subject(title = title, examDate = examDate)
        insert(newSubject)
    }
}
