package com.pwlimaverde.todolist.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwlimaverde.todolist.core.models.Todo
import com.pwlimaverde.todolist.sevices.features.FeaturesServerPresenter
import com.pwlimaverde.todolist.ui.UiEvent
import com.pwlimaverde.todolist.ui.navigation.AddEtiteRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val featuresServerPresenter: FeaturesServerPresenter
) : ViewModel() {

    val todos = getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.Delete -> {
                delete(event.id)
            }

            is ListEvent.CompleteChange -> {
                completeChange(event.id, event.isCompleted)
            }

            is ListEvent.AddEdit -> {
                addEdit(event.id)
            }
        }
    }

    private fun delete(id: Long) {
        viewModelScope.launch {
            featuresServerPresenter.delete(id)
            _uiEvent.send(UiEvent.ShowSnackbar("Tarefa removida com Sucesso!"))
        }
    }

    private fun completeChange(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            featuresServerPresenter.updateCompleted(id, isCompleted)
        }
    }

    private fun getAll(): Flow<List<Todo>> = flow {
        emitAll(featuresServerPresenter.getAll())
    }

    private fun addEdit(id: Long?) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(AddEtiteRoute(id)))
        }
    }
}