package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class QualityItem(val text: String, val positionInList: Int, var isSelected: Boolean = false): Parcelable {
    P240("240p", 0),
    P360("360p", 1),
    P480("480p", 2),
    P720("720p", 3),
    P1080("1080p", 4),
    P1440("1440p", 5),
    P2160("2160p", 6),
    AUTO("AUTO",-1, true)
}

fun Int.toQualityItem(isSelected: Boolean = false): QualityItem {
    val result =  when (this) {
        0 -> QualityItem.AUTO
        in 1..240 -> QualityItem.P240
        in 240..360 -> QualityItem.P360
        in 360..480 -> QualityItem.P480
        in 480..720 -> QualityItem.P720
        in 720..1080 -> QualityItem.P1080
        in 1080..1440 -> QualityItem.P1440
        in 1440..2160 -> QualityItem.P2160
        else -> QualityItem.AUTO
    }
    result.isSelected = isSelected
    return result
}


