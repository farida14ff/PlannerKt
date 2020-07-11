package com.example.plannerkt.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.plannerkt.models.FastNote

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setNotes(fastNote: FastNote)

//    @Update
//    fun updateNote(note: Note)

    @Query("UPDATE notes SET body = :body WHERE id = :id")
    fun updateNote(body: String, id: Int)


    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): FastNote?


    @Query("SELECT * from notes ORDER BY id ASC")
    fun getNotes() : LiveData<List<FastNote>>


    @Query("DELETE FROM notes WHERE id = :id")
    fun deleteItem(id: Int)


    @Delete
    fun deleteAllItems(fastNote: List<FastNote?>?)


//    @Query("UPDATE notes SET basket_counter_menu = :num WHERE id = :id")
//    fun update(num: Int, id: Long)
}