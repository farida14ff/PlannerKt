package com.example.plannerkt.models

import java.io.Serializable


data class FastNote(
    val text: String
): Serializable {
    constructor() : this("")

    var isSelected: Boolean = false

}
