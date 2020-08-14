package com.example.plannerkt.section_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plannerkt.R
import com.example.plannerkt.adapters.FixedTabsPagerAdapter
import com.example.plannerkt.section_chat.chats.ChatFragment
import com.example.plannerkt.section_chat.events.EventsFragment
import com.example.plannerkt.section_chat.users.UsersFragment
import kotlinx.android.synthetic.main.fragment_chat_section.view.*


class ChatSectionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View =  inflater.inflate(R.layout.fragment_chat_section, container, false)

        initTabLayout(root)

        return root
    }

    private fun initTabLayout(root: View) {

        val tabs = root.tabsChat
        val viewPager = root.viewPagerChat

        val chatFragment =
            ChatFragment()
        val eventsFragment =
            EventsFragment()
        val usersFragment =
            UsersFragment()

        val adapter = FixedTabsPagerAdapter(childFragmentManager)
        adapter.addFragment(chatFragment,CHATS_TITLE)
        adapter.addFragment(eventsFragment, EVENTS_TITLE)
        adapter.addFragment(usersFragment, USERS_TITLE)
        viewPager.adapter = adapter

        tabs.addTab(tabs.newTab().setText(CHATS_TITLE))
        tabs.addTab(tabs.newTab().setText(EVENTS_TITLE))
        tabs.addTab(tabs.newTab().setText(USERS_TITLE))

        tabs.setupWithViewPager(viewPager)

    }

    companion object {
        const val CHATS_TITLE = "CHATS"
        const val EVENTS_TITLE = "EVENTS"
        const val USERS_TITLE = "USERS"
    }

}