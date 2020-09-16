package com.example.plannerkt.section_notes

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plannerkt.MainActivity
import com.example.plannerkt.R
import com.example.plannerkt.adapters.FixedTabsPagerAdapter
import com.example.plannerkt.section_notes.daysNotes.DaysNotesFragment
import com.example.plannerkt.section_notes.fastNotes.FastNotesFragment
import com.example.plannerkt.section_notes.monthNotes.MonthNotesFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_notes_section.view.*


class NotesSectionFragment : Fragment() {


    var editor: SharedPreferences.Editor? = null
    var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root: View =  inflater.inflate(R.layout.fragment_notes_section, container, false)

        initTabLayout(root)
        initSharedPref()

        root.ic_logout.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext(),R.style.AlertDialogTheme)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want logout?")
            builder.setPositiveButton(R.string.logout) { _, _ ->
                Firebase.auth.signOut()
                editor?.putBoolean("login",false)?.commit()
                startActivity(Intent(context, MainActivity::class.java))
                activity?.finish()
            }

            builder.setNegativeButton(android.R.string.no) { _, _ ->
                null
            }


            builder.show()

        }


        return root
    }

    private fun initTabLayout(root: View) {

        val tabs = root.tabs
        val viewPager = root.viewPager

        val fastNotesFragment = FastNotesFragment()
        val daysNotesFragment = DaysNotesFragment()
        val monthNotesFragment = MonthNotesFragment()

        val adapter = FixedTabsPagerAdapter(childFragmentManager)
        adapter.addFragment(fastNotesFragment,FAST_NOTES_TITLE)
        adapter.addFragment(daysNotesFragment, DAY_NOTES_TITLE)
        adapter.addFragment(monthNotesFragment, MONTH_NOTES_TITLE)
        viewPager.adapter = adapter

        tabs.addTab(tabs.newTab().setText(FAST_NOTES_TITLE))
        tabs.addTab(tabs.newTab().setText(DAY_NOTES_TITLE))
        tabs.addTab(tabs.newTab().setText(MONTH_NOTES_TITLE))

        tabs.setupWithViewPager(viewPager)

    }

    companion object {
        const val FAST_NOTES_TITLE = "NOTES"
        const val DAY_NOTES_TITLE = "DAY"
        const val MONTH_NOTES_TITLE = "MONTH"
    }


    @SuppressLint("CommitPrefEdits")
    private fun initSharedPref() {
        sharedPreferences = activity?.getSharedPreferences(
            "myPreferences",
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences?.edit()
    }


}