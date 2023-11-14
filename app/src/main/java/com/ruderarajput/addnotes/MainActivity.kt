package com.ruderarajput.addnotes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mancj.materialsearchbar.MaterialSearchBar
import com.ruderarajput.addnotes.Activity.AddEditActivity
import com.ruderarajput.addnotes.Adapter.RvAdapter
import com.ruderarajput.addnotes.Room.Note
import com.ruderarajput.addnotes.ViewModels.MainActivityViewModel
import com.ruderarajput.addnotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var noteList:List<Note>
    lateinit var viewModel: MainActivityViewModel
    lateinit var adapter:RvAdapter

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        noteList=ArrayList()

        var observer=Observer<List<Note>>{
            noteList=it
            adapter = RvAdapter(noteList,this)
            binding.recViewNotes.layoutManager=LinearLayoutManager(this)
            binding.recViewNotes.adapter=adapter
        }

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.notesList.observe(this,observer)
        adapter = RvAdapter(noteList,this)
        binding.recViewNotes.layoutManager=LinearLayoutManager(this)
        binding.recViewNotes.adapter=adapter

        binding.addNotes.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
        val searchBar=binding.searchBar
        searchBar.setOnSearchActionListener(object :MaterialSearchBar.OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {
              if (!enabled){
                  noteList=noteList
                  adapter.updateData(noteList)
              }else{

              }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
               filterNotes(text.toString())
            }

            override fun onButtonClicked(buttonCode: Int) {
            }
        })
    }
    private fun filterNotes(query: String) {
        var filteredNotes=noteList.filter { note ->
            note.titleName!!.contains(query,ignoreCase = true)||
                    note.noteName!!.contains(query,ignoreCase = true)
        }
        adapter.updateData(filteredNotes)
    }
}