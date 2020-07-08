package com.example.plannerkt.section_notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plannerkt.R
import com.example.plannerkt.adapters.FixedTabsPagerAdapter
import com.example.plannerkt.section_notes.daysNotes.DaysNotesFragment
import com.example.plannerkt.section_notes.fastNotes.FastNotesFragment
import com.example.plannerkt.section_notes.monthNotes.MonthNotesFragment
import kotlinx.android.synthetic.main.fragment_notes_section.view.*


class NotesSectionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root: View =  inflater.inflate(R.layout.fragment_notes_section, container, false)

        initTabLayout(root)


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

}