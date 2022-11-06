package com.plutoisnotaplanet.exoplayerstreamingapp.data.rest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.plutoisnotaplanet.exoplayerstreamingapp.application.Constants
import com.plutoisnotaplanet.exoplayerstreamingapp.data.rest.interceptors.ResponseInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiFactory {

    fun create(
        baseUrl: String
    ): Api {

        return createRetrofit(
            createOkHttpClient(
                loggingInterceptor = createHttpLoggingInterceptor(),
                responseInterceptor = createResponseInterceptor()
            ),
            gson = createGson()
        )
            .baseUrl(baseUrl)
            .build()
            .create(Api::class.java)
    }


    private fun createOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(Constants.ApiConstants.TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(Constants.ApiConstants.TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(Constants.ApiConstants.TIMEOUT, TimeUnit.MINUTES)
            .apply {
                addInterceptor(loggingInterceptor)
                addInterceptor(responseInterceptor)
            }

        return okHttpClient.build()
    }


    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

    private fun createResponseInterceptor(): ResponseInterceptor =
        ResponseInterceptor()

    private fun createGson(): Gson = GsonBuilder().setLenient().create()

    private fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))

}
