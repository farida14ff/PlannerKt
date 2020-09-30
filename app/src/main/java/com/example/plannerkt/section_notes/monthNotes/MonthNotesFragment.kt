package com.example.plannerkt.section_notes.monthNotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plannerkt.R
import com.example.plannerkt.adapters.DayAndMonthNotesAdapter
import com.example.plannerkt.halpers.OnItemClickListener
import com.example.plannerkt.halpers.OnItemLongClickListener
import com.example.plannerkt.models.Note
import com.example.plannerkt.section_notes.monthNotes.addMonthNote.AddMonthNoteActivity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_month_notes.view.*
import java.util.*

class MonthNotesFragment : Fragment() {

    private val monthNoteList= ArrayList<Note>()
    lateinit var adapter: DayAndMonthNotesAdapter
    var deleteNoteBtn: LinearLayout? = null
    var editor: SharedPreferences.Editor? = null
    private val db = Firebase.firestore
    private var sharedPreferences: SharedPreferences? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_month_notes, container, false)
        setHasOptionsMenu(true);
        initSharedPref()
        initViews(view)
        initList(view)
        getFbMonthNotes()

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

    private fun getFbMonthNotes() {
        db.collection(COLLECTION_NAME)
            .orderBy("sentAt")
            .addSnapshotListener { snapshots: QuerySnapshot?, _: FirebaseFirestoreException? ->
                try {

                    for (change in snapshots!!.documentChanges) {
                        when (change.type) {
                            DocumentChange.Type.ADDED -> {
                                val monthNote: Note =
                                    change.document.toObject(Note::class.java)
                                monthNoteList.add(0, monthNote)

                            }
                            DocumentChange.Type.REMOVED -> {
                            }
                            DocumentChange.Type.MODIFIED -> {
                            }
                        }
                    }
                } catch (ex: NullPointerException) {
                    Log.e("npExp", "getFbNotes")
                }
                adapter.notifyDataSetChanged()

            }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(view: View) {

//        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
//        notesViewModel?.getNotes()?.observe(this, Observer<List<Note>> { this.initList(it) })

        deleteNoteBtn = view.findViewById(R.id.more)
//        deleteNoteBtn?.setOnClickListener {
//            notesViewModel?.deleteAllItems(this.adapter.itemsToDelete)
//            Log.e("setNotesToDelete NF", Arrays.toString(this.adapter.itemsToDelete.toArray()))
//        }

        view.add_month_note_btn
            .setOnClickListener {
//            editor?.putBoolean("editableStatus", false)?.commit()
            startActivity(Intent(context, AddMonthNoteActivity::class.java))

        }
    }


    private fun initList(view: View) {
        adapter = DayAndMonthNotesAdapter(monthNoteList, object :
            OnItemClickListener {
            override fun <T> onItemClick(listItem: T) {
//                editor?.putBoolean("editableStatus", true)?.commit()
//                if (note != null) {
//                    editor?.putInt("notesId", note.id)?.commit()
//                }

//                startActivity(Intent(context, AddMonthNoteActivity::class.java))

            }

        },
            object : OnItemLongClickListener {
                override fun <T> onItemLongClick(listItem: T) {
//                    more.visibility = View.VISIBLE

                }
            })


        val layoutManager = LinearLayoutManager(context)
//        layoutManager.stackFromEnd = true
        view.recycler_month_notes.layoutManager = layoutManager
        view.recycler_month_notes.adapter = adapter

    }

    companion object {
        const val COLLECTION_NAME = "monthNotes"
    }


}