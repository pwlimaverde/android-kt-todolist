package com.pwlimaverde.todolist.sevices.features


import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pwlimaverde.return_success_or_error_kt.core.ErrorReturn
import com.pwlimaverde.return_success_or_error_kt.core.SuccessReturn
import com.pwlimaverde.return_success_or_error_kt.parameters.NoParams
import com.pwlimaverde.todolist.core.models.Registro
import com.pwlimaverde.todolist.core.models.Todo
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces.ExternalStorage
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ESUseCase
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ExternalStorageUseCase
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.interfaces.LocalStorage
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase.LSUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FeaturesServerPresenter(
    private val localStorage: LSUseCase,
    private val externalStorage: ExternalStorageUseCase

) {
    suspend fun insert(title: String, description: String?, id: Long? = null): Boolean {
        when (val data = localStorage.invokeCoroutine(NoParams(null))) {
            is SuccessReturn<LocalStorage> -> {
                data.result.insert(title, description, id)
                return true
            }

            is ErrorReturn<LocalStorage> -> return false
        }
    }

    suspend fun updateCompleted(id: Long, isCompleted: Boolean): Boolean {
        when (val data = localStorage.invokeCoroutine(NoParams(null))) {
            is SuccessReturn<LocalStorage> -> {
                data.result.updateCompleted(id, isCompleted)
                return true
            }

            is ErrorReturn<LocalStorage> -> return false
        }
    }

    suspend fun delete(id: Long): Boolean {
        when (val data = localStorage.invokeCoroutine(NoParams(null))) {
            is SuccessReturn<LocalStorage> -> {
                data.result.delete(id)
                return true
            }

            is ErrorReturn<LocalStorage> -> return false
        }
    }

    suspend fun getAll(): Flow<List<Todo>> {
        return when (val data = localStorage.invokeCoroutine(NoParams(null))) {
            is SuccessReturn<LocalStorage> -> {
                data.result.getAll()
            }

            is ErrorReturn<LocalStorage> -> emptyFlow()
        }
    }

    suspend fun getBy(id: Long): Todo? {
        return when (val data = localStorage.invokeCoroutine(NoParams(null))) {
            is SuccessReturn<LocalStorage> -> {
                data.result.getBy(id)
            }

            is ErrorReturn<LocalStorage> -> null
        }
    }

    suspend fun readDocument(registro: Registro): Map<String, Any> {
        try {
            when (val data = externalStorage.invokeCoroutine(NoParams(null))) {
                is SuccessReturn<ExternalStorage> -> {
                    val teste = data.result.readDocument(registro)
                    return teste
                }

                is ErrorReturn<ExternalStorage> -> return emptyMap()
            }
        } catch (e: Exception) {
            Log.e(TAG, "read: ${e.message}")
            return emptyMap()
        }
    }

    suspend fun readCollection(registro: Registro, colecao: String): List<Map<String, Any>> {
        try {
            when (val data = externalStorage.invokeCoroutine(NoParams(null))) {
                is SuccessReturn<ExternalStorage> -> {
                    val teste = data.result.readCollection(registro, colecao)
                    return teste
                }

                is ErrorReturn<ExternalStorage> -> return emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "read: ${e.message}")
            return emptyList()
        }
    }
}