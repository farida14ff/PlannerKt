package com.example.plannerkt.notes.addNote

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.plannerkt.R
import com.example.plannerkt.models.Note
import com.example.plannerkt.notes.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {

    private var notesViewModel: NotesViewModel? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)
        initViews(view)
        initList(view)
        return view
    }

    private fun initList(view: View?) {


    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews(view: View?) {
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

//        notesViewModel?.getNotes()?.observe(this, Observer<List<Note>> { this.renderMessges(it) })

        val noteEditText = view?.findViewById<EditText>(R.id.note_edit_text)
        noteEditText?.requestFocus()
        noteEditText?.addTextChangedListener(object : TextWatcher {
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
//        noteEditText?.filters =
//            arrayOf<InputFilter>(InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT))

        val goBackBtn = view!!.findViewById<ImageView>(R.id.go_back_icon)
        goBackBtn.setOnClickListener {
            if(noteEditText?.text != null && noteEditText.text?.trim()?.length!! > 0){
                val note = Note(0, "", noteEditText.text.toString())
                notesViewModel?.setNotes(note)
            }

//            noteEditText?.text?.isNotEmpty().let {

//            }
            fragmentManager!!.popBackStack()

        }

//        val currentDateTime = LocalDateTime.now()
//
//        val monthAndDate = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
//        val time = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
//        val formattedDate: String = monthAndDate.substring(0,7)
//        todaysDate.text = "$formattedDate $time"
//        Log.e("formattedDate", formattedDate)
//        Log.e("time", time)

        val doneBtn = view.findViewById<LinearLayout>(R.id.note_ready_LL)
        doneBtn.setOnClickListener {
            if(noteEditText?.text != null && noteEditText.text?.trim()?.length!! > 0) {

                val note = Note(0, "", noteEditText.text.toString())
                notesViewModel?.setNotes(note)
            }
            fragmentManager!!.popBackStack()


        }


    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }


}