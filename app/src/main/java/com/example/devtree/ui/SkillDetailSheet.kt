package com.example.devtree.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devtree.model.SkillNode

@Composable
fun SkillDetailSheet(skill: SkillNode, onLevelChange: (Int) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Skill: ${skill.name}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Level: ${skill.level}/${skill.maxLevel}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = skill.level.toFloat(),
            onValueChange = { onLevelChange(it.toInt()) },
            valueRange = 0f..skill.maxLevel.toFloat()
        )
    }
}
