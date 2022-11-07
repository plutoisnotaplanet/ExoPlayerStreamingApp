package com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions

import android.content.Context
import android.os.SystemClock
import android.view.View
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Coordinates
import kotlin.math.roundToInt

fun Context.convertIntToDp(dp: Int): Int {
    val density = this.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}

fun Context.calculateQualityDialogPosition(qualityListSize: Int): Coordinates {
    val screenWidth = this.resources.displayMetrics.widthPixels
    val screenHeight = this.resources.displayMetrics.heightPixels

    val paddingOnEachElementInList = qualityListSize * 2
    //180 for tablet, -20 for phone
    val yCoordinates = convertIntToDp(if (screenHeight > 1080) 180 else -20) + paddingOnEachElementInList
    // 480 for tablet, 280 for phone
    val xCoordinates = convertIntToDp(if (screenWidth > 2250) 480 else 280)
    return Coordinates(y = yCoordinates, x = xCoordinates)
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

private class SafeClickListener(
    private var defaultInterval: Int = 300,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}