package com.pwlimaverde.todolist.core.models

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
)

//fake objects
val todo1 = Todo(
    id = 1,
    title = "Todo 1",
    description = "Description for todo 1",
    isCompleted = false
)

val todo2 = Todo(
    id = 2,
    title = "Todo 2",
    description = "Description for todo 2",
    isCompleted = true
)

val todo3 = Todo(
    id = 3,
    title = "Todo 3",
    description = "Description for todo 3",
    isCompleted = false
)