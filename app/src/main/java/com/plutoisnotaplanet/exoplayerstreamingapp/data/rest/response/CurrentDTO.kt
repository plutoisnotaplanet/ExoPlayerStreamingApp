package com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.response

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Current

data class CurrentDTO(
    @SerializedName("cdnvideo")
    val cdnVideo: Int? = null,
    @SerializedName("desc")
    val desc: String? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("timestart")
    val timeStart: Int? = null,
    @SerializedName("timestop")
    val timeStop: Int? = null,
    @SerializedName("title")
    val title: String? = null
) {

    fun toModel(): Current {
        return Current(
            cdnVideo = cdnVideo,
            desc = desc,
            rating = rating,
            timeStart = timeStart,
            timeStop = timeStop,
            title = title
        )
    }
}