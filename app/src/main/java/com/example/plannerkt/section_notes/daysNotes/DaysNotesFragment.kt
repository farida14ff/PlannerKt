package com.example.plannerkt.section_notes.daysNotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plannerkt.R
import com.example.plannerkt.adapters.DayAndMonthNotesAdapter
import com.example.plannerkt.listeners.OnItemClickListener
import com.example.plannerkt.listeners.OnItemLongClickListener
import com.example.plannerkt.models.Note
import com.example.plannerkt.section_notes.daysNotes.addDayNote.AddDayNoteActivity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_days_notes.view.*
import kotlin.collections.ArrayList

class DaysNotesFragment : Fragment() {

    lateinit var adapter: DayAndMonthNotesAdapter
    var deleteNoteBtn: LinearLayout? = null
    var editor: SharedPreferences.Editor? = null
    private val db = Firebase.firestore
    private val dayNotesList= ArrayList<Note>()
    private var sharedPreferences: SharedPreferences? = null
    var notesId: Long = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_days_notes, container, false)
        setHasOptionsMenu(true);
        initViews(view)
        initList(view)
        getFbNotes()

        sharedPreferences = activity?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

//        notesId = sharedPreferences!!.getLong("mNoteId",0)
//        Log.e("notesId getter",notesId.toString())

        return view
    }

    private fun getFbNotes() {
        db.collection(COLLECTION_NAME)
            .orderBy("sentAt")
            .addSnapshotListener { snapshots: QuerySnapshot?, _: FirebaseFirestoreException? ->
                try {
                    for (change in snapshots!!.documentChanges) {
                        when (change.type) {
                            DocumentChange.Type.ADDED -> {
                                val note: Note =
                                    change.document.toObject(Note::class.java)
                                dayNotesList.add(0, note)
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
        super.onCreateOptionsMenu(menu,inflater);
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(view: View) {
        deleteNoteBtn = view.findViewById(R.id.more)

        view.add_day_note_btn.setOnClickListener {
            editor?.putBoolean("editableStatus",false)?.commit()
            startActivity(Intent(context, AddDayNoteActivity::class.java))

        }
    }


    private fun initList(view: View) {
        adapter = DayAndMonthNotesAdapter(dayNotesList, object :
            OnItemClickListener {
            override fun <T> onItemClick(listItem: T) {
                editor?.putBoolean("editableStatus",true)?.commit()
                startActivity(Intent(context, AddDayNoteActivity::class.java))

            }

        },
            object : OnItemLongClickListener {
                override fun <T> onItemLongClick(listItem: T) {
                    // more.visibility = View.VISIBLE

                }
            })


        val layoutManager = LinearLayoutManager(context)
            view.recycler_day_notes.layoutManager = layoutManager
            view.recycler_day_notes.adapter = adapter


    }

    companion object {
        const val COLLECTION_NAME = "dayNotes"
    }



}