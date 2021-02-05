package com.pepivsky.noteskeeper

data class CourseInfo(val courseId: String, val title: String) {

    override fun toString(): String { //sobreescribiendo el metodo toString para que se muestre solo el titulo en el spinner
        return title
    }
}

data class NoteInfo(var course: CourseInfo? = null, var title: String? = null, var text: String? = null) {


}