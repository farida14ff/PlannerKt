package com.example.plannerkt.section_notes.fastNotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plannerkt.BuildConfig
import com.example.plannerkt.R
import com.example.plannerkt.adapters.NotesAdapter
import com.example.plannerkt.listeners.OnItemClickListener
import com.example.plannerkt.listeners.OnItemLongClickListener
import com.example.plannerkt.models.FastNote
import com.example.plannerkt.models.Note
import com.example.plannerkt.section_notes.addNote.AddNoteActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_notes.view.*
import java.util.*


class FastNotesFragment : Fragment() {

    private var notesViewModel: NotesViewModel? = null
    private val fastNoteList= ArrayList<FastNote>()
    lateinit var adapter: NotesAdapter
    var deleteNoteBtn: LinearLayout? = null
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null


    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        setHasOptionsMenu(true);
        initViews(view)
        initSharedPref()
        initList(view)
        getFbNotes()
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
//        notesViewModel?.getNotes()?.observe(this, Observer<List<Note>> { this.initList(it) })

        deleteNoteBtn = view.findViewById(R.id.more)

//        deleteNoteBtn?.setOnClickListener{
//            notesViewModel?.deleteAllItems(this.adapter.itemsToDelete)
//            Log.e("setNotesToDelete NF", Arrays.toString(this.adapter.itemsToDelete.toArray()))
//
//        }

        val addNoteBtn = view.findViewById<FloatingActionButton>(R.id.add_note_btn)
        addNoteBtn.setOnClickListener {
            editor?.putBoolean("editableStatus",false)?.commit()
            startActivity(Intent(context,AddNoteActivity::class.java))

        }
    }

    private fun getFbNotes() {

        db.collection("fastNotes")
            .addSnapshotListener { snapshots: QuerySnapshot?, _: FirebaseFirestoreException? ->
                try {

                    for (change in snapshots!!.documentChanges) {
                        when (change.type) {
                            DocumentChange.Type.ADDED -> {
                                val fastNote: FastNote =
                                    change.document.toObject(FastNote::class.java)
                                fastNoteList.add(0, fastNote)

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

//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("FNF", "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("FNF", "Error getting documents.", exception)
//            }

//        //.orderBy("sentAt")
//        FirebaseFirestore.getInstance().collection("fastNotes")
//            .addSnapshotListener { snapshots: QuerySnapshot?, _: FirebaseFirestoreException? ->
//                try {
////                    if (BuildConfig.DEBUG && snapshots == null) {
////                        error("Assertion failed")
////                    }
//                    for (change in snapshots!!.documentChanges) {
//                        when (change.type) {
//                            DocumentChange.Type.ADDED -> {
//                                val fastNote: FastNote =
//                                    change.document.toObject(FastNote::class.java)
//                                fastNoteList.add(0, fastNote)
//
//                            }
//                            DocumentChange.Type.REMOVED -> {
//                            }
//                            DocumentChange.Type.MODIFIED -> {
//                            }
//                        }
//                    }
//                } catch (ex: NullPointerException) {
//                    Log.e("npExp","getFbNotes")
//                }
//                adapter.notifyDataSetChanged()
//            }
            }
    }


    private fun initList(view: View) {
        adapter = NotesAdapter(fastNoteList, object :
            OnItemClickListener {
            override fun <T> onItemClick(listItem: T) {
                editor?.putBoolean("editableStatus",true)?.commit()
                if ((listItem as FastNote) != null) {
//                    editor?.putInt("notesId",fastNote.id)?.commit()
                }

                startActivity(Intent(context,AddNoteActivity::class.java))

            }

        },
            object : OnItemLongClickListener {
                override fun onItemLongClick(note: Note?) {
//                    more.visibility = View.VISIBLE

                }
            })


        val layoutManager = GridLayoutManager(activity, 2)
//        layoutManager.stackFromEnd = true
        view.recycler_notes.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
        view.recycler_notes.adapter = adapter

    }





//
//    fun deleteFromDB(note: Note) {
//        notesViewModel?.deleteItem(note.id)
//    }


}