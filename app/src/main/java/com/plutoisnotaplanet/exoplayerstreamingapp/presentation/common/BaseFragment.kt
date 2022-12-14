package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import kotlinx.coroutines.*
import timber.log.Timber


abstract class BaseFragment<AS,VS, VM: BaseViewModel<AS, VS>, VB: ViewBinding>: Fragment() {

    abstract val viewModel: VM
    abstract var _binding: VB?
    private var nulifyObserver: LifecycleObserver? = null
    val binding: VB by lazy { _binding!! }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable.message)
    }

    protected fun CoroutineScope.launchWithCatchOnIo(block: suspend CoroutineScope.() -> Unit) =
        launch(SupervisorJob() + exceptionHandler + Dispatchers.IO) {
            block()
        }

    abstract fun actionRender(action: AS)

    abstract fun viewStateRender(state: Response<VS>)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (nulifyObserver != null) {
            viewLifecycleOwner.lifecycle.removeObserver(nulifyObserver!!)
            nulifyObserver = null
        }
        _binding = null
    }

    protected fun nulifyOnDestroy(toDestroy: () -> Unit) {
        nulifyObserver = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            toDestroy()
                        }
                    })
                }
            }
        }
    }

    protected fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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