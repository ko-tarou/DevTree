package com.example.devtree.model

fun sampleSkillNodes(): List<SkillNode> = listOf(

    //アプリの基礎
    SkillNode("application-fundamentals", "基本", 0, 5, listOf(
        SkillConnection("Languages", Direction.RIGHT),
        SkillConnection("sample",Direction.LEFT),
        SkillConnection("retrofits", Direction.DOWN2)
    )),

    SkillNode("sample","サンプル",0,5, listOf()),

    SkillNode("Languages","言語",0,5, listOf(
        SkillConnection("Kotlin", Direction.UR),
        SkillConnection("java", Direction.RIGHT),
        SkillConnection("C++", Direction.DR),
    )),

    SkillNode("Kotlin","Kotlin",0,5, listOf()),
    SkillNode("java","java",0,5, listOf()),
    SkillNode("C++","C++",0,5, listOf()),

    SkillNode("retrofits", "Retrofits", 0, 5,  listOf(
        SkillConnection("coroutines", Direction.DOWN2)
    )),
    SkillNode("coroutines", "Coroutines", 0, 5,  listOf(
        SkillConnection("room",Direction.DOWN2)
    )),
    SkillNode("room", "Room", 2, 5,  listOf()),

)
