package com.example.novanex_studycompanion.data.Subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity representing a Subject in the database
@Entity(tableName = "subjects_table")
data class Subject(
    @PrimaryKey @ColumnInfo(name = "title") // Primary key is the title of the subject
    val title: String, // The title of the subject
    val examDate: String // The exam date for the subject
)
