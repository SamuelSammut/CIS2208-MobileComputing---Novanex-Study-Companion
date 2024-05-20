package com.example.novanex_studycompanion.data.Subject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.novanex_studycompanion.data.Quiz.Quiz
import com.example.novanex_studycompanion.data.Quiz.QuizDao

@Database(entities = [Subject::class, Quiz::class], version = 4, exportSchema = false)
abstract class SubjectRoomDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao // Provide DAO for Subject
    abstract fun quizDao(): QuizDao // Provide DAO for Quiz

    companion object {
        @Volatile
        private var INSTANCE: SubjectRoomDatabase? = null // Singleton instance of the database

        // Get the singleton instance of the database
        fun getDatabase(context: Context): SubjectRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubjectRoomDatabase::class.java,
                    "subject_database"
                )
                    .addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Migration from version 2 to 3: Add bestScore column to quizzes_table
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE quizzes_table ADD COLUMN bestScore INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
