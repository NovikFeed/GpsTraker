package com.example.gps.model.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WayDAO {
    @Upsert
    suspend fun upsertWay(way : WayDBO)
    @Query("SELECT * FROM WayDBO")
    fun getWays() : Flow<List<WayDBO>>
    @Query("SELECT * FROM WayDBO WHERE uniqueKey = :key")
    suspend fun getWayById(key: String) : WayDBO
    @Query("UPDATE WayDBO SET arrayCoordinate = :coordinateList, endDate = :endDate WHERE uniqueKey = :key")
    suspend fun updateWay(key : String, coordinateList : String, endDate : String)
}