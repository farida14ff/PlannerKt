package com.example.plannerkt.listeners

import com.example.plannerkt.models.Note

interface OnItemClickListener {
    fun onItemClick(note: Note?)
}