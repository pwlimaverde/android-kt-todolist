package com.pwlimaverde.todolist.sevices.features


import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.pwlimaverde.todolist.sevices.features.external_storage.datasource.firestore.FireStoreDatasource
import com.pwlimaverde.todolist.sevices.features.external_storage.datasource.firestore.FireStoreExternalStorage
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.interfaces.ExternalStorage
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ESData
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ESUseCase
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ExternalStorageUseCase
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoDao
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoDatabaseProvider
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoRoomDatasource
import com.pwlimaverde.todolist.sevices.features.local_storage.datasource.room.TodoRoomRepository
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.interfaces.LocalStorage
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase.LSData
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase.LSUseCase
import com.pwlimaverde.todolist.sevices.features.local_storage.domain.usecase.LocalStorageUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val featuresServicesModule = module {
    single<FirebaseFirestore> {
        Firebase.firestore
    }
    factory<ExternalStorage> {
        FireStoreExternalStorage(get())
    }

    single<TodoDao> {
        TodoDatabaseProvider.provide(androidContext()).todoDao
    }
    factory<LocalStorage> {
        TodoRoomRepository(get())
    }
    factory<LSData> {
        TodoRoomDatasource(get())
    }
    factory<LSUseCase> {
        LocalStorageUseCase(get())
    }
    single {

        FeaturesServerPresenter(
            localStorage = get(),
            externalStorage = ExternalStorageUseCase(FireStoreDatasource(get())),
        )
    }
}