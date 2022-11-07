package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class BaseViewModel<SE, VS> : ViewModel() {

    protected val _singleActionFlow = MutableSharedFlow<SE>()
    val singleActionFlow: SharedFlow<SE> = _singleActionFlow.asSharedFlow()

    protected val _viewStateFlow: MutableSharedFlow<Response<VS>> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val viewStateFlow: SharedFlow<Response<VS>> = _viewStateFlow.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable.message)
    }

    protected fun CoroutineScope.launchOnIo(block: suspend CoroutineScope.() -> Unit) =
        launch(SupervisorJob() + exceptionHandler + Dispatchers.IO) {
            block()
        }

    protected fun CoroutineScope.launchOnIoWithProgress(block: suspend CoroutineScope.() -> Unit) =
        launch(SupervisorJob() + exceptionHandler + Dispatchers.IO) {
            _viewStateFlow.emit(Response.Loading)
            block()
        }


    fun setAction(state: SE) {
        viewModelScope.launch {
            _singleActionFlow.emit(state)
        }
    }

}