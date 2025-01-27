package com.example.gps.model.repositoryes

import com.example.gps.model.data.local.Way
import kotlinx.coroutines.flow.Flow

interface WayDataRepositoryInterface {
    suspend fun upsertWay(way: Way)
    suspend fun getWays(): Flow<List<Way>>
    suspend fun updateAddresses(key : String, startAddress : String, destinationAddress : String)
    suspend fun updateWay(key: String, coordinateList: String, endDate: String)
}