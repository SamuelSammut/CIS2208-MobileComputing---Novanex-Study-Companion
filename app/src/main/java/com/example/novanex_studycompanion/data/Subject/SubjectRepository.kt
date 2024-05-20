package com.example.novanex_studycompanion.data.Subject

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

// Repository class to manage Subject data operations
class SubjectRepository(private val subjectDao: SubjectDao) {
    val allSubjects: LiveData<List<Subject>> = subjectDao.getAllSubjects() // Get all subjects

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(subject: Subject) {
        subjectDao.insert(subject) // Insert a new subject
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(subject: Subject) {
        subjectDao.delete(subject) // Delete a specific subject
    }
}
