package com.pwlimaverde.todolist.ui.screens.list

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.pwlimaverde.todolist.core.models.Registro
import com.pwlimaverde.todolist.core.models.Todo
import com.pwlimaverde.todolist.sevices.features.FeaturesServerPresenter
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoDatabaseProvider
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoRoomDatasource
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoRoomRepository
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase.LocalStorageUseCase
import com.pwlimaverde.todolist.ui.UiEvent
import com.pwlimaverde.todolist.ui.components.TodoItem
import com.pwlimaverde.todolist.ui.navigation.AddEtiteRoute
import com.pwlimaverde.todolist.ui.theme.TodoListTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit,
    listViewModel: ListViewModel
) {

    val todos by listViewModel.todos.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {

        listViewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(uiEvent.message)
                }

                UiEvent.NavigateBack -> {}

                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is AddEtiteRoute -> {
                            navigateToAddEditScreen(uiEvent.route.id)
                        }
                    }
                }
            }
        }
    }

    ListContent(
        todos = todos,
        snackbarHostState = snackbarHostState,
        onEvent = listViewModel::onEvent
    )
}

@Composable
fun ListContent(
    todos: List<Todo>,
    snackbarHostState: SnackbarHostState,
    onEvent: (ListEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ListEvent.AddEdit(null))
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onCompletedChange = {
                        onEvent(ListEvent.CompleteChange(todo.id, it))
                    },
                    onItemClicked = {
                        onEvent(ListEvent.AddEdit(todo.id))
                    },
                    onDeleteClicked = {
                        onEvent(ListEvent.Delete(todo.id))
                    }
                )
                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    TodoListTheme {
        ListContent(
            listOf(
                Todo(id = 1, title = "Todo 1", description = "Description 1", isCompleted = false),
                Todo(id = 2, title = "Todo 2", description = "Description 2", isCompleted = true),
            ),
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}