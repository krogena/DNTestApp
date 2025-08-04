package ru.iyshcherbakov.dntestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.jvm.java

@Database([NodeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun nodeDao(): NodeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "node_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}