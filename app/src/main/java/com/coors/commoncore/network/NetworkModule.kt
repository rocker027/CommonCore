package com.coors.commoncore.network

import com.coors.commoncore.network.api.DemoApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    private companion object {
        const val BASE_URL = "https://run.mocky.io/"
        const val DEFAULT_TIMEOUT_CONNECT = 15L
        const val DEFAULT_TIMEOUT_READ = 30L
        const val DEFAULT_TIMEOUT_WRITE = 30L
        const val JSON_MEMBER_NAME_STATUS = "status"
    }

    /*--------------------------------
     * Retrofit
     *-------------------------------*/

    @Singleton
    @Provides
    fun providesDemoRetrofit(
        moshi: Moshi,
    ): Retrofit = provideBasicRetrofitBuilder(moshi)
        .baseUrl(BASE_URL)
        .client(
            provideOkHttpClientBuilder()
                .build()
        )
        .build()


    /*--------------------------------------------
     * Basic Retrofit & OkHttpClient Builders
     *--------------------------------------------*/

    private fun provideBasicRetrofitBuilder(moshi: Moshi): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    private fun provideOkHttpClientBuilder(
        connectTimeout: Long = DEFAULT_TIMEOUT_CONNECT,
        readTimeout: Long = DEFAULT_TIMEOUT_READ,
        writeTimeout: Long = DEFAULT_TIMEOUT_WRITE
    ): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)

//    @Provides
//    fun providesApiConfiguration(
//        endpoint: String,
//    ) = ApiConfiguration(endpoint)
//
//    data class ApiConfiguration(
//        val endpoint: String,
//    )

    @Provides
    fun providesDemoService(
        retrofit: Retrofit
    ): DemoApiService = retrofit.create(DemoApiService::class.java)
}