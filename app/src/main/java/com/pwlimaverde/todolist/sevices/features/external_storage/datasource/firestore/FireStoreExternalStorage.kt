package com.pwlimaverde.todolist.sevices.features.external_storage.datasource.firestore


import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.pwlimaverde.todolist.core.models.Registro
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces.ExternalStorage
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FireStoreExternalStorage(private val db: FirebaseFirestore) : ExternalStorage {
    override suspend fun readDocument(registro: Registro): Map<String, Any> {
        try {

            val reference = documentReference(registro)

            val snapshot = reference.get().await()

            val data = snapshot.data ?: emptyMap()
            return data

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

    //
//override suspend fun readStreamCollection(
//    registro: Registro,
//    colecao: String
//): Flow<Map<String, Any>> {
//    TODO("Not yet implemented")
//}
//
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
}