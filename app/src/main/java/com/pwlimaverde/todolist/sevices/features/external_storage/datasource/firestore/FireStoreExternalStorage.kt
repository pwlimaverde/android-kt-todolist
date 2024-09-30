package com.pwlimaverde.todolist.sevices.features.external_storage.datasource.firestore


import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.snapshots
import com.pwlimaverde.todolist.core.models.Registro
import com.pwlimaverde.todolist.core.models.Todo
import com.pwlimaverde.todolist.core.models.mapToDataClass
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces.ExternalStorage
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.random.Random

class FireStoreExternalStorage(private val db: FirebaseFirestore) : ExternalStorage {
    override suspend fun readDocument(registro: Registro): Todo? {
        try {

            val reference = documentReference(registro)

            val snapshot = reference.get().await()

            if (snapshot.exists() && snapshot.data != null) {
                val data = mapToDataClass(snapshot.data!!, Todo::class)
                return data
            }else{
                return null
            }


        } catch (_: Exception) {
            throw Exception("Erro ao ler o documento")
        }
    }


    //override suspend fun readStreamDocument(registro: Registro): Flow<Map<String, Any>> {
//    TODO("Not yet implemented")
//}
//
    override suspend fun readCollectionRaiz(
        colecao: String
    ): List<Map<String, Any>> {

        val collection = db.collection(colecao)
        val snapshot = collection.get().await()

        val data = snapshot.documents.map { document ->
            document.data ?: emptyMap()
        }
        return data
    }


    override suspend fun readStreamCollectionRaiz(
        colecao: String
    ): Flow<List<Todo>> {
        try {

            val collection = db.collection(colecao)
            val snapshot = collection.snapshots()

            val data = snapshot.map { documents ->
                documents.map { document ->
                    convertMapToTodo(document)
                }
            }
            return data
        } catch (_: Exception) {
            throw Exception("Erro ao ler a coleção")
        }
    }

    override suspend fun write(registro: Registro) {
        var caminho = db.collection(registro.colecao).document(registro.documento)

        if (registro.dados != null) {
            caminho.set(registro.dados)
        }
        var subData = registro.subcolecao
        while (subData != null) {
            caminho = caminho.collection(subData.colecao).document(subData.documento)
            if (subData.dados != null) {
                caminho.set(subData.dados!!)
            }
            subData = subData.subcolecao
        }


    }

    override suspend fun remove(registro: Registro) {
        val reference = documentReference(registro)
        reference.delete()
    }

    private fun documentReference(registro: Registro): DocumentReference {
        var caminho = db.collection(registro.colecao).document(registro.documento)
        var subData = registro.subcolecao
        while (subData != null) {
            caminho = caminho.collection(subData.colecao).document(subData.documento)
            subData = subData.subcolecao
        }
        return caminho
    }

    private fun convertMapToTodo(document: QueryDocumentSnapshot): Todo {

        return Todo(
            id = document.id.toLong(),
            title = document.getString("title") ?: "Erro ao carregar!",
            description = document.getString("description"),
            isCompleted = document.getBoolean("isCompleted") ?: false
        )

    }

}