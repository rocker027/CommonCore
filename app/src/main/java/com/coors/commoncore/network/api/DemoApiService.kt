package com.coors.commoncore.network.api

import com.coors.commoncore.model.AnchorModel
import com.coors.commoncore.model.BaseResponse
import com.coors.commoncore.result.Result
import retrofit2.http.GET

interface DemoApiService {
    @GET("ssapi/anchor/search?pid=bb&matchSource=all")
    suspend fun getAnchors(): Result<BaseResponse<List<AnchorModel>>>
}