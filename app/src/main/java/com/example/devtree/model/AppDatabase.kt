package com.example.devtree.model

import android.content.Context
import androidx.room.*

@Database(entities = [SkillLevelEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun skillLevelDao(): SkillLevelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "devtree_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
