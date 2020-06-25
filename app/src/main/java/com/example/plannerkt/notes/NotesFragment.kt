package com.example.plannerkt.notes

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plannerkt.R
import com.example.plannerkt.adapters.NotesAdapter
import com.example.plannerkt.listeners.OnItemClickListener
import com.example.plannerkt.listeners.OnItemLongClickListener
import com.example.plannerkt.models.Note
import com.example.plannerkt.notes.addNote.AddNoteFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_notes.*
import java.util.*


class NotesFragment : Fragment() {


    private var notesViewModel: NotesViewModel? = null

    lateinit var adapter: NotesAdapter
    var deleteNoteBtn: LinearLayout? = null
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        setHasOptionsMenu(true);
        initViews(view)
        initSharedPref()
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

        notesViewModel?.getNotes()?.observe(this, Observer<List<Note>> { this.initList(it) })

        deleteNoteBtn = view.findViewById(R.id.more)

        deleteNoteBtn?.setOnClickListener{
            notesViewModel?.deleteAllItems(this.adapter.itemsToDelete)
            Log.e("setNotesToDelete NF", Arrays.toString(this.adapter.itemsToDelete.toArray()))

        }




        val addNoteBtn = view.findViewById<FloatingActionButton>(R.id.add_note_btn)
        addNoteBtn.setOnClickListener {
//            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()

            editor?.putBoolean("editableStatus",false)?.commit()

            val addNoteFragment: Fragment = AddNoteFragment()
            fragmentManager!!.beginTransaction()
                .replace(R.id.main_container, addNoteFragment)
                .addToBackStack(null)
                .commit()

        }
    }


    private fun initList(notes: List<Note>?) {
        adapter = NotesAdapter(notes, object :
            OnItemClickListener {
            override fun onItemClick(note: Note?) {
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                editor?.putBoolean("editableStatus",true)?.commit()

                val addNoteFragment: Fragment = AddNoteFragment()
                fragmentManager!!.beginTransaction()
                    .replace(R.id.main_container, addNoteFragment)
                    .addToBackStack(null)
                    .commit()

//                notesViewModel?.updateNote(
//                    notesViewModel?.getNoteById(note.id)
//                )

            }

        },
            object : OnItemLongClickListener {
                override fun onItemLongClick(note: Note?) {
//                    more.visibility = View.VISIBLE

                }
            })


        val layoutManager = GridLayoutManager(activity, 2)
//        layoutManager.stackFromEnd = true
        recycler_notes.layoutManager = layoutManager
        recycler_notes.adapter = adapter

    }

    fun deleteFromDB(note: Note) {
        notesViewModel?.deleteItem(note.id)
    }


}