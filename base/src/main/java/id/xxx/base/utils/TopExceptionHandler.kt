package id.xxx.base.utils

import kotlin.system.exitProcess

//Thread.setDefaultUncaughtExceptionHandler(TopExceptionHandler)

class TopExceptionHandler : Thread.UncaughtExceptionHandler {
    init {
        Thread.getDefaultUncaughtExceptionHandler()
    }

    override fun uncaughtException(thred: Thread, throwable: Throwable) {
        exitProcess(0)
    }
}