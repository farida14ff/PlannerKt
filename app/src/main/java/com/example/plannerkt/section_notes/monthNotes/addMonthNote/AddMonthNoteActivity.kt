package com.example.plannerkt.section_notes.monthNotes.addMonthNote

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.example.plannerkt.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_note.*

class AddMonthNoteActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    var editableStatus: Boolean = false
    var notesId: Int = 0
    val db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        sharedPreferences =getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

        editableStatus = sharedPreferences!!.getBoolean("editableStatus",false)
        notesId = sharedPreferences!!.getInt("notesId",0)

        initViews()
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        val noteEditText = findViewById<EditText>(R.id.note_edit_text)
        noteEditText.requestFocus()
        noteEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                if (noteEditText.text.isNotEmpty()) {
                    note_ready_LL.visibility = View.VISIBLE

                } else {
                    note_ready_LL.visibility = View.GONE

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })



        if (editableStatus) {

        }

        val goBackBtn = findViewById<ImageView>(R.id.go_back_icon)
        goBackBtn.setOnClickListener {
            if(noteEditText?.text != null && noteEditText.text?.trim()?.length!! > 0){
                if (!editableStatus) {
                    setFbNote(noteEditText.text.toString())
                    Log.e("editableStatus addnote", "false")

                }
                else {
//                    notesViewModel?.updateNote(note.body,note.id)
//                    notesViewModel?.updateNote(note)
                    Log.e("editableStatus addnote", "true")

                }
            }

            finish()

        }

        val doneBtn = findViewById<LinearLayout>(R.id.note_ready_LL)
        doneBtn.setOnClickListener {
            if(noteEditText?.text != null && noteEditText.text?.trim()?.length!! > 0) {
//                val note = Note(0, "", noteEditText.text.toString())

                if (!editableStatus) {
                    setFbNote(noteEditText.text.toString())
                    Log.e("editableStatus addnote", "false")

                }
                else {
//                    notesViewModel?.updateNote(note.body,note.id)
//                    notesViewModel?.updateNote(note)
                    Log.e("editableStatus addnote", "true")

                }
            }
            finish()


        }


    }

    private fun setFbNote(text: String){

        val fastNote = hashMapOf(
            "text" to text,
            "sentAt" to FieldValue.serverTimestamp()

//            "last" to "Lovelace",
//            "born" to 1815
        )

        db.collection(COLLECTION_NAME)
            .add(fastNote)
            .addOnSuccessListener { documentReference ->
                Log.d("ANA", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("ANA", "Error adding document", e)
            }

        Log.e("setFbNoteBG","set")

    }

    companion object {
        const val COLLECTION_NAME = "monthNotes"
    }
}