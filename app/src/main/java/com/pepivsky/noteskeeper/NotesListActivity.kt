package com.pepivsky.noteskeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class NotesListActivity : AppCompatActivity() {

    private lateinit var listNotes: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        listNotes = findViewById(R.id.listNotes)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }
        //poblando el listView
        listNotes.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, DataManager.notes) //usando  un layout generico incluido en Android

        listNotes.setOnItemClickListener { parent, view, position, id -> //metodo que se lana cuando se le da tap a un item de la lista
            val activityIntent = Intent(this, MainActivity::class.java) //intent que lanza la actividad y envia
            activityIntent.putExtra(EXTRA_NOTE_POSITION, position)
            startActivity(activityIntent)
        }
    }

    //sobreescribiendo el metodo para que cuando se guarde una nota nueva, se refresque la lista de la actividad,
    override fun onResume() {
        super.onResume()
        (listNotes.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged() //notificando al adapter que la lista ha cambiado, (se ha agregado un item)

    }
}