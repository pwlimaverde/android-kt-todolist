package com.pwlimaverde.todolist.sevices.features.local_storage.domain.interfaces

import com.pwlimaverde.todolist.core.models.Todo
import kotlinx.coroutines.flow.Flow

interface LocalStorage {
    suspend fun insert(title: String, description: String?, id: Long? = null)

    suspend fun updateCompleted(id: Long, isCompleted: Boolean)

    suspend fun delete(id: Long)

    suspend fun getAll(): Flow<List<Todo>>

    suspend fun getBy(id: Long): Todo?
}