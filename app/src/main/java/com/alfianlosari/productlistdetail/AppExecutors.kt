package com.alfianlosari.productlistdetail

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {

    private val _mDiskIO: Executor
    private val _mNetworkIO:Executor
    private val _mMainThread: Executor

    val diskIO: Executor
        get() = _mDiskIO

    val networkIO: Executor
        get() = _mNetworkIO

    val mainThread: Executor
        get() = _mMainThread


    constructor() {
        this._mDiskIO = Executors.newSingleThreadExecutor()
        this._mNetworkIO = Executors.newFixedThreadPool(3)
        this._mMainThread = MainThreadExecutor()
    }

    private inner class MainThreadExecutor: Executor {
        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command)
        }

        private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())

    }

}