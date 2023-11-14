package com.ruderarajput.addnotes.ViewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.ruderarajput.addnotes.Room.Note
import com.ruderarajput.addnotes.Room.NoteDatabase
import kotlinx.coroutines.launch

class AddEditActivityViewHolder(application: Application):AndroidViewModel(application) {



  fun insert(note: Note,context: Context){
            NoteDatabase.getDb(context).noteDao().insert(note)
    }
    fun update(note: Note,context: Context){
        NoteDatabase.getDb(context).noteDao().update(note)
    }
}