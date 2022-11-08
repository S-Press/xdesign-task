package com.xdesign.takehome.api.executors

import java.util.concurrent.Executor

class NewThreadPerTaskExecutor: Executor {
    override fun execute(r: Runnable) {
        Thread(r).start()
    }
}