package com.example.plannerkt.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.plannerkt.models.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setNotes(note: Note)

    @Update
    fun updateNote(note: Note)


    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int) : Note

    @Query("SELECT * from notes ORDER BY id ASC")
    fun getNotes() : LiveData<List<Note>>

    @Query("DELETE FROM notes WHERE id = :id")
    fun deleteItem(id: Int)

    @Delete
    fun deleteAllItems(note: List<Note?>?)


//    @Query("UPDATE notes SET basket_counter_menu = :num WHERE id = :id")
//    fun update(num: Int, id: Long)
}