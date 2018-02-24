package com.alfianlosari.productlistdetail

import android.app.Application
import com.alfianlosari.productlistdetail.db.AppDatabase
import com.alfianlosari.productlistdetail.db.repository.DataRepository

class App: Application() {

    private val appExecutors = AppExecutors()

    val appDatabase: AppDatabase
        get() = AppDatabase.instance(this, appExecutors)

    val repository: DataRepository
        get() = DataRepository.instance(appDatabase)

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }




}