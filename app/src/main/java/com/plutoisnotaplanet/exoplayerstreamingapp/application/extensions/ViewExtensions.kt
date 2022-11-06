package com.plutoisnotaplanet.exoplayerstreamingapp.application.extensions

import android.content.Context
import android.os.SystemClock
import android.view.View
import kotlin.math.roundToInt

fun Context.convertIntToDp(dp: Int): Int {
    val density = this.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
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