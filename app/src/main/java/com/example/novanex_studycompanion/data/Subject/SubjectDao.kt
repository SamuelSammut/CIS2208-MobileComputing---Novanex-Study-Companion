package com.example.novanex_studycompanion.data.Subject

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao // Data Access Object for Subject entity
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subject: Subject) // Insert a new subject, ignore if it conflicts

    @Query("DELETE FROM subjects_table")
    suspend fun deleteAll() // Delete all subjects from the table

    @Delete
    suspend fun delete(subject: Subject) // Delete a specific subject

    @Query("SELECT * FROM subjects_table ORDER BY examDate ASC")
    fun getAllSubjects(): LiveData<List<Subject>> // Get all subjects ordered by exam date
}
