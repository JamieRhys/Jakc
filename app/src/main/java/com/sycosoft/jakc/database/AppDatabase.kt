package com.sycosoft.jakc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sycosoft.jakc.database.converters.LocalDateConverter
import com.sycosoft.jakc.database.dao.PartDao
import com.sycosoft.jakc.database.dao.ProjectDao
import com.sycosoft.jakc.database.entities.EntityCounter
import com.sycosoft.jakc.database.entities.EntityPart
import com.sycosoft.jakc.database.entities.EntityProject
import com.sycosoft.jakc.database.entities.Project

/**
 * This manages our applications database connection.
 *
 * @author Jamie-Rhys Edwards
 * @since v0.0.1
 *
 */
// Let's define the databases configuration
@Database(
    entities = [
        EntityProject::class,
        EntityPart::class,
        EntityCounter::class
    ], // List of entity classes that will be a part of the database.
    version = 2 // The version of the database.
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val projectDao: ProjectDao
    abstract val partDao: PartDao

    companion object {
        // This volatile variable ensures that the value is read from and written to main memory directly,
        // avoiding the use of a CPU cache, making it safe for multithreaded access.
        @Volatile var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // If the instance is null, the database has not yet been initialised.
            if(INSTANCE == null) {
                // Synchronised block ensures that only one thread can execute the code within it
                // at any one time, preventing multiple threads from creating duplicate instances
                // of the database.
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "jakc.db",
                    )
                        .fallbackToDestructiveMigration() // Set to allow destructive migration,
                        // meaning if the database schema changes, it will drop the existing
                        // database and recreate it.
                        // TODO: Remove fallbackToDestructiveMigration for production release as we do not want this!
                        .build()
                }
            }

            // If the instance is not null, we've previously initialised the database (creating a
            // singleton class), so return the instance.
            return INSTANCE!!
        }
    }
}