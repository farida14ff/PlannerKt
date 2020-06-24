package com.example.plannerkt.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.plannerkt.R
import com.example.plannerkt.listeners.OnItemClickListener
import com.example.plannerkt.listeners.OnItemLongClickListener
import com.example.plannerkt.models.Note
import com.example.plannerkt.notes.NotesFragment
import com.example.plannerkt.notes.NotesViewModel
import kotlinx.android.synthetic.main.notes_item.view.*
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class NotesAdapter(
    private val items: List<Note>?,
    private val onItemClickListener: OnItemClickListener,
    private val onItemLongClickListener: OnItemLongClickListener
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    private var lastChecked: CheckBox? = null
    private var lastCheckedPos = 0
    var itemsToDelete: ArrayList<Note> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notes_item, parent, false)
        )
    }



    override fun getItemCount(): Int {
        return items?.size!!
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(items!![position], onItemClickListener, onItemLongClickListener)
        holder.checkBox.isChecked = items[position].isSelected
        holder.checkBox.tag = position

        if (position === 0 && items[0].isSelected && holder.checkBox.isChecked) {
            lastChecked = holder.checkBox
            lastCheckedPos = 0
        }

        holder.checkBox.setOnClickListener { v ->
            val cb = v as CheckBox
            val clickedPos = (cb.tag as Int).toInt()
            if (cb.isChecked) {

                lastChecked = cb
                lastCheckedPos = clickedPos

                itemsToDelete.add(items[clickedPos])

            } else {
                lastChecked = null
                itemsToDelete.remove(items[clickedPos])

            }
            items[clickedPos].isSelected = cb.isChecked
            Log.e("setNotesToDelete",Arrays.toString(itemsToDelete.toArray()))

        }

    }


    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val note_title = view.notes_title
        private val note_body = view.notes_body
        val checkBox = view.checker

        @SuppressLint("LongLogTag", "UseValueOf")
        fun bind(
            note: Note,
            onItemClickListener: OnItemClickListener,
            onItemLongClickListener: OnItemLongClickListener
        ) {

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(note)


            }
            note_title.text = note.title
            note_body.text = note.body


        }

    }
}

