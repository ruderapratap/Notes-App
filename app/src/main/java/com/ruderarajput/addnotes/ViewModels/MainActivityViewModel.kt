package com.ruderarajput.addnotes.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ruderarajput.addnotes.Room.Note
import com.ruderarajput.addnotes.Room.NoteDatabase

class MainActivityViewModel(application: Application):AndroidViewModel(application) {

    lateinit var notesList:LiveData<List<Note>>

    init {
        notesList=NoteDatabase.getDb(application).noteDao().getAllNotes()
    }
}