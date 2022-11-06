package com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.response

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.ForeignPlayer

data class ForeignPlayerDTO(
    @SerializedName("sdk")
    val sdk: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("valid_from")
    val validFrom: Int? = null
) {

    fun toModel(): ForeignPlayer {
        return ForeignPlayer(
            sdk = sdk,
            url = url,
            validFrom = validFrom
        )
    }
}