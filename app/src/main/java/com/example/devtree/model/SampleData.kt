package com.example.devtree.model

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
): Map<String, androidx.compose.ui.geometry.Offset> {
    val positions = mutableMapOf<String, androidx.compose.ui.geometry.Offset>()
    val visited = mutableSetOf<String>()
    val queue = ArrayDeque<Pair<String, androidx.compose.ui.geometry.Offset>>()

    positions[startId] = androidx.compose.ui.geometry.Offset(0f, 0f)
    queue.add(startId to androidx.compose.ui.geometry.Offset(0f, 0f))

    while (queue.isNotEmpty()) {
        val (currentId, currentPos) = queue.removeFirst()
        if (visited.contains(currentId)) continue
        visited.add(currentId)

        val currentNode = nodes.find { it.id == currentId } ?: continue
        for (conn in currentNode.connections) {
            val targetOffset = when (conn.direction) {
                Direction.UP -> androidx.compose.ui.geometry.Offset(currentPos.x, currentPos.y - gridSize)
                Direction.DOWN -> androidx.compose.ui.geometry.Offset(currentPos.x, currentPos.y + gridSize)
                Direction.LEFT -> androidx.compose.ui.geometry.Offset(currentPos.x - gridSize, currentPos.y)
                Direction.RIGHT -> androidx.compose.ui.geometry.Offset(currentPos.x + gridSize, currentPos.y)
            }
            if (!positions.containsKey(conn.targetId)) {
                positions[conn.targetId] = targetOffset
                queue.add(conn.targetId to targetOffset)
            }
        }
    }
    return positions
}
