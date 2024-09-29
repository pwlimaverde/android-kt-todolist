package com.pwlimaverde.todolist.sevices.features.external_storage.datasource.firestore


import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pwlimaverde.todolist.core.models.Registro
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces.ExternalStorage
import kotlinx.coroutines.tasks.await

class FireStoreExternalStorage(private val db: FirebaseFirestore) : ExternalStorage {
    override suspend fun readDocument(registro: Registro): Map<String, Any> {
        try {

            var caminho = db.collection(registro.colecao).document(registro.documento)
            var subData = registro.subcolecao
            while (subData != null) {
                caminho = caminho.collection(subData.colecao).document(subData.documento)
                subData = subData.subcolecao
            }

            val snapshot = caminho.get().await()
            Log.e(TAG, "readDocument: ${snapshot.data}")
            val data = snapshot.data?: emptyMap()
            return data

        } catch (_: Exception) {
            throw Exception("Erro ao ler o documento")
        }
    }


//override suspend fun readStreamDocument(registro: Registro): Flow<Map<String, Any>> {
//    TODO("Not yet implemented")
//}
//
//override suspend fun readCollection(
//    registro: Registro,
//    colecao: String
//): List<Map<String, Any>> {
//    TODO("Not yet implemented")
//}
//
//override suspend fun readStreamCollection(
//    registro: Registro,
//    colecao: String
//): Flow<Map<String, Any>> {
//    TODO("Not yet implemented")
//}
//
//override suspend fun write(registro: Registro) {
//    TODO("Not yet implemented")
//}
//
//override suspend fun remove(registro: Registro) {
//    TODO("Not yet implemented")
//}
}