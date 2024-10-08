package com.pwlimaverde.todolist.ui.screens.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pwlimaverde.todolist.sevices.features.FeaturesServerPresenter
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoDatabaseProvider
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoRoomDatasource
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoRoomRepository
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase.LocalStorageUseCase
import com.pwlimaverde.todolist.ui.UiEvent
import com.pwlimaverde.todolist.ui.theme.TodoListTheme

@Composable
fun AddEditScreen(
    id: Long?,
    navigateBack: () -> Unit,
    addEditViewModel: AddEditViewModel
) {

    val title = addEditViewModel.title
    val description = addEditViewModel.description

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        addEditViewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(uiEvent.message)
                }

                UiEvent.NavigateBack -> {
                    navigateBack()
                }

                is UiEvent.Navigate<*> -> {}
            }
        }
    }

    AddEditContent(
        id = id,
        title = title,
        description = description,
        snackbarHostState = snackbarHostState,
        onEvent = addEditViewModel::onEvent
    )
}

@Composable
fun AddEditContent(
    id: Long? = null,
    title: String = "",
    description: String?,
    snackbarHostState: SnackbarHostState,
    onEvent: (AddEditEvent) -> Unit
) {

    LaunchedEffect(key1= true) {
        if (id != null) {
            onEvent(AddEditEvent.IdChanged(id))
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(AddEditEvent.Save)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title ->
                    onEvent(AddEditEvent.TitleChanged(title))
                },
                placeholder = { Text(text = "Title") },
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description ?: "",
                onValueChange = { description ->
                    onEvent(AddEditEvent.DescriptionChanged(description))
                },
                placeholder = { Text(text = "Description (optional)") },
            )

        }
    }
}

@Preview
@Composable
private fun AddEditContentPreview() {
    TodoListTheme {
        AddEditContent(
            title = "Title",
            description = "Description",
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}