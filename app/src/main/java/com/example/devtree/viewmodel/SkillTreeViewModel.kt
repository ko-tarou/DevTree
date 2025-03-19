package com.example.devtree.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.example.devtree.model.*

class SkillTreeViewModel : ViewModel() {
    val skills = sampleSkillNodes()
    val positions = generateNodePositions(skills, startId = "kotlin")

    var scale by mutableStateOf(1f)
    var offset by mutableStateOf(Offset.Zero)
    var selectedSkill by mutableStateOf<SkillNode?>(null)

    // 🟢 レベル変更の一時保存用
    var pendingLevelChange by mutableStateOf<Int?>(null)

    fun updateSkillLevel(skill: SkillNode, newLevel: Int) {
        skill.level = newLevel
    }

    fun updateTransform(zoomChange: Float, offsetChange: Offset) {
        scale *= zoomChange
        offset += offsetChange
    }

    fun setPendingLevel(level: Int) {
        pendingLevelChange = level
    }

    fun clearPendingLevelChange() {
        pendingLevelChange = null
    }
}
