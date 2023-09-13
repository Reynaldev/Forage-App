package com.reyndev.forage.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.reyndev.forage.model.Forageable

/**
 * Room database to persist data for the Forage app.
 * This database stores a [Forageable] entity
 */
// TODO: create the database with all necessary annotations, methods, variables, etc.
@Database(entities = [Forageable::class], version = 1, exportSchema = false)
abstract class ForageDatabase : RoomDatabase() {
    abstract fun forageableDao(): ForageableDao

    companion object {
        private var INSTANCE: ForageDatabase? = null

        fun getDatabase(context: Context): ForageDatabase =
            INSTANCE ?: synchronized(this) {
                return INSTANCE.let {
                    Log.v("ForageDatabase", "Creating database")

                    Room.databaseBuilder(
                        context,
                        ForageDatabase::class.java,
                        "forageable_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
    }
}