package com.app.surnote.v1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.surnote.v1.R
import com.app.surnote.v1.model.NoteItem
import com.app.surnote.v1.data.NoteRepository

class NoteAdapter(
    private var noteList: MutableList<NoteItem>,
    private val onClick: (NoteItem) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
        val tvPreview: TextView = itemView.findViewById(R.id.tvNotePreview)
        val tvDate: TextView = itemView.findViewById(R.id.tvNoteDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.tvTitle.text = note.title
        holder.tvPreview.text = note.content
        holder.tvDate.text = note.date
        holder.itemView.setOnClickListener { onClick(note) }
    }

    override fun getItemCount() = noteList.size

    fun filter(query: String) {
        noteList = if (query.isEmpty()) {
            NoteRepository.getNotes()
        } else {
            NoteRepository.getNotes()
                .filter { it.title.contains(query, ignoreCase = true) }
                .toMutableList()
        }
        notifyDataSetChanged()
    }
}