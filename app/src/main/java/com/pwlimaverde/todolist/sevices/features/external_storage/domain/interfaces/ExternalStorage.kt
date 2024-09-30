package com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces

import com.pwlimaverde.todolist.core.models.Registro
import com.pwlimaverde.todolist.core.models.Todo
import kotlinx.coroutines.flow.Flow

interface ExternalStorage {
    suspend fun readDocument(registro: Registro):Todo?
//    suspend fun readStreamDocument(registro: Registro): Flow<Map<String, Any>>
    suspend fun readCollectionRaiz(colecao: String): List<Map<String, Any>>
    suspend fun readStreamCollectionRaiz(colecao: String): Flow<List<Todo>>
    suspend fun write(registro: Registro)
    suspend fun remove(registro: Registro)
}