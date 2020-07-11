package com.example.plannerkt.section_notes.fastNotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.plannerkt.models.FastNote

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NotesRepository =
        NotesRepository(application)

    fun getNotes() = repository.getNotes()

    fun getNoteById(id: Int) = repository.getNoteById(id)

    fun updateNote(body: String, id: Int) = repository.updateNote(body,id)
//    fun updateNote(note: Note) = repository.updateNote(note)

    fun setNotes(notes: FastNote) { repository.setNotes(notes)}

    fun deleteItem(id: Int) { repository.deleteItem(id)}

    fun deleteAllItems(fastNote: List<FastNote?>?) { repository.deleteAllItems(fastNote)}

//    fun saveItem(note: Note){repository.saveItem(note)}

}