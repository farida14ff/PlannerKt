package com.example.plannerkt.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.plannerkt.R
import com.example.plannerkt.halpers.OnItemClickListener
import com.example.plannerkt.halpers.OnItemLongClickListener
import com.example.plannerkt.models.Note
import kotlinx.android.synthetic.main.item_notes.view.*
import java.util.*
import kotlin.collections.ArrayList

class DayAndMonthNotesAdapter(
    private val items: ArrayList<Note>?,
    private val onItemClickListener: OnItemClickListener,
    private val onItemLongClickListener: OnItemLongClickListener
): RecyclerView.Adapter<DayAndMonthNotesAdapter.DayAndMonthNotesViewHolder>() {

    private var lastChecked: CheckBox? = null
    private var lastCheckedPos = 0
    var itemsToDelete: ArrayList<Note> = ArrayList()
    lateinit var note: Note
//    var editor: SharedPreferences.Editor? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAndMonthNotesViewHolder {
        return DayAndMonthNotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_day_and_month_note,parent,false))
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    override fun onBindViewHolder(holder: DayAndMonthNotesViewHolder, position: Int) {


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
            Log.e("setNotesToDelete", Arrays.toString(itemsToDelete.toArray()))

        }
    }

    inner class DayAndMonthNotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val note_body = view.notes_body
        val checkBox = view.checker!!
        private val sharedPreference =  view.context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        private var editor = sharedPreference.edit()

        @SuppressLint("LongLogTag", "UseValueOf")
        fun bind(
            note2: Note,
            onItemClickListener: OnItemClickListener,
            onItemLongClickListener: OnItemLongClickListener
        ) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(note2)
                editor?.putString("notesPosition",items!![position].text)?.commit()
//                editor?.putString("notesPosition","defVal")?.commit()

                Log.e("notesPosition setter",items!![position].text)
            }

            note = note2
            val noteId = note.id




            note_body.text = note2.text


        }
    }
}