package com.example.gps.di

import com.example.gps.model.repositoryes.GoogleMapRepository
import com.example.gps.model.repositoryes.GoogleMapRepositoryInterface
import com.example.gps.model.repositoryes.GpsRecordServiceRepository
import com.example.gps.model.repositoryes.GpsRecordServiceRepositoryInterface
import com.example.gps.model.repositoryes.PermissionRepository
import com.example.gps.model.repositoryes.PermissionRepositoryInterface
import com.example.gps.model.repositoryes.WayDataRepository
import com.example.gps.model.repositoryes.WayDataRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPermissionRepository(permissionRepository: PermissionRepository): PermissionRepositoryInterface

    @Binds
    @Singleton
    abstract fun bindWayDataRepository(wayDataRepository: WayDataRepository): WayDataRepositoryInterface

    @Binds
    @Singleton
    abstract fun bindGpsRecordRepository(gpsRecordServiceRepository: GpsRecordServiceRepository): GpsRecordServiceRepositoryInterface

    @Binds
    @Singleton
    abstract fun bindGoogleMapRepository(googleMapRepository: GoogleMapRepository):GoogleMapRepositoryInterface
}

