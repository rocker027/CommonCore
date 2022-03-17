package com.coors.commoncore.data.mapper

interface Mapper<F, T> {
    suspend fun map(from: F): T
}
