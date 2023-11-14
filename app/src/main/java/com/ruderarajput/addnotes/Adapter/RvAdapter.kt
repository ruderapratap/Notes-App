package com.ruderarajput.addnotes.Adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.ruderarajput.addnotes.Activity.AddEditActivity
import com.ruderarajput.addnotes.R
import com.ruderarajput.addnotes.Room.Note
import com.ruderarajput.addnotes.Room.NoteDatabase
import com.ruderarajput.addnotes.databinding.SampleNotesBinding
import java.nio.file.Files.delete

class RvAdapter(var notesList: List<Note>, var context: Context):RecyclerView.Adapter<RvAdapter.MyViewHolder>(){
    private var filteredData: List<Note> = notesList.toMutableList()

    lateinit var dialog: AlertDialog
    inner class MyViewHolder(var binding: SampleNotesBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding=SampleNotesBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return notesList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.binding.title.text=notesList.get(position).titleName
       holder.binding.descNotes.text=notesList.get(position).noteName
        holder.binding.noteTime.text= notesList.get(position).dateName.toString()

        holder.itemView.setOnClickListener {
            val intent=Intent(context,AddEditActivity::class.java)
            intent.putExtra("NOTE_EDIT",notesList.get(position))
            context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener{
            val dialog=AlertDialog.Builder(context)
            dialog.setTitle("Delete Note")
            dialog.setMessage("click on delete button for delete this note or click on cancel button for exit.")
            dialog.setIcon(R.drawable.delete_ic)
            dialog.setPositiveButton("Delete"){dialogInterface,which->
                val notesDatabase=NoteDatabase.getDb(context).noteDao().deleteSingleNote(id =notesList.get(position).id!!)
            }
            dialog.setNeutralButton("Cancel"){dialogInterface,which->
                //exit()
            }
            val dialoge=dialog.create()
            dialoge.show()
            true
        }
    }
    fun updateData(newNoteList:List<Note>){
        notesList=newNoteList
        notifyDataSetChanged()
    }
}