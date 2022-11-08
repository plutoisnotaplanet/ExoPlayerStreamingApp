package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.ImageLoader
import coil.imageLoader
import coil.request.ImageRequest
import com.google.android.exoplayer2.*
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.application.Constants
import com.plutoisnotaplanet.exoplayerstreamingapp.application.Constants.View.CHANNEL_IV_SIZE
import com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions.convertIntToDp
import com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions.setSafeOnClickListener
import com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions.showToast
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.ActivityPlayerBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.LayoutExoplayerControlsViewBinding
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Response
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate.PlayerController
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate.PlayerControllerDelegate
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate.TrackSelector
import com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.delegate.TrackSelectorDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(), TrackSelectorDelegate by TrackSelector(),
    PlayerControllerDelegate by PlayerController() {

    private val viewModel: PlayerViewModel by viewModels()
    private var binding: ActivityPlayerBinding? = null

    private var imageLoader: ImageLoader? = null

    @Inject lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        imageLoader = applicationContext.imageLoader
        observeViewModel()
        prepareIntentForChannelId(intent)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        nulifyProperties()
        binding = null
        imageLoader = null
    }

    private fun prepareIntentForChannelId(intent: Intent?) {
        if (intent != null) {
            val id = intent.getIntExtra(Constants.Bundle.CHANNEL_ID, 0)
            viewModel.getChannelById(id)
        } else {
            showToast(R.string.error_on_loading_video)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFlow.collectLatest {
                    when (it) {
                        is Response.Success -> {
                            setupPlayer(it.data)
                        }
                        is Response.Error -> {
                            showToast(R.string.error_on_loading_channel)
                        }
                        is Response.Loading -> {

                        }
                    }
                }
            }
        }
    }

    private fun stopPlayer() {
        stopPlayer(player)
    }

    private fun setupPlayer(data: PlayerViewState) {
        initializePlayerBindings(data)
        startPlayer(this, player, data.url)
        binding?.videoView?.player = player

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                showToast(error.message ?: "Error")
            }
        })
        binding?.videoView?.setControllerVisibilityListener { visibility ->
            if (visibility != View.VISIBLE) {
                hideSystemUi()
                qualityRv?.isVisible = false
            }
        }
    }

    private fun initializePlayerBindings(playerViewState: PlayerViewState) {
        val controllerView =
            binding?.videoView?.findViewById<ConstraintLayout>(R.id.controller_view)
        val binding = LayoutExoplayerControlsViewBinding.bind(controllerView!!)

        val sizeInDp = convertIntToDp(CHANNEL_IV_SIZE)
        with(binding) {
            exoProgress.hideScrubber(true)
            playerTtTv.text = playerViewState.title
            playerStTv.text = playerViewState.subTitle
            ivSettings.setSafeOnClickListener {
                selectVideoTrackWithList(
                    context = this@PlayerActivity,
                    player = player,
                    viewModel = viewModel,
                    recyclerView = qualityRv
                )
            }
            closeIv.setSafeOnClickListener { finish() }
            val request = ImageRequest.Builder(this@PlayerActivity)
                .data(playerViewState.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .size(sizeInDp, sizeInDp)
                .target(playerIv)
                .build()
            imageLoader?.enqueue(request)
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding!!.videoView).let { controller ->
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())
        }
    }
}