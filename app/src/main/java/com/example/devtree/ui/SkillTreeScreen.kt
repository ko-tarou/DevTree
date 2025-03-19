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
import androidx.compose.ui.graphics.toArgb
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

    // 🎨 カラーパレット定義
    val BgColor = Color(0xFFF6F1DE)
    val LineColor = Color(0xFF3E3F5B)
    val NodeLockedColor = Color(0xFF3E3F5B)
    val NodeLvLow = Color(0xFF8AB2A6)
    val NodeLvHigh = Color(0xFFACD3A8)
    val TextColor = Color(0xFFFFFFFF)

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
                .background(BgColor)
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
                            val centerOffset = Offset(
                                (size.width / 2).toFloat(),
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

                // 線の描画
                skills.forEach { skill ->
                    val startPos = positions[skill.id]?.plus(centerOffset) ?: return@forEach
                    skill.connections.forEach { conn ->
                        val endPos = positions[conn.targetId]?.plus(centerOffset) ?: return@forEach
                        drawLine(
                            color = LineColor.copy(alpha = 0.4f),
                            start = startPos,
                            end = endPos,
                            strokeWidth = 3f / scale
                        )
                    }
                }

                // ノード描画
                skills.forEach { skill ->
                    val pos = positions[skill.id]?.plus(centerOffset) ?: return@forEach

                    val nodeColor = if (skill.unlocked) {
                        if (skill.level < 3) NodeLvLow else NodeLvHigh
                    } else {
                        NodeLockedColor
                    }

                    // 光彩（背景ぼかし効果）
                    drawCircle(
                        color = nodeColor.copy(alpha = 0.3f),
                        radius = 50f,
                        center = pos
                    )

                    drawCircle(
                        color = nodeColor,
                        radius = 40f,
                        center = pos
                    )

                    // 文字サイズ調整（長さに応じて）
                    val baseFontSize = 22f
                    val adjustedFontSize = when {
                        skill.name.length <= 4 -> baseFontSize
                        skill.name.length <= 6 -> baseFontSize * 0.85f
                        skill.name.length <= 8 -> baseFontSize * 0.75f
                        else -> baseFontSize * 0.65f
                    }

                    // テキスト中央描画
                    drawContext.canvas.nativeCanvas.apply {
                        val paint = android.graphics.Paint().apply {
                            color = TextColor.toArgb()
                            textSize = adjustedFontSize
                            isFakeBoldText = true
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                        drawText(
                            skill.name,
                            pos.x,
                            pos.y + adjustedFontSize / 3,
                            paint
                        )
                    }
                }
            }
        }
    }
}
