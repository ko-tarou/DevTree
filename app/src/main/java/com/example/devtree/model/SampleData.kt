package com.example.devtree.model

fun sampleSkillNodes(): List<SkillNode> = listOf(
    SkillNode("kotlin", "Kotlin", 2, 5, listOf(
        SkillConnection("compose", Direction.LEFT),
        SkillConnection("xml_ui", Direction.UR),
        SkillConnection("room", Direction.UP),
        SkillConnection("retrofit", Direction.DOWN)
    )),
    SkillNode("compose", "Compose", 1, 5,  listOf(
        SkillConnection("mvvm", Direction.DOWN)
    )),
    SkillNode("xml_ui", "XML UI", 1, 5,  listOf()),
    SkillNode("mvvm", "MVVM", 0, 5,  listOf(
        SkillConnection("livedata", Direction.LEFT),
        SkillConnection("flow", Direction.RIGHT)
    )),
    SkillNode("livedata", "LiveData", 0, 5,  listOf()),
    SkillNode("flow", "Flow", 0, 5,  listOf()),
    SkillNode("retrofit", "Retrofit", 0, 5,  listOf(
        SkillConnection("coroutines", Direction.DOWN)
    )),
    SkillNode("coroutines", "Coroutines", 0, 5,  listOf()),
    SkillNode("room", "Room", 0, 5,  listOf(
        SkillConnection("firebase", Direction.UP)
    )),
    SkillNode("firebase", "Firebase", 0, 5,  listOf())
)
