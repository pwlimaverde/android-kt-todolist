package com.pwlimaverde.todolist.ui.screens

import com.pwlimaverde.todolist.ui.screens.addedit.AddEditViewModel
import com.pwlimaverde.todolist.ui.screens.list.ListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val screensModule = module {
    viewModelOf(::ListViewModel)
    viewModelOf(::AddEditViewModel)
}