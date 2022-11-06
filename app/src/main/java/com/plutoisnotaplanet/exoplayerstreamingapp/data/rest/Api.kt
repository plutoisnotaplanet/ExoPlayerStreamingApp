package com.plutoisnotaplanet.exoplayerstreamingapp.data.rest

import com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.response.PlayListDTO
import retrofit2.http.GET

interface Api {

    @GET("playlist/channels.json")
    suspend fun loadPlayList(): PlayListDTO

}