package com.example.gps.model.repositoryes

import kotlinx.coroutines.flow.Flow

interface PermissionRepositoryInterface {
    suspend fun getPermission(): Flow<Boolean>
    fun clear()
    fun goToSettings()
}