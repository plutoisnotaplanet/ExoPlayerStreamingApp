package com.plutoisnotaplanet.exoplayerstreamingapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plutoisnotaplanet.exoplayerstreamingapp.data.db.ExoPlayerStreamingDataBase
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Channel
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.ChannelShortModel
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.Current
import com.plutoisnotaplanet.exoplayerstreamingapp.domain.model.ForeignPlayer

@Entity(tableName = ExoPlayerStreamingDataBase.CHANNELS_DB)
data class ChannelEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
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
    var isFavorite: Boolean = false
) {

    fun toModel(): Channel {
        return Channel(
            id,
            address,
            cdn,
            current,
            drmStatus,
            epgId,
            foreignPlayer,
            foreignPlayerKey,
            hasEpg,
            image,
            isFederal,
            isForeign,
            nameEn,
            nameRu,
            number,
            owner,
            regionCode,
            tz,
            url,
            urlSound,
            vitrinaEventsUrl,
            isFavorite
        )
    }

    fun toShortModel(): ChannelShortModel {
        return ChannelShortModel(
            id = id,
            name = nameRu,
            url = url,
            description = current?.title,
            image = image,
            isFavorite = isFavorite
        )
    }
}