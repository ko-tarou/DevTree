package com.example.devtree

import android.annotation.SuppressLint
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
            SkillTreeMapScreen()
        }
    }
}

//スキルノードデータクラス
enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class SkillConnection(
    val targetId: String,
    val direction: Direction
)

data class SkillNode(
    val id: String,
    val name: String,
    var level: Int,
    val maxLevel: Int = 5,
    val unlocked: Boolean,
    val connections: List<SkillConnection>
)


fun sampleSkillNodes(): List<SkillNode> = listOf(
    SkillNode("kotlin", "Kotlin", 2, 5, true, listOf(
        SkillConnection("compose", Direction.LEFT),
        SkillConnection("xml_ui", Direction.RIGHT),
        SkillConnection("room", Direction.UP),
        SkillConnection("retrofit", Direction.DOWN)
    )),
    SkillNode("compose", "Compose", 1, 5, true, listOf(
        SkillConnection("mvvm", Direction.DOWN)
    )),
    SkillNode("xml_ui", "XML UI", 1, 5, false, listOf()),
    SkillNode("mvvm", "MVVM", 0, 5, false, listOf(
        SkillConnection("livedata", Direction.LEFT),
        SkillConnection("flow", Direction.RIGHT)
    )),
    SkillNode("livedata", "LiveData", 0, 5, false, listOf()),
    SkillNode("flow", "Flow", 0, 5, false, listOf()),
    SkillNode("retrofit", "Retrofit", 0, 5, false, listOf(
        SkillConnection("coroutines", Direction.DOWN)
    )),
    SkillNode("coroutines", "Coroutines", 0, 5, false, listOf()),
    SkillNode("room", "Room", 0, 5, false, listOf(
        SkillConnection("firebase", Direction.UP)
    )),
    SkillNode("firebase", "Firebase", 0, 5, false, listOf())
)



fun generateNodePositions(
    nodes: List<SkillNode>,
    startId: String,
    gridSize: Float = 200f
): Map<String, Offset> {
    val positions = mutableMapOf<String, Offset>()
    val visited = mutableSetOf<String>()
    val queue = ArrayDeque<Pair<String, Offset>>()

    positions[startId] = Offset(0f, 0f)
    queue.add(startId to Offset(0f, 0f))

    while (queue.isNotEmpty()) {
        val (currentId, currentPos) = queue.removeFirst()
        if (visited.contains(currentId)) continue
        visited.add(currentId)

        val currentNode = nodes.find { it.id == currentId } ?: continue
        for (conn in currentNode.connections) {
            val targetOffset = when (conn.direction) {
                Direction.UP -> Offset(currentPos.x, currentPos.y - gridSize)
                Direction.DOWN -> Offset(currentPos.x, currentPos.y + gridSize)
                Direction.LEFT -> Offset(currentPos.x - gridSize, currentPos.y)
                Direction.RIGHT -> Offset(currentPos.x + gridSize, currentPos.y)
            }
            if (!positions.containsKey(conn.targetId)) {
                positions[conn.targetId] = targetOffset
                queue.add(conn.targetId to targetOffset)
            }
        }
    }
    return positions
}


// メイン画面
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SkillTreeMapScreen() {
    val skills = remember { sampleSkillNodes() }
    val positions = remember { generateNodePositions(skills, startId = "kotlin") }
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var selectedSkill by remember { mutableStateOf<SkillNode?>(null) }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
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
                .transformable(state = transformState)
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
                            val adjustedTap = (tapOffset - offset) / scale
                            val tappedSkill = positions.entries.find { (id, pos) ->
                                val skill = skills.find { it.id == id } ?: return@find false
                                val dx = pos.x - adjustedTap.x
                                val dy = pos.y - adjustedTap.y
                                val distance = sqrt(dx * dx + dy * dy)
                                distance < 40f
                            }?.key?.let { id -> skills.find { it.id == id } }

                            if (tappedSkill != null) {
                                selectedSkill = tappedSkill
                                coroutineScope.launch { sheetState.show() }
                            }
                        }
                    }
            ) {
                // 線描画
                skills.forEach { skill ->
                    val startPos = positions[skill.id] ?: return@forEach
                    skill.connections.forEach { conn ->
                        val endPos = positions[conn.targetId] ?: return@forEach
                        drawLine(
                            color = Color.Gray,
                            start = startPos,
                            end = endPos,
                            strokeWidth = 4f / scale
                        )
                    }
                }

                // ノード描画
                skills.forEach { skill ->
                    val pos = positions[skill.id] ?: return@forEach
                    drawCircle(
                        color = if (skill.unlocked) Color(0xFF4CAF50) else Color.Gray,
                        radius = 40f,
                        center = pos
                    )
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            skill.name,
                            pos.x - 30f,
                            pos.y + 5f,
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
