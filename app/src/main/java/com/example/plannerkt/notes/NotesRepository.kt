package com.example.plannerkt.notes

import android.app.Application
import com.example.plannerkt.data.NotesDao
import com.example.plannerkt.data.NotesDatabase
import com.example.plannerkt.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class NotesRepository (application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var notesDao: NotesDao?

    init {
        val db = NotesDatabase.getDatabase(application)
        notesDao = db?.notesDao()
    }


    fun deleteItem(id: Int) = notesDao?.deleteItem(id)

//    fun deleteAllItems(note: List<Note?>?) = notesDao?.deleteAllItems(note)

    fun deleteAllItems(note: List<Note?>?){
        launch { deleteAllItemsBG(note) }
    }

    private suspend fun deleteAllItemsBG(note:List<Note?>?){
        withContext(Dispatchers.IO){
            notesDao?.deleteAllItems(note)
        }
    }

    fun getNotes() = notesDao?.getNotes()

    fun getNoteById(id: Int) = notesDao?.getNoteById(id)

    fun setNotes(note: Note) {
        launch  { setNoteBG(note) }
    }

    private suspend fun setNoteBG(note: Note){
        withContext(Dispatchers.IO){
            notesDao?.setNotes(note)
        }
    }

}