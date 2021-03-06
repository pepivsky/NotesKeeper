package com.pepivsky.noteskeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {

    private var notePosition = POSITION_NOT_SET

    lateinit var spinnerCourses: Spinner
    lateinit var edtNoteTitle: EditText
    lateinit var edtNoteText: EditText
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)


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
        notePosition = savedInstanceState?.getInt(CURRENT_NOTE_POSITION, POSITION_NOT_SET) ?: //recuperando la posicion del bundle que se crea con onsaveInstanceState, si el valor de este es nulo significa que es la primera vez que se crea esta actividad, por lo tanto le asignamos el que viene del extra
            intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET) //si no hay posicion se devuelve la constante como valor por defecto

        if (notePosition != POSITION_NOT_SET) {
            displayNote()
        } else { //crear nota vacia
            DataManager.notes.add(NoteInfo()) //guardando nota vacia
            notePosition = DataManager.notes.lastIndex
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //creare el menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //controlar las opciones del menu, (cuando un item es presionado)
        return when(item.itemId) {
            R.id.action_next -> {
            moveNext()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }



    }

    private fun moveNext() { //metodo para pasar al suigiente objeto de la lista al darle tap al menu
        ++notePosition
        displayNote()
        invalidateOptionsMenu() //este metodo llama a "onPRepareOptionsMenu" cada vez que se pasa al siguiente item
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean { //metodo para cambiar el item del menu en tiempo de ejecucion
        if (notePosition >= DataManager.notes.lastIndex) {//si la posicion esta en el ultimo item, cambia el menu para evitar el error de salirnos del tamaño de la lista
            val menuItem = menu?.findItem(R.id.action_next) //obteniendo el menu

            if (menuItem != null) { //si el item no es nulo podemos trabajar sus propiedadea
                menuItem.icon = getDrawable(R.drawable.ic_block) //cambiamos el icono
                menuItem.isEnabled = false // desactiva el boton
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition] //recuperando el objeto nota a partir de su posicion en la lista
        edtNoteTitle.setText(note.title)
        edtNoteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition) //seteando la posicion del curso para que se muestre en el sppiner

    }

    //sobreescribiendo el metodo para que se guarden los datos al darle back a la actividad
    override fun onPause() {
        super.onPause()
        saveNote() //funcion que guarda la nota
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition] //obteniendo la nota
        //seteando las propiedades
        note.title = edtNoteTitle.text.toString()
        note.text = edtNoteText.text.toString()
        note.course = spinnerCourses.selectedItem as CourseInfo
    }

    //sobreescribir el mteodo para guardar la posicion de lla nota actual y que se restaure al girar la pantalla, (evita que nuestra navegacion se reinice a valor obtenido del intent)
    override fun onSaveInstanceState(outState: Bundle) { //recibe un bundle
        super.onSaveInstanceState(outState)
        outState?.putInt(CURRENT_NOTE_POSITION, notePosition) //guardando la posicion actual en el bundle
    }
}