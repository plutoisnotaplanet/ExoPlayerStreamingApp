package com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.response

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.entities.ChannelEntity
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Channel
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.ChannelShortModel

data class ChannelDTO(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("cdn")
    val cdn: String? = null,
    @SerializedName("current")
    val currentDTO: CurrentDTO? = null,
    @SerializedName("drm_status")
    val drmStatus: Int? = null,
    @SerializedName("epg_id")
    val epgId: Int? = null,
    @SerializedName("foreign_player")
    val foreignPlayerDTO: ForeignPlayerDTO? = null,
    @SerializedName("foreign_player_key")
    val foreignPlayerKey: Boolean? = null,
    @SerializedName("hasEpg")
    val hasEpg: Boolean? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("is_federal")
    val isFederal: Boolean? = null,
    @SerializedName("is_foreign")
    val isForeign: Boolean? = null,
    @SerializedName("name_en")
    val nameEn: String? = null,
    @SerializedName("name_ru")
    val nameRu: String? = null,
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("owner")
    val owner: String? = null,
    @SerializedName("region_code")
    val regionCode: Int? = null,
    @SerializedName("tz")
    val tz: Int? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("url_sound")
    val urlSound: String? = null,
    @SerializedName("vitrina_events_url")
    val vitrinaEventsUrl: String? = null
) {

    fun toModel(): Channel {
        return Channel(
            id = id,
            address = address,
            cdn = cdn,
            current = currentDTO?.toModel(),
            drmStatus = drmStatus,
            epgId = epgId,
            foreignPlayer = foreignPlayerDTO?.toModel(),
            foreignPlayerKey = foreignPlayerKey,
            hasEpg = hasEpg,
            image = image,
            isFederal = isFederal,
            isForeign = isForeign,
            nameEn = nameEn,
            nameRu = nameRu,
            number = number,
            owner = owner,
            regionCode = regionCode,
            tz = tz,
            url = url,
            urlSound = urlSound,
            vitrinaEventsUrl = vitrinaEventsUrl
        )
    }

    fun toDbEntity(): ChannelEntity {
        return ChannelEntity(
            id = id ?: 0,
            address = address,
            cdn = cdn,
            current = currentDTO?.toModel(),
            drmStatus = drmStatus,
            epgId = epgId,
            foreignPlayer = foreignPlayerDTO?.toModel(),
            foreignPlayerKey = foreignPlayerKey,
            hasEpg = hasEpg,
            image = image,
            isFederal = isFederal,
            isForeign = isForeign,
            nameEn = nameEn,
            nameRu = nameRu,
            number = number,
            owner = owner,
            regionCode = regionCode,
            tz = tz,
            url = url,
            urlSound = urlSound,
            vitrinaEventsUrl = vitrinaEventsUrl
        )
    }

    fun toShortModel(): ChannelShortModel {
        return ChannelShortModel(
            id = id ?: 0,
            name = nameRu,
            image = image,
            url = url,
            description = currentDTO?.title,
            isFavorite = false
        )
    }
}