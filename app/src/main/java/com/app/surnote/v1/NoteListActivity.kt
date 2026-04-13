package com.app.surnote.v1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.surnote.v1.adapter.NoteAdapter
import com.app.surnote.v1.data.NoteRepository
import com.app.surnote.v1.model.NoteItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteListActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val noteList = NoteRepository.getNotes()

        adapter = NoteAdapter(noteList) { note ->
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra("note_title", note.title)
            intent.putExtra("note_content", note.content)
            intent.putExtra("note_date", note.date)
            startActivity(intent)
        }

        val rvNoteList = findViewById<RecyclerView>(R.id.rvNoteList)
        rvNoteList.layoutManager = LinearLayoutManager(this)
        rvNoteList.adapter = adapter

        // Search
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })

        // FAB tambah note
        val fabAddNote = findViewById<FloatingActionButton>(R.id.fabAddNote)
        fabAddNote.setOnClickListener {
            // nanti bisa diarahkan ke halaman tambah note
        }
    }
}