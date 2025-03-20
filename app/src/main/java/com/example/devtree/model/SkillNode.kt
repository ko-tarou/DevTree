package com.example.devtree.model

enum class Direction {
    UP, DOWN, LEFT, RIGHT,UR,UL,DR,DL
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
    val connections: List<SkillConnection>,
)
