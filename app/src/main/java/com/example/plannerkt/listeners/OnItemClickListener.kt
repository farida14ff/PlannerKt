package com.example.plannerkt.listeners

//interface OnItemClickListener {
//    fun onItemClick(note: Note?)
//}

interface OnItemClickListener {
    fun <T> onItemClick(listItem: T)
}