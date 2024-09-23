package com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room

import com.pwlimaverde.todolist.core.models.Todo
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.interfaces.LocalStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRoomRepository(
    private val dao: TodoDao
) : LocalStorage {
    override suspend fun insert(title: String, description: String?, id: Long?) {
        val entity = id?.let{
            dao.getBy(it)?.copy(
                title = title,
                description = description,
            )
        }?: TodoEntity(
            title = title,
            description = description,
            isCompleted = false
        )
        dao.insert(entity)
    }

    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val existingEntity = dao.getBy(id) ?: return
        val updatedEntity = existingEntity.copy(isCompleted = isCompleted)
        dao.insert(updatedEntity)
    }

    override suspend fun delete(id: Long) {
        val existingEntity = dao.getBy(id) ?: return
        dao.delete(existingEntity)
    }

    override suspend fun getAll(): Flow<List<Todo>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                convertEntityToTodo(entity)
            }
        }
    }

    override suspend fun getBy(id: Long): Todo? {
        return dao.getBy(id)?.let { entity ->
            convertEntityToTodo(entity)
        }
    }

    private fun convertEntityToTodo(todo: TodoEntity): Todo {
        return Todo(
            id = todo.id,
            title = todo.title,
            description = todo.description,
            isCompleted = todo.isCompleted
        )
    }
}