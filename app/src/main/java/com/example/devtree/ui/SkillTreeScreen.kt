package com.example.devtree.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devtree.viewmodel.SkillTreeViewModel
import kotlinx.coroutines.launch
import kotlin.math.sqrt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SkillTreeScreen(viewModel: SkillTreeViewModel = viewModel()) {
    val skills = viewModel.skills
    val positions = viewModel.positions
    val scale = viewModel.scale
    val offset = viewModel.offset
    val selectedSkill = viewModel.selectedSkill
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            selectedSkill?.let { skill ->
                SkillDetailSheet(skill) { newLevel ->
                    viewModel.updateSkillLevel(skill, newLevel)
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .transformable(
                    state = rememberTransformableState { zoomChange, offsetChange, _ ->
                        viewModel.updateTransform(zoomChange, offsetChange)
                    }
                )
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
                            val centerOffset = Offset((size.width / 2).toFloat(),
                                (size.height / 2).toFloat()
                            )
                            val adjustedTap = (tapOffset - offset) / scale - centerOffset
                            val tappedSkill = positions.entries.find { (id, pos) ->
                                val dx = pos.x - adjustedTap.x
                                val dy = pos.y - adjustedTap.y
                                val distance = sqrt(dx * dx + dy * dy)
                                distance < 40f
                            }?.key?.let { id -> skills.find { it.id == id } }

                            if (tappedSkill != null) {
                                viewModel.selectedSkill = tappedSkill
                                coroutineScope.launch { sheetState.show() }
                            }
                        }
                    }
            ) {
                val centerOffset = Offset(size.width / 2, size.height / 2)
                skills.forEach { skill ->
                    val startPos = positions[skill.id]?.plus(centerOffset) ?: return@forEach
                    skill.connections.forEach { conn ->
                        val endPos = positions[conn.targetId]?.plus(centerOffset) ?: return@forEach
                        drawLine(
                            color = Color.Gray,
                            start = startPos,
                            end = endPos,
                            strokeWidth = 4f / scale
                        )
                    }
                }
                skills.forEach { skill ->
                    val pos = positions[skill.id]?.plus(centerOffset) ?: return@forEach
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
