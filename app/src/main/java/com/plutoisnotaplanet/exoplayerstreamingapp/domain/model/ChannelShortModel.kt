package com.plutoisnotaplanet.exoplayerstreamingapp.domain.model

data class ChannelShortModel(
    val id: Int,
    val url: String? = null,
    val name: String? = null,
    val image: String? = null,
    val description: String? = null,
    val isFavorite: Boolean
) {
}