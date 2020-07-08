package com.example.plannerkt.section_notes.fastNotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.plannerkt.models.FastNote
import com.example.plannerkt.models.Note
import java.util.ArrayList

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NotesRepository =
        NotesRepository(application)

    fun getNotes() = repository.getNotes()

    fun getNoteById(id: Int) = repository.getNoteById(id)

    fun updateNote(body: String, id: Int) = repository.updateNote(body,id)
//    fun updateNote(note: Note) = repository.updateNote(note)

    fun setNotes(notes: Note) { repository.setNotes(notes)}

    fun setFbNotes(text: String) { repository.setFbNotes(text)}

    fun deleteItem(id: Int) { repository.deleteItem(id)}

    fun deleteAllItems(note: List<Note?>?) { repository.deleteAllItems(note)}

//    fun saveItem(note: Note){repository.saveItem(note)}

}