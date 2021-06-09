package com.example.recipeapp.app

import android.app.Application
import com.example.recipeapp.app.koin.apiModule
import com.example.recipeapp.app.koin.businessLogicModule
import com.example.recipeapp.app.koin.networkModule
import com.example.recipeapp.app.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                    listOf(
                            networkModule,
                            apiModule,
                            businessLogicModule,
                            viewModelModule
                    )
            )
        }
    }

}