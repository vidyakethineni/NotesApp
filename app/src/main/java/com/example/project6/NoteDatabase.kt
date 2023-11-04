package com.example.project6

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Database class to manage and provide access to the Note entities
 */
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    /**
     * Abstract function to retrieve the Note Data Access Object (DAO)
     */
    abstract val noteDao: NoteDao
    /**
     * Companion object holding a singleton instance of the database
     */
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        /**
         * Retrieves a singleton instance of the database
         */
        fun getInstance(context: Context): NoteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "notes_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}