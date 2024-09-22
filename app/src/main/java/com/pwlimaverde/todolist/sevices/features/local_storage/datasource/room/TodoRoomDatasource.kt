package com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room

import com.pwlimaverde.return_success_or_error_kt.datasource.DataSource
import com.pwlimaverde.return_success_or_error_kt.parameters.NoParams
import com.pwlimaverde.todolist.core.models.Todo
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.interfaces.LocalStorage
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase.LSData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class TodoRoomDatasource(
    private val repository: TodoRoomRepository
):LSData {

    override suspend fun invoke(parameters: NoParams): LocalStorage {
        try {
            return repository
        }catch (
            e: Exception
        ) {
            throw e
        }
    }
}