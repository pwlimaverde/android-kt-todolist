package com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase

import com.pwlimaverde.return_success_or_error_kt.core.ReturnSuccessOrError
import com.pwlimaverde.return_success_or_error_kt.datasource.DataSource
import com.pwlimaverde.return_success_or_error_kt.parameters.NoParams
import com.pwlimaverde.return_success_or_error_kt.usecase.UseCaseCallData
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces.ExternalStorage

class ExternalStorageUseCase(dataSource: ESData) : ESUseCase(dataSource) {
    override suspend fun invoke(parameters: NoParams): ReturnSuccessOrError<ExternalStorage> {
        return resultDatasource(parameters)
    }
}

typealias ESUseCase = UseCaseCallData<ExternalStorage, ExternalStorage, NoParams>
typealias ESData = DataSource<ExternalStorage, NoParams>