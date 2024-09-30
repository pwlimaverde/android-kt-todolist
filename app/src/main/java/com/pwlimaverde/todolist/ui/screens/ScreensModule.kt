package com.pwlimaverde.todolist.ui.screens

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.pwlimaverde.todolist.sevices.features.external_storage.datasource.firestore.FireStoreDatasource
import com.pwlimaverde.todolist.sevices.features.external_storage.datasource.firestore.FireStoreExternalStorage
import com.pwlimaverde.todolist.sevices.features.external_storage.domain.usecase.ExternalStorageUseCase
import com.pwlimaverde.todolist.ui.screens.addedit.AddEditViewModel
import com.pwlimaverde.todolist.ui.screens.list.ListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val screensModule = module {
    viewModelOf(::ListViewModel)
    viewModelOf(::AddEditViewModel)
}