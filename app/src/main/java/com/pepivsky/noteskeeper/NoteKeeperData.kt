package com.pepivsky.noteskeeper

class CourseInfo(val courseId: String, val title: String) {

    override fun toString(): String { //sobreescribiendo el metodo toString para que se muestre solo el titulo en el spinner
        return title
    }
}

class NoteInfo(var course: CourseInfo, var title: String, var text: String) {


}