package com.example.plannerkt.section_notes.fastNotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plannerkt.R
import com.example.plannerkt.adapters.NotesAdapter
import com.example.plannerkt.halpers.OnItemClickListener
import com.example.plannerkt.halpers.OnItemLongClickListener
import com.example.plannerkt.models.FastNote
import com.example.plannerkt.section_notes.fastNotes.addNote.AddNoteActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_notes.view.*
import java.util.*
import kotlin.collections.ArrayList


class FastNotesFragment : Fragment() {

    private var notesViewModel: NotesViewModel? = null
    private val noteList= ArrayList<FastNote>()
    lateinit var adapter: NotesAdapter
    var deleteNoteBtn: LinearLayout? = null
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
//    private val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        setHasOptionsMenu(true);
        initViews(view)
        initSharedPref()
//        getFbNotes()
        return view
    }

    @SuppressLint("CommitPrefEdits")
    private fun initSharedPref() {
        sharedPreferences = activity?.getSharedPreferences(
            "myPreferences",
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences?.edit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(view: View) {

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        notesViewModel?.getNotes()?.observe(this, Observer<List<FastNote>> { this.initList(it,view)})

        deleteNoteBtn = view.findViewById(R.id.more)

        deleteNoteBtn?.setOnClickListener{
            notesViewModel?.deleteAllItems(this.adapter.itemsToDelete)
            Log.e("setNotesToDelete NF", Arrays.toString(this.adapter.itemsToDelete.toArray()))
        }

        val addNoteBtn = view.findViewById<FloatingActionButton>(R.id.add_note_btn)
        addNoteBtn.setOnClickListener {
            editor?.putBoolean("editableStatus",false)?.commit()
            startActivity(Intent(context,
                AddNoteActivity::class.java))

        }

    }


    private fun initList(fastNoteList:List<FastNote>, view: View) {

        adapter = NotesAdapter(fastNoteList.asReversed(), object :
            OnItemClickListener {
            override fun <T> onItemClick(listItem: T) {
                editor?.putBoolean("editableStatus",true)?.commit()
//                if ((listItem as FastNote) != null) {
////                    editor?.putInt("notesId",fastNote.id)?.commit()
//                }
//
//                startActivity(Intent(context,
//                    AddNoteActivity::class.java))

            }

        },
            object : OnItemLongClickListener {
                override fun <T> onItemLongClick(listItem: T) {
//                    more.visibility = View.VISIBLE

                }
            })


        val layoutManager = GridLayoutManager(activity, 2)
//        layoutManager.stackFromEnd = true
        view.recycler_notes.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
        view.recycler_notes.adapter = adapter

    }


}