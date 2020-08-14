package com.example.plannerkt.section_chat.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plannerkt.R

class EventsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = LayoutInflater.from(context).inflate(R.layout.fragmnet_events,container,false)
        return  root

    }
}