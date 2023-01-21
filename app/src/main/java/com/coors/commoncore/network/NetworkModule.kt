package com.coors.commoncore.network

import com.coors.commoncore.BuildConfig
import com.coors.commoncore.network.api.DemoApiService
import com.coors.commoncore.network.api.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    private companion object {
        const val BASE_URL = "https://m.aaawhu.com/"
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
        .client(provideOkHttpClientBuilder().build())
        .build()


    /*--------------------------------------------
     * Basic Retrofit & OkHttpClient Builders
     *--------------------------------------------*/

    private fun provideBasicRetrofitBuilder(moshi: Moshi): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())

    private fun provideOkHttpClientBuilder(
        connectTimeout: Long = DEFAULT_TIMEOUT_CONNECT,
        readTimeout: Long = DEFAULT_TIMEOUT_READ,
        writeTimeout: Long = DEFAULT_TIMEOUT_WRITE
    ): OkHttpClient.Builder =
        OkHttpClient.Builder()
//            .addInterceptor(provideGeneralHeaderInterceptor())
            .addInterceptor(provideLoggingInterceptor())
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    private fun provideGeneralHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val newRequest = original.newBuilder()
                .header("x-morse-os", "Android")
//                .header("x-morse-app-version", BuildConfig.APP_VERSION_NAME)
                .build()
            chain.proceed(newRequest)
        }
    }

//    private fun processErrorResponseWithSuccessfulInterceptor() = object : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val request = chain.request()
//            val response = chain.proceed(request)
//            if (response.isSuccessful) {
//                try {
//                    val jsonElement = JsonParser.parseString(response.cloneBodyString())
//                    /* work around to convert server response to the error bean as status code OK */
//                    if (!jsonElement.asJsonObject.get(JSON_MEMBER_NAME_STATUS).asBoolean) {
//                        return response.newBuilder().code(HttpURLConnection.HTTP_BAD_REQUEST)
//                            .build()
//                    }
//                } catch (ignored: Exception) {
//                }
//            }
//
//            return response
//        }
//    }

    @Provides
    fun providesDemoService(
        retrofit: Retrofit
    ): DemoApiService = retrofit.create(DemoApiService::class.java)
}