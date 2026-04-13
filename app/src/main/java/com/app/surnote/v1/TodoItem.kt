package com.app.surnote.v1

data class TodoItem(
    val taskName: String,
    val taskTime: String,
    val date: String,
    var isDone: Boolean = false
)