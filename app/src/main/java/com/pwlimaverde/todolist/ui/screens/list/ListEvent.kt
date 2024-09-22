package com.pwlimaverde.todolist.ui.screens.list

sealed interface ListEvent {
    data class Delete(val id: Long) : ListEvent
    data class CompleteChange(val id: Long, val isCompleted: Boolean) : ListEvent
    data class AddEdit(val id: Long?) : ListEvent
}