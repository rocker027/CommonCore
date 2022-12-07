package com.coors.commoncore.network

import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class NetworkManager @Inject constructor(
//    private val networkComponentBuilder: NetworkComponent.Builder
//){
//    var networkComponent: NetworkComponent? = null
//        private set
//
//    fun init(builder: NetworkComponentOptions.Builder.() -> NetworkComponentOptions) {
//        val options = builder(NetworkComponentOptions.Builder())
//        networkComponent = with(networkComponentBuilder) {
//            endpointApi(options.endpointApi)
//            build()
//        }
//    }
//
//    class NetworkComponentOptions private constructor(
//        internal val endpointApi: String,
//    ) {
//        class Builder {
//            internal var endpointApi: String = ""
//            fun endpointApi(endpoint: String) = apply { this.endpointApi = endpoint }
//            fun build() = NetworkComponentOptions(
//                endpointApi
//            )
//        }
//    }
//}