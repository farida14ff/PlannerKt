package com.example.plannerkt.section_notes.monthNotes

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plannerkt.R
import com.example.plannerkt.adapters.DayAndMonthNotesAdapter
import com.example.plannerkt.listeners.OnItemClickListener
import com.example.plannerkt.listeners.OnItemLongClickListener
import com.example.plannerkt.models.Note
import com.example.plannerkt.section_notes.addNote.AddNoteActivity
import com.example.plannerkt.section_notes.fastNotes.NotesViewModel
import kotlinx.android.synthetic.main.fragment_days_notes.*
import kotlinx.android.synthetic.main.fragment_days_notes.view.*
import kotlinx.android.synthetic.main.fragment_month_notes.*
import kotlinx.android.synthetic.main.fragment_month_notes.view.*
import java.util.*

class MonthNotesFragment : Fragment() {
    private var notesViewModel: NotesViewModel? = null
    lateinit var adapter: DayAndMonthNotesAdapter
    var deleteNoteBtn: LinearLayout? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_month_notes, container, false)
        setHasOptionsMenu(true);
        initViews(view)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(view: View) {

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        notesViewModel?.getNotes()?.observe(this, Observer<List<Note>> { this.initList(it) })

        deleteNoteBtn = view.findViewById(R.id.more)
        deleteNoteBtn?.setOnClickListener {
            notesViewModel?.deleteAllItems(this.adapter.itemsToDelete)
            Log.e("setNotesToDelete NF", Arrays.toString(this.adapter.itemsToDelete.toArray()))
        }

        view.add_month_note_btn
            .setOnClickListener {
            editor?.putBoolean("editableStatus", false)?.commit()
            startActivity(Intent(context, AddNoteActivity::class.java))

        }
    }


    private fun initList(notes: List<Note>?) {
        adapter = DayAndMonthNotesAdapter(notes, object :
            OnItemClickListener {
            override fun <T> onItemClick(listItem: T) {
                editor?.putBoolean("editableStatus", true)?.commit()
//                if (note != null) {
//                    editor?.putInt("notesId", note.id)?.commit()
//                }

                startActivity(Intent(context, AddNoteActivity::class.java))

            }

        },
            object : OnItemLongClickListener {
                override fun onItemLongClick(note: Note?) {
//                    more.visibility = View.VISIBLE

                }
            })


        val layoutManager = LinearLayoutManager(context)
//        layoutManager.stackFromEnd = true
        recycler_month_notes.layoutManager = layoutManager
        recycler_month_notes.adapter = adapter

    }

    fun deleteFromDB(note: Note) {
        notesViewModel?.deleteItem(note.id)
    }


}