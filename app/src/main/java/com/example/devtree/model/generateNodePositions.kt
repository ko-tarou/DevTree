package com.example.devtree.model

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
                Direction.UR -> androidx.compose.ui.geometry.Offset(currentPos.x + gridSize,currentPos.y - gridSize)
                Direction.UL -> androidx.compose.ui.geometry.Offset(currentPos.x - gridSize,currentPos.y - gridSize)
                Direction.DR -> androidx.compose.ui.geometry.Offset(currentPos.x + gridSize,currentPos.y + gridSize)
                Direction.DL -> androidx.compose.ui.geometry.Offset(currentPos.x - gridSize,currentPos.y + gridSize)
            }
            if (!positions.containsKey(conn.targetId)) {
                positions[conn.targetId] = targetOffset
                queue.add(conn.targetId to targetOffset)
            }
        }
    }
    return positions
}