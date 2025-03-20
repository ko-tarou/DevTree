package com.example.devtree.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devtree.model.*
import kotlinx.coroutines.launch

class SkillTreeViewModel(application: Application) : AndroidViewModel(application) {
    // skills を状態管理付きリストで定義
    val skills = mutableStateListOf<SkillNode>()

    // positions も状態付きMapとして管理
    var positions by mutableStateOf<Map<String, Offset>>(emptyMap())
        private set

    var scale by mutableStateOf(1f)
    var offset by mutableStateOf(Offset.Zero)
    var selectedSkill by mutableStateOf<SkillNode?>(null)
    var pendingLevelChange by mutableStateOf<Int?>(null)

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.skillLevelDao()

    init {
        viewModelScope.launch {
            val loadedSkills = sampleSkillNodes()

            // Roomから保存されたレベルを取得＆適用
            val levels = dao.getAllLevels()
            levels.forEach { saved ->
                loadedSkills.find { it.id == saved.skillId }?.level = saved.level
            }

            // skillsに読み込んだデータを追加（UI再描画が自動で走る）
            skills.addAll(loadedSkills)

            // skillsに基づいて positions を生成（ここで初めて意味ある）
            positions = generateNodePositions(skills, startId = "application-fundamentals")
        }
    }

    fun updateSkillLevel(skill: SkillNode, newLevel: Int) {
        skill.level = newLevel
        viewModelScope.launch {
            dao.insertLevel(SkillLevelEntity(skill.id, newLevel))
        }
    }

    fun setPendingLevel(level: Int) {
        pendingLevelChange = level
    }

    fun clearPendingLevelChange() {
        pendingLevelChange = null
    }

    fun updateTransform(zoomChange: Float, offsetChange: Offset) {
        scale *= zoomChange
        offset += offsetChange
    }
}
