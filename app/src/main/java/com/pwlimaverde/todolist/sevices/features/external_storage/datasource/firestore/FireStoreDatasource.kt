package com.pwlimaverde.todolist.sevices.features.external_storage.datasource.firestore

import com.pwlimaverde.return_success_or_error_kt.parameters.NoParams
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces.ExternalStorage
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ESData

class FireStoreDatasource(private val repositoryEs: ExternalStorage) : ESData {
    override suspend fun invoke(parameters: NoParams): ExternalStorage {
        try {
            return repositoryEs
        } catch (
            e: Exception
        ) {
            throw e
        }
    }
}