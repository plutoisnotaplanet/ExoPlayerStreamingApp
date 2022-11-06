package com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.util.*

var lastEmissionTime: Long? = 0L
@FlowPreview
@ExperimentalCoroutinesApi
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = callbackFlow {
    collect { upstream ->
        if (lastEmissionTime == null) lastEmissionTime = 0L
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - lastEmissionTime!! > windowDuration
        if (mayEmit) {
            lastEmissionTime = currentTime
            send(upstream)
        }
    }

    awaitClose { lastEmissionTime = null }
}

fun <T> Flow<T>.throttleLatest(periodMillis: Long): Flow<T> {
    return channelFlow {
        var lastValue: T?
        var timer: Timer? = null
        onCompletion { timer?.cancel() }
        collect { value ->
            lastValue = value
            if (timer == null) {
                timer = Timer()
                timer?.scheduleAtFixedRate(
                    object : TimerTask() {
                        override fun run() {
                            val value = lastValue
                            lastValue = null
                            if (value != null) {
                                launch {
                                    send(value as T)
                                }
                            } else {
                                timer?.cancel()
                                timer = null
                            }
                        }
                    },
                    0,
                    periodMillis
                )
            }
        }
    }
}