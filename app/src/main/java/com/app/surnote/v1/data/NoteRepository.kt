package com.app.surnote.v1.data

import com.app.surnote.v1.model.NoteItem

object NoteRepository {
    fun getNotes(): MutableList<NoteItem> {
        return mutableListOf(
            NoteItem(1, "Meeting Surnote", "Bahas fitur kalender dan to-do list", "9/4/2026"),
            NoteItem(2, "Syarat Lulus", "Skor bahasa inggris >450", "9/4/2026"),
            NoteItem(3, "Tugas Akhir", "Kerjakan BAB 3 minggu ini", "10/4/2026")
        )
    }
}