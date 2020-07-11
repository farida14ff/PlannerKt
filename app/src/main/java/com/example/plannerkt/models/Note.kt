package com.example.plannerkt.models

import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*


class Note() : Serializable {
    val text: String = ""

    @ServerTimestamp
    private val sentAt: Date? = null


    var isSelected: Boolean = false


}
