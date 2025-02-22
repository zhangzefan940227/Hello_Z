package com.study.lib

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Main {

}

fun main(args: Array<String>) {
    val fixedThreadPool: ExecutorService = Executors.newFixedThreadPool(5)

    val executor: ExecutorService = ThreadPoolExecutor(
        5, // 核心线程数
        10, // 最大线程数
        60L, // 空闲线程存活时间
        TimeUnit.SECONDS, // 时间单位
        LinkedBlockingQueue<Runnable>() // 任务队列
    )

}
