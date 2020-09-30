package com.example.plannerkt.halpers

//interface OnItemClickListener {
//    fun onItemClick(note: Note?)
//}

interface OnItemClickListener {
    fun <T> onItemClick(listItem: T)
}