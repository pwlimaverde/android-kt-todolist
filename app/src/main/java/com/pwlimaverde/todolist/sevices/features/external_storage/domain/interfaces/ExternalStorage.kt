package com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces

import com.pwlimaverde.todolist.core.models.Registro
import kotlinx.coroutines.flow.Flow

interface ExternalStorage {
    suspend fun readDocument(registro: Registro):Map<String, Any>
//    suspend fun readStreamDocument(registro: Registro): Flow<Map<String, Any>>
//    suspend fun readCollection(registro: Registro, colecao: String): List<Map<String, Any>>
//    suspend fun readStreamCollection(registro: Registro, colecao: String): Flow<Map<String, Any>>
//    suspend fun write(registro: Registro)
//    suspend fun remove(registro: Registro)
}