package com.pwlimaverde.todolist.ui.screens.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwlimaverde.todolist.sevices.features.FeaturesServerPresenter
import com.pwlimaverde.todolist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val id: Long? = null,
    private val featuresServerPresenter: FeaturesServerPresenter
) : ViewModel() {

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf<String?>(null)
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        id?.let { id ->
            viewModelScope.launch {
                val todo = featuresServerPresenter.getBy(id)
                title = todo?.title?:""
                description = todo?.description
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleChanged -> {
                title = event.title
            }

            is AddEditEvent.DescriptionChanged -> {
                description = event.description
            }

            AddEditEvent.Save -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {
        viewModelScope.launch {
            if (title.isBlank()) {
                _uiEvent.send(UiEvent.ShowSnackbar("O Titulo não pode ser vazio"))
                return@launch
            }
            featuresServerPresenter.insert(title, description, id)
            _uiEvent.send(UiEvent.NavigateBack)
        }
    }
}