package com.example.devtree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnifiedSkillTreeCanvas()
        }
    }
}

//スキルノードデータクラス
data class SkillNode(
    val id: String,
    val name: String,
    var level: Int,
    val maxLevel: Int = 5,
    val unlocked: Boolean,
    val prerequisites: List<String>,
    val position: Offset
)

// サンプルスキルツリーデータ
fun sampleSkillNodes(): List<SkillNode> = listOf(
    SkillNode("kotlin", "Kotlin", 2, 5, true, listOf(), Offset(300f, 100f)),
    SkillNode("compose", "Compose", 1, 5, true, listOf("kotlin"), Offset(200f, 300f)),
    SkillNode("mvvm", "MVVM", 0, 5, false, listOf("compose"), Offset(400f, 300f))
)

// メイン画面
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UnifiedSkillTreeCanvas() {
    val skills = remember { sampleSkillNodes() }
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var selectedSkill by remember { mutableStateOf<SkillNode?>(null) }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            selectedSkill?.let { skill ->
                SkillDetailSheet(skill = skill) { newLevel ->
                    skill.level = newLevel
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .transformable(state = state)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .pointerInput(Unit) {
                        detectTapGestures { tapOffset ->
                            // タップ位置からスキルノードを判定
                            val tappedSkill = skills.find { skill ->
                                val dx = (skill.position.x * scale + offset.x) - tapOffset.x
                                val dy = (skill.position.y * scale + offset.y) - tapOffset.y
                                val distance = sqrt(dx * dx + dy * dy)
                                distance < 40f * scale
                            }
                            if (tappedSkill != null) {
                                selectedSkill = tappedSkill
                                coroutineScope.launch { sheetState.show() }
                            }
                        }
                    }
            ) {
                // 線を描画
                skills.forEach { skill ->
                    skill.prerequisites.forEach { parentId ->
                        val parent = skills.find { it.id == parentId }
                        if (parent != null) {
                            drawLine(
                                color = Color.Gray,
                                start = parent.position,
                                end = skill.position,
                                strokeWidth = 4f / scale
                            )
                        }
                    }
                }

                // ノードを描画
                skills.forEach { skill ->
                    drawCircle(
                        color = if (skill.unlocked) Color(0xFF4CAF50) else Color.Gray,
                        radius = 40f,
                        center = skill.position
                    )
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            skill.name,
                            skill.position.x - 30f,
                            skill.position.y + 5f,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.WHITE
                                textSize = 24f
                            }
                        )
                    }
                }
            }
        }
    }
}



// スキル詳細BottomSheet
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
