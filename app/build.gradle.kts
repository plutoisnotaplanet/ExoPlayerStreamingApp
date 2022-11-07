import com.plutoisnotaplanet.buildsrc.Dependencies

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.plutoisnotaplanet.exoplayerstreamingapp"

    compileSdk = Dependencies.Settings.compileSdk

    defaultConfig {
        applicationId = "com.plutoisnotaplanet.exoplayerstreamingapp"
        minSdk = Dependencies.Settings.minSdk
        targetSdk = Dependencies.Settings.targetSdk
        versionCode = Dependencies.Settings.versionCode
        versionName = Dependencies.Settings.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //core
    implementation (Dependencies.Kotlin.stdlib)
    implementation (Dependencies.AndroidX.coreKtx)
    implementation (Dependencies.AndroidX.appcompat)
    implementation (Dependencies.AndroidX.material)
    implementation (Dependencies.AndroidX.tooling)
    implementation (Dependencies.AndroidX.constraintLayout)
    implementation (Dependencies.AndroidX.viewBinding)
    implementation (Dependencies.AndroidX.fragment)
    implementation (Dependencies.AndroidX.swipeRefreshLayout)


    //Navigation
    implementation (Dependencies.AndroidX.Navigation.fragment)
    implementation (Dependencies.AndroidX.Navigation.ui)

    // network
    implementation (Dependencies.Network.retrofit)
    implementation (Dependencies.Network.gson)
    implementation (Dependencies.Network.okhttp)
    implementation (Dependencies.Network.logInterceptor)
    implementation (Dependencies.Network.gsonConverter)

    //exoplayer
    implementation (Dependencies.ExoPlayer.core)
    implementation (Dependencies.ExoPlayer.ui)
    implementation (Dependencies.ExoPlayer.hls)

    //coil
    implementation (Dependencies.Coil.core)

    //room
    implementation (Dependencies.Database.runtime)
    implementation (Dependencies.Database.ktx)
    kapt (Dependencies.Database.compiler)

    //lifecycle
    implementation (Dependencies.AndroidX.Lifecycle.runtime)
    implementation (Dependencies.AndroidX.Lifecycle.liveData)
    implementation (Dependencies.AndroidX.Lifecycle.viewModel)

    // coroutines
    implementation (Dependencies.Coroutines.android)
    testImplementation (Dependencies.Coroutines.test)

    // hilt
    implementation (Dependencies.Hilt.core)
    kapt (Dependencies.Hilt.compiler)
    kapt (Dependencies.Hilt.androidXCompiler)
    kaptAndroidTest (Dependencies.Hilt.kaptTest)
    androidTestImplementation (Dependencies.Hilt.test)

    //logging
    implementation (Dependencies.Logging.timber)

    androidTestImplementation (Dependencies.AndroidX.Test.core)
    androidTestImplementation (Dependencies.AndroidX.Test.Ext.junit)
    androidTestImplementation (Dependencies.AndroidX.Test.rules)
}