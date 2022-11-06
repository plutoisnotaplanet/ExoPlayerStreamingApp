package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.R.*
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val navHostFragment = supportFragmentManager.findFragmentById(id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        Timber.e("${navController.currentDestination}")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}