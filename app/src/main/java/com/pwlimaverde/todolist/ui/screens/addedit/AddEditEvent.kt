package com.pwlimaverde.todolist.ui.screens.addedit

sealed interface AddEditEvent {
    data class IdChanged(val id: Long) : AddEditEvent
    data class TitleChanged(val title: String) : AddEditEvent
    data class DescriptionChanged(val description: String) : AddEditEvent
    data object Save : AddEditEvent
}