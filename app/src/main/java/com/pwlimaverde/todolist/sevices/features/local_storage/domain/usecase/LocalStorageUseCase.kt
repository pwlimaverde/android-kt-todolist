package com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase

import com.pwlimaverde.return_success_or_error_kt.core.ReturnSuccessOrError
import com.pwlimaverde.return_success_or_error_kt.datasource.DataSource
import com.pwlimaverde.return_success_or_error_kt.parameters.NoParams
import com.pwlimaverde.return_success_or_error_kt.usecase.UseCaseCallData
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.interfaces.LocalStorage

class LocalStorageUseCase(dataSource: LSData) : LSUseCase(dataSource) {
    override suspend fun invoke(parameters: NoParams): ReturnSuccessOrError<LocalStorage> {
        return resultDatasource(parameters)
    }
}

typealias LSUseCase = UseCaseCallData<LocalStorage, LocalStorage, NoParams>
typealias LSData = DataSource<LocalStorage, NoParams>