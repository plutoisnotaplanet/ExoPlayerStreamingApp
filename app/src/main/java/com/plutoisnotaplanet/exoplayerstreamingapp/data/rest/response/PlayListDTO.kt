package com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Channel

data class PlayListDTO(
    @SerializedName("channels")
    val playList: List<ChannelDTO>
) {

}