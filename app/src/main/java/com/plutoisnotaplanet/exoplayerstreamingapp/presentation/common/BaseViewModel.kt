package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class BaseViewModel<SE, VS> : ViewModel() {

    protected val _singleActionFlow = MutableSharedFlow<SE>()
    val singleActionFlow: SharedFlow<SE> = _singleActionFlow.asSharedFlow()

    protected val _viewStateFlow: MutableStateFlow<Response<VS>> = MutableStateFlow(Response.Loading)
    val viewStateFlow: StateFlow<Response<VS>> = _viewStateFlow.asStateFlow()

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