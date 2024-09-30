package com.pwlimaverde.todolist.sevices.features


import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pwlimaverde.return_success_or_error_kt.core.ErrorReturn
import com.pwlimaverde.return_success_or_error_kt.core.SuccessReturn
import com.pwlimaverde.return_success_or_error_kt.parameters.NoParams
import com.pwlimaverde.todolist.core.models.Registro
import com.pwlimaverde.todolist.core.models.Todo
import com.pwlimaverde.todolist.core.models.todoToMap
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces.ExternalStorage
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ESUseCase
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ExternalStorageUseCase
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.interfaces.LocalStorage
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase.LSUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.util.UUID

class FeaturesServerPresenter(
    private val localStorage: LSUseCase,
    private val externalStorage: ExternalStorageUseCase

) {
    suspend fun insert(title: String, description: String?, id: Long? = null): Boolean {
//        when (val data = localStorage.invokeCoroutine(NoParams(null))) {
//            is SuccessReturn<LocalStorage> -> {
//                data.result.insert(title, description, id)
//                return true
//            }
//
//            is ErrorReturn<LocalStorage> -> return false
//        }
        try {
            if (id != null) {
                val todo = readDocument(Registro("todolist", id.toString()))
                if (todo != null) {
                    writeDocument(
                        Registro(
                            "todolist",
                            todo.id.toString(),
                            dados = todoToMap(todo.copy(title = title, description = description))
                        )
                    )
                    return true
                } else {
                    return false
                }
            } else {

                val uuid = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE

                writeDocument(
                    Registro(
                        "todolist",
                        uuid.toString(),
                        dados = todoToMap(
                            Todo(
                                id = uuid,
                                title = title,
                                description = description,
                                isCompleted = false
                            ),
                        )
                    ),
                )
                return true
            }
        } catch (e: Exception) {
            Log.e(TAG, "insert: ${e.message}")
            return false
        }
    }

    suspend fun updateCompleted(id: Long, isCompleted: Boolean): Boolean {
//        when (val data = localStorage.invokeCoroutine(NoParams(null))) {
//            is SuccessReturn<LocalStorage> -> {
//                data.result.updateCompleted(id, isCompleted)
//                return true
//            }
//
//            is ErrorReturn<LocalStorage> -> return false
//        }
        val todo = readDocument(Registro("todolist", id.toString()))
        if (todo != null) {
            writeDocument(
                Registro(
                    "todolist",
                    todo.id.toString(),
                    dados = todoToMap(todo.copy(isCompleted = isCompleted))
                )
            )
            return true
        } else {
            return false
        }
    }

    suspend fun delete(id: Long): Boolean {
//        when (val data = localStorage.invokeCoroutine(NoParams(null))) {
//            is SuccessReturn<LocalStorage> -> {
//                data.result.delete(id)
//                return true
//            }
//
//            is ErrorReturn<LocalStorage> -> return false
//        }
        removeDocument(Registro("todolist", id.toString()))
        return true
    }

    suspend fun getAll(): Flow<List<Todo>> {
//        return when (val data = localStorage.invokeCoroutine(NoParams(null))) {
//            is SuccessReturn<LocalStorage> -> {
//                data.result.getAll()
//            }
//
//            is ErrorReturn<LocalStorage> -> emptyFlow()
//        }
        return readStreamCollectionRaiz("todolist")
    }

//    suspend fun getBy(id: Long): Todo? {
//        return when (val data = localStorage.invokeCoroutine(NoParams(null))) {
//            is SuccessReturn<LocalStorage> -> {
//                data.result.getBy(id)
//            }
//
//            is ErrorReturn<LocalStorage> -> null
//        }
//    }

    suspend fun readDocument(registro: Registro): Todo? {
        try {
            when (val data = externalStorage.invokeCoroutine(NoParams(null))) {
                is SuccessReturn<ExternalStorage> -> {
                    val teste = data.result.readDocument(registro)
                    return teste
                }

                is ErrorReturn<ExternalStorage> -> return null
            }
        } catch (e: Exception) {
            Log.e(TAG, "read: ${e.message}")
            return null
        }
    }

    suspend fun readCollectionRaiz(colecao: String): List<Map<String, Any>> {
        try {
            when (val data = externalStorage.invokeCoroutine(NoParams(null))) {
                is SuccessReturn<ExternalStorage> -> {
                    val teste = data.result.readCollectionRaiz(colecao)
                    return teste
                }

                is ErrorReturn<ExternalStorage> -> return emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "read: ${e.message}")
            return emptyList()
        }
    }

    suspend fun readStreamCollectionRaiz(colecao: String): Flow<List<Todo>> {
        try {
            when (val data = externalStorage.invokeCoroutine(NoParams(null))) {
                is SuccessReturn<ExternalStorage> -> {
                    val teste = data.result.readStreamCollectionRaiz(colecao)
                    return teste
                }

                is ErrorReturn<ExternalStorage> -> return emptyFlow()
            }
        } catch (e: Exception) {
            Log.e(TAG, "read: ${e.message}")
            return emptyFlow()
        }
    }

    suspend fun removeDocument(registro: Registro) {
        try {
            when (val data = externalStorage.invokeCoroutine(NoParams(null))) {
                is SuccessReturn<ExternalStorage> -> {
                    data.result.remove(registro)
                }

                is ErrorReturn<ExternalStorage> -> {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "read: ${e.message}")
        }
    }

    suspend fun writeDocument(registro: Registro) {
        try {
            when (val data = externalStorage.invokeCoroutine(NoParams(null))) {
                is SuccessReturn<ExternalStorage> -> {
                    data.result.write(registro)
                }

                is ErrorReturn<ExternalStorage> -> {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "read: ${e.message}")
        }
    }
}