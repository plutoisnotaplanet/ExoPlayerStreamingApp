package com.plutoisnotaplanet.exoplayerstreamingapp.domain.model

import com.google.gson.annotations.SerializedName

data class ForeignPlayer(
    val sdk: String? = null,
    val url: String? = null,
    val validFrom: Int? = null
) {
}