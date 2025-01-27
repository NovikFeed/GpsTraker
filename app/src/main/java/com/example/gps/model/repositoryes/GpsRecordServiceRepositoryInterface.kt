package com.example.gps.model.repositoryes

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface GpsRecordServiceRepositoryInterface {
    suspend fun startRecordRoute(uid : String)
    suspend fun checkWorkService()
    suspend fun stopRecordRoute()
}