package com.plutoisnotaplanet.exoplayerstreamingapp.domain.model

import com.google.gson.annotations.SerializedName

data class Current(
    val cdnVideo: Int? = null,
    val desc: String? = null,
    val rating: Int? = null,
    val timeStart: Int? = null,
    val timeStop: Int? = null,
    val title: String? = null
) {
}