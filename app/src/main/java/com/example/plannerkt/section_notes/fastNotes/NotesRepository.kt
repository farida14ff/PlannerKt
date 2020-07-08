package com.example.plannerkt.section_notes.fastNotes

import android.app.Application
import android.util.Log
import android.view.View
import com.example.plannerkt.BuildConfig
import com.example.plannerkt.data.NotesDao
import com.example.plannerkt.data.NotesDatabase
import com.example.plannerkt.models.FastNote
import com.example.plannerkt.models.Note
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
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


    fun deleteAllItems(note: List<Note?>?){
        launch { deleteAllItemsBG(note) }
    }

    private suspend fun deleteAllItemsBG(note:List<Note?>?){
        withContext(Dispatchers.IO){
            notesDao?.deleteAllItems(note)
        }
    }

    fun getNotes() = notesDao?.getNotes()



    fun getNoteById(id: Int)=notesDao?.getNoteById(id)

//        launch {
//            getNoteByIdBG(id)
//        }


    private suspend fun getNoteByIdBG(id: Int) {
        withContext(Dispatchers.IO){
            notesDao?.getNoteById(id)
        }
    }


    fun updateNote(body: String, id: Int){
        launch {
            updateNoteBG(body, id)
        }
    }

    private suspend fun updateNoteBG(body: String, id: Int){
        withContext(Dispatchers.IO){
            notesDao?.updateNote(body, id)
        }
    }
//    fun updateNote(note: Note){
//        launch {
//            updateNoteBG(note)
//        }
//    }
//
//    private suspend fun updateNoteBG(note: Note){
//        withContext(Dispatchers.IO){
//            notesDao?.updateNote(note)
//        }
//    }

    fun setNotes(note: Note) {
        launch  { setNoteBG(note) }
    }

    private suspend fun setNoteBG(note: Note){
        withContext(Dispatchers.IO){
            notesDao?.setNotes(note)
        }
    }

    fun setFbNotes(text: String) {
        launch  { setFbNoteBG(text) }
    }

    private suspend fun setFbNoteBG(text: String){
        withContext(Dispatchers.IO){
            val map: MutableMap<String, Any> = HashMap()
//            map["sentAt"] = FieldValue.serverTimestamp()//timestamp.now
            map["body"] = text
//        map["uid"] = String.valueOf(fbUserId)
            FirebaseFirestore.getInstance().collection("fastNotes").add(map)
            Log.e("setFbNoteBG","set")


        }
    }

}