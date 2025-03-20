package com.example.devtree.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skill_levels")
data class SkillLevelEntity(
    @PrimaryKey val skillId: String,
    val level: Int
)
