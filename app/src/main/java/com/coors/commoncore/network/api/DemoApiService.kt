package com.coors.commoncore.network.api

import retrofit2.http.GET

interface DemoApiService {
    @GET("v3/2ff793b9-3a3b-43eb-a057-861c0f141e34")
    suspend fun getUsers(): List<String>
}