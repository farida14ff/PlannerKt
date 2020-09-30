package com.example.plannerkt.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.plannerkt.R
import com.example.plannerkt.halpers.OnItemClickListener
import com.example.plannerkt.halpers.OnItemLongClickListener
import com.example.plannerkt.models.FastNote
import kotlinx.android.synthetic.main.item_notes.view.*
import java.util.*
import kotlin.collections.ArrayList


class NotesAdapter(
    private val items: List<FastNote>?,
    private val onItemClickListener: OnItemClickListener,
    private val onItemLongClickListener: OnItemLongClickListener
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    private var lastChecked: CheckBox? = null
    private var lastCheckedPos = 0
    var itemsToDelete: ArrayList<FastNote> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notes, parent, false)
        )
    }



    override fun getItemCount(): Int {
        return items?.size!!
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(items!![position], onItemClickListener, onItemLongClickListener)
        holder.checkBox.isChecked = items[position].isSelected
        holder.checkBox.tag = position

        if (position == 0 && items[0].isSelected && holder.checkBox.isChecked) {
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

        private val note_body = view.notes_body
        val checkBox = view.checker

        @SuppressLint("LongLogTag", "UseValueOf")
        fun bind(
            fastNote: FastNote,
            onItemClickListener: OnItemClickListener,
            onItemLongClickListener: OnItemLongClickListener
        ) {

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(fastNote)


            }
            note_body.text = fastNote.body


        }

    }
}

