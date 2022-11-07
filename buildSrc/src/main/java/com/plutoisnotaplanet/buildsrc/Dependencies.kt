package com.plutoisnotaplanet.buildsrc

object Dependencies {

    object Settings {
        const val compileSdk = 33
        const val minSdk = 24
        const val targetSdk = 33
        const val buildTools = "30.0.2"
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object Gradle {
        private const val version = "7.3.0"
        const val androidGradlePlugin = "com.android.tools.build:gradle:$version"
    }

    object Logging {
        private const val version = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:$version"
    }

    object ExoPlayer {
        private const val version = "2.18.1"
        const val core = "com.google.android.exoplayer:exoplayer-core:$version"
        const val dash = "com.google.android.exoplayer:exoplayer-dash:$version"
        const val ui = "com.google.android.exoplayer:exoplayer-ui:$version"
        const val hls = "com.google.android.exoplayer:exoplayer-hls:$version"
        const val smoothstreaming = "com.google.android.exoplayer:exoplayer-smoothstreaming:$version"
    }

    object Hilt {
        private const val hiltCoreVersion = "2.44"
        private const val hiltVersion = "1.0.0"
        const val gradleHilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltCoreVersion"
        const val core = "com.google.dagger:hilt-android:$hiltCoreVersion"
        const val compiler = "com.google.dagger:hilt-compiler:$hiltCoreVersion"
        const val androidXCompiler = "androidx.hilt:hilt-compiler:$hiltVersion"
        const val test = "com.google.dagger:hilt-android-testing:$hiltCoreVersion"
        const val kaptTest = "com.google.dagger:hilt-compiler:$hiltCoreVersion"
    }

    object Network {
        private const val okhttpVersion = "4.7.2"
        private const val retrofitVersion = "2.9.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val logInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val gson = "com.google.code.gson:gson:$retrofitVersion"
        const val mock = "com.squareup.okhttp3:mockwebserver:$okhttpVersion"
    }

    object Coil {
        private const val version = "2.2.2"
        const val core = "io.coil-kt:coil:$version"
    }


    object Database {
        private const val roomVersion = "2.4.3"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
    }


    object Kotlin {
        private const val version = "1.7.20"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Coroutines {
        private const val version = "1.3.9"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.5.1"
        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val material = "com.google.android.material:material:1.7.0"
        const val tooling = "androidx.ui:ui-tooling:1.0.0-alpha07"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.1"
        const val fragment = "androidx.fragment:fragment-ktx:1.5.4"
        const val viewBinding = "androidx.databinding:viewbinding:7.3.1"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        object Navigation {
            private const val version = "2.5.3"
            const val fragment  = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Lifecycle {
            private const val lifecycleVersion = "2.5.1"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }
    }
}