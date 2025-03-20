package com.example.devtree.model

import androidx.room.*

@Dao
interface SkillLevelDao {
    @Query("SELECT * FROM skill_levels")
    suspend fun getAllLevels(): List<SkillLevelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevel(skillLevel: SkillLevelEntity)
}
