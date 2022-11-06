package com.plutoisnotaplanet.exoplayerstreamingapp.domain.model

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.response.CurrentDTO
import com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.response.ForeignPlayerDTO

data class Channel(
    val id: Int? = null,
    val address: String? = null,
    val cdn: String? = null,
    val current: Current? = null,
    val drmStatus: Int? = null,
    val epgId: Int? = null,
    val foreignPlayer: ForeignPlayer? = null,
    val foreignPlayerKey: Boolean? = null,
    val hasEpg: Boolean? = null,
    val image: String? = null,
    val isFederal: Boolean? = null,
    val isForeign: Boolean? = null,
    val nameEn: String? = null,
    val nameRu: String? = null,
    val number: Int? = null,
    val owner: String? = null,
    val regionCode: Int? = null,
    val tz: Int? = null,
    val url: String? = null,
    val urlSound: String? = null,
    val vitrinaEventsUrl: String? = null,
    val isFavorite: Boolean = false
) {
}