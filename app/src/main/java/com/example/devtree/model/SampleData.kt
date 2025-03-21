package com.example.devtree.model

fun sampleSkillNodes(): List<SkillNode> = listOf(

    //アプリの基礎
    SkillNode("application-fundamentals", "基本", 0, 5, listOf(
        SkillConnection("Languages", Direction.RIGHT),
        SkillConnection("Android-package",Direction.LEFT),


        SkillConnection("Android-Operating-System", Direction.DOWN2)
    )),

    SkillNode("Languages","言語",0,5, listOf(
        SkillConnection("Kotlin", Direction.UR),
        SkillConnection("java", Direction.RIGHT),
        SkillConnection("C++", Direction.DR),
    )),

    SkillNode("Kotlin","Kotlin",0,5, listOf()),
    SkillNode("java","java",0,5, listOf()),
    SkillNode("C++","C++",0,5, listOf()),


    SkillNode("Android-package","ファイル",0,5, listOf(
        SkillConnection("APK", Direction.UL),
        SkillConnection("AAB", Direction.DL),
    )),

    SkillNode("APK","APK",0,5, listOf()),
    SkillNode("AAB","AAB",0,5, listOf()),



    //AndroidのOS
    SkillNode("Android-Operating-System", "OS", 0, 5,  listOf(
        SkillConnection("Multi-User-Linux", Direction.UL),
        SkillConnection("File-Permissions", Direction.UR),
        SkillConnection("Resource-Isolations", Direction.LEFT),
        SkillConnection("Process-Management", Direction.RIGHT),

        SkillConnection("Plat-Arch", Direction.DOWN2)
    )),

    SkillNode("Multi-User-Linux","Multi-User-Linux",0,5, listOf()),
    SkillNode("File-Permissions","File-Permissions",0,5, listOf()),
    SkillNode("Resource-Isolations","Resource-Isolations",0,5, listOf()),
    SkillNode("Process-Management","Process-Management",0,5, listOf()),


    //Android Platform Architecture
    SkillNode("Plat-Arch", "Plat-Arch", 0, 5,  listOf(
        SkillConnection("The-Linux-Kernel", Direction.UL),
        SkillConnection("Hardware-Abstraction", Direction.UR),
        SkillConnection("Android-Runtime", Direction.LEFT),
        SkillConnection("Native-Libraries", Direction.RIGHT),
        SkillConnection("java-API-Framework", Direction.DL),
        SkillConnection("System-Apps", Direction.DR),

        SkillConnection("App-Manifest",Direction.DOWN2)
    )),

    SkillNode("The-Linux-Kernel","The-Linux-Kernel",0,5, listOf()),
    SkillNode("Hardware-Abstraction","Hardware-Abstraction",0,5, listOf()),
    SkillNode("Android-Runtime","Android-Runtime",0,5, listOf()),
    SkillNode("Native-Libraries","Native-Libraries",0,5, listOf()),
    SkillNode("java-API-Framework","java-API-Framework",0,5, listOf()),
    SkillNode("System-Apps","System-Apps",0,5, listOf()),


    //App Manifest
    SkillNode("App-Manifest", "App-Manifest", 0, 5,  listOf(
        SkillConnection("Package", Direction.RIGHT),
        SkillConnection("Application-ID", Direction.LEFT),

        SkillConnection("App-Components",Direction.DOWN2)
    )),

    SkillNode("Package","Package",0,5, listOf()),
    SkillNode("Application-ID","Application-ID",0,5, listOf()),


    //App-Components
    SkillNode("App-Components", "App-Components", 0, 5,  listOf(
        SkillConnection("Activity", Direction.DL),
        SkillConnection("Service", Direction.UR),
        SkillConnection("Broadcast-Receiver", Direction.UL),
        SkillConnection("Intent", Direction.LEFT),
        SkillConnection("Content-Provider", Direction.DR),

        SkillConnection("App-Architecture",Direction.DOWN3)
    )),

    SkillNode("Activity","Activity",0,5, listOf()),
    SkillNode("Service","Service",0,5, listOf()),
    SkillNode("Broadcast-Receiver","Broadcast-Receiver",0,5, listOf()),
    SkillNode("Intent","Intent",0,5, listOf(
        SkillConnection("Intent-Filters", Direction.UL),
        SkillConnection("Explicit-Intents", Direction.LEFT),
        SkillConnection("Implicit-Intents", Direction.DL),
    )),
    SkillNode("Content-Provider","Content-Provider",0,5, listOf()),

    SkillNode("Intent-Filters","Intent-Filters",0,5, listOf()),
    SkillNode("Explicit-Intents","Explicit-Intents",0,5, listOf()),
    SkillNode("Implicit-Intents","Implicit-Intents",0,5, listOf()),



    //App-Architecture
    SkillNode("App-Architecture","App-Architecture",0,5, listOf(
        SkillConnection("App-Entry-Points", Direction.UL),
        SkillConnection("App-Navigation", Direction.UR),
        SkillConnection("App-Startup", Direction.DR),
        SkillConnection("Dependency-Injection", Direction.LEFT),
        SkillConnection("Arch-Components", Direction.DDL),

        SkillConnection("Design-Patterns", Direction.DOWN6),
    )),

    SkillNode("App-Entry-Points","App-Entry-Points",0,5, listOf(
        SkillConnection("Activities", Direction.LEFT),
        SkillConnection("App-Shortcuts", Direction.DL),
    )),
    SkillNode("App-Navigation","App-Navigation",0,5, listOf(
        SkillConnection("Navigation-Component", Direction.RIGHT),
        SkillConnection("Fragments", Direction.UUUR),
        SkillConnection("App-Links", Direction.DDDR),
    )),
    SkillNode("App-Startup","App-Startup",0,5, listOf()),
    SkillNode("Dependency-Injection","DI",0,5, listOf()),
    SkillNode("Arch-Components","Arch-Components",0,5, listOf(
        SkillConnection("UI-Layer", Direction.DDL),
        SkillConnection("Data-Layer", Direction.LEFT),
    )),

    SkillNode("Activities","Activities",0,5, listOf(
        SkillConnection("Activity", Direction.UR),

        SkillConnection("Activity-Lifecycles", Direction.UUL),
        SkillConnection("Activity-State-Changes", Direction.UL),
        SkillConnection("Task-and-Back-Stack", Direction.LEFT),
        SkillConnection("Parcelables-and-Bundles", Direction.DL),
    )),
    SkillNode("App-Shortcuts","App-Shortcuts",0,5, listOf()),

    SkillNode("Activity-Lifecycles","Activity-Lifecycles",0,5, listOf()),
    SkillNode("Activity-State-Changes","Activity-State-Changes",0,5, listOf()),
    SkillNode("Task-and-Back-Stack","Task-and-Back-Stack",0,5, listOf()),
    SkillNode("Parcelables-and-Bundles","Parcelables-and-Bundles",0,5, listOf()),

    SkillNode("Navigation-Component","Navigation-Component",0,5, listOf(
        SkillConnection("Navigation-Graph", Direction.UUR),
        SkillConnection("Global-Actions", Direction.UR),
        SkillConnection("Destinations", Direction.RIGHT),
        SkillConnection("DeepLink", Direction.DR),
    )),
    SkillNode("Fragments","Fragments",0,5, listOf(
        SkillConnection("Fragment-Lifecycles", Direction.UUUR),
        SkillConnection("Fragment-State-Changes", Direction.UUR),
        SkillConnection("Fragment-Manager", Direction.UR),
        SkillConnection("Fragment-Transactions", Direction.RIGHT),

        SkillConnection("DialogFragment", Direction.UP),
    )),
    SkillNode("App-Links","App-Links",0,5, listOf(
        SkillConnection("TabLayout", Direction.UR),
        SkillConnection("ViewPager", Direction.RIGHT),
        SkillConnection("ViewPager2", Direction.DR),
        SkillConnection("Custom-Back-Navigation", Direction.DOWN),
    )),

    SkillNode("Fragment-Lifecycles","Fragment-Lifecycles",0,5, listOf()),
    SkillNode("Fragment-State-Changes","Fragment-State-Changes",0,5, listOf()),
    SkillNode("Fragment-Manager","Fragment-Manager",0,5, listOf()),
    SkillNode("Fragment-Transactions","Fragment-Transactions",0,5, listOf()),
    SkillNode("DialogFragment","DialogFragment",0,5, listOf(
        SkillConnection("BottomSheetDialogFragment", Direction.UP),
    )),

    SkillNode("BottomSheetDialogFragment","BottomSheetDialog",0,5, listOf()),

    SkillNode("Navigation-Graph","Navigation-Graph",0,5, listOf()),
    SkillNode("Global-Actions","Global-Actions",0,5, listOf()),
    SkillNode("Destinations","Destinations",0,5, listOf()),
    SkillNode("DeepLink","DeepLink",0,5, listOf()),

    SkillNode("TabLayout","TabLayout",0,5, listOf()),
    SkillNode("ViewPager","ViewPager",0,5, listOf()),
    SkillNode("ViewPager2","ViewPager2",0,5, listOf()),
    SkillNode("Custom-Back-Navigation","Custom-Back-Navigation",0,5, listOf()),

    SkillNode("Data-Layer","Data-Layer",0,5, listOf(
        SkillConnection("Data-Store", Direction.UL),
        SkillConnection("WorkManager", Direction.LEFT),
    )),
    SkillNode("UI-Layer","UI-Layer",0,5, listOf(
        SkillConnection("ViewBinding", Direction.UL),
        SkillConnection("DataBinding", Direction.LEFT),
        SkillConnection("Lifecycle", Direction.DL),
        SkillConnection("ViewModel", Direction.DOWN),
        SkillConnection("LiveData", Direction.DR),
        SkillConnection("Paging", Direction.RIGHT),
    )),

    SkillNode("Data-Store","Data-Store",0,5, listOf()),
    SkillNode("WorkManager","WorkManager",0,5, listOf()),
    SkillNode("ViewBinding","ViewBinding",0,5, listOf()),
    SkillNode("DataBinding","DataBinding",0,5, listOf()),
    SkillNode("Lifecycle","Lifecycle",0,5, listOf()),
    SkillNode("ViewModel","ViewModel",0,5, listOf()),
    SkillNode("LiveData","LiveData",0,5, listOf()),
    SkillNode("Paging","Paging",0,5, listOf()),



    //Design-Patterns
    SkillNode("Design-Patterns","Design-Patterns",0,5, listOf(
        SkillConnection("Builder-Pattern", Direction.UUR),
        SkillConnection("Factory-Pattern", Direction.UR),
        SkillConnection("Dependency-Injection2", Direction.RIGHT),
        SkillConnection("Observer-Pattern", Direction.DR),
        SkillConnection("Architecture", Direction.DL),
    )),

    SkillNode("Builder-Pattern","Builder-Pattern",0,5, listOf()),
    SkillNode("Factory-Pattern","Factory-Pattern",0,5, listOf()),
    SkillNode("Dependency-Injection2","DI",0,5, listOf()),
    SkillNode("Observer-Pattern","Observer-Pattern",0,5, listOf()),
    SkillNode("Architecture","Architecture",0,5, listOf()),


)
