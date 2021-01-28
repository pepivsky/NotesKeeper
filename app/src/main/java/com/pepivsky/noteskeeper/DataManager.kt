package com.pepivsky.noteskeeper

class DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
    }

    private fun initializeCourses() { //inicializa la lista de cursos
        var course = CourseInfo("android_intents", "Android with intents")
        courses.set(course.courseId, course)

        course = CourseInfo("android_async", "Android async programming and services")
        courses.set(course.courseId, course)
    }

}