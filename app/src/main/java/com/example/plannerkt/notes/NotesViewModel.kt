package com.example.plannerkt.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.plannerkt.models.Note

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private var repository:NotesRepository = NotesRepository(application)

    fun getNotes() = repository.getNotes()

    fun getNoteById(id: Int) = repository.getNoteById(id)

    fun updateNote(note: Note) = repository.updateNote(note)

    fun setNotes(notes: Note) { repository.setNotes(notes)}



    fun deleteItem(id: Int) { repository.deleteItem(id)}

    fun deleteAllItems(note: List<Note?>?) { repository.deleteAllItems(note)}

//    fun saveItem(note: Note){repository.saveItem(note)}

}