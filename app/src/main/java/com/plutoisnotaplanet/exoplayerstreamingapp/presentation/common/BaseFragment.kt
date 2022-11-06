package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import kotlinx.coroutines.*
import timber.log.Timber


abstract class BaseFragment<SE,VS, VM: BaseViewModel<SE, VS>, VB: ViewBinding>: Fragment() {

    abstract val viewModel: VM
    abstract var _binding: VB?
    val binding: VB by lazy { _binding!! }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable.message)
    }

    protected fun CoroutineScope.launchWithCatchOnIo(block: suspend CoroutineScope.() -> Unit) =
        launch(SupervisorJob() + exceptionHandler + Dispatchers.IO) {
            block()
        }

    abstract fun actionRender(action: SE)

    abstract fun viewStateRender(state: Response<VS>)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    protected fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.singleActionFlow.collect { state ->
                    state?.let { actionRender(it) }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFlow.collect { state ->
                    viewStateRender(state)
                }
            }
        }
    }
}