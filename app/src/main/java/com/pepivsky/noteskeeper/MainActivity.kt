package com.pepivsky.noteskeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    private var notePosition = POSITION_NOT_SET

    lateinit var spinnerCourses: Spinner
    lateinit var edtNoteTitle: EditText
    lateinit var edtNoteText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerCourses = findViewById(R.id.spinnerCourses)
        edtNoteTitle = findViewById(R.id.edtNoteTitle)
        edtNoteText = findViewById(R.id.edtNoteText)



        //adaptador
        val adapterCourses = ArrayAdapter<CourseInfo>(
            this,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCourses.adapter = adapterCourses

        //recuperando la posicion del intent
        notePosition = intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET) //si no hay posicio se devuelve la constante como valor por defecto

        if (notePosition != POSITION_NOT_SET) {
            displayNote()
        }

    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_next -> {
            moveNext()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }



    }

    private fun moveNext() {
        ++notePosition
        displayNote()
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition] //recuperando el objeto nota a partir de su posicion en la lista
        edtNoteTitle.setText(note.title)
        edtNoteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition) //seteando la posicion del curso para que se muestre en el sppiner

    }
}