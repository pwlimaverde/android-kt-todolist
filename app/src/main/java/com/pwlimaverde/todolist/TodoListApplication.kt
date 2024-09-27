package com.pwlimaverde.todolist

import android.app.Application
import com.pwlimaverde.todolist.sevices.features.featuresServicesModule
import com.pwlimaverde.todolist.ui.screens.screensModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TodoListApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TodoListApplication)
            modules(screensModule, featuresServicesModule)
        }

    }
}