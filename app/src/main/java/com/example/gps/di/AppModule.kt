package com.example.gps.di

import android.app.Application
import androidx.room.Room
import com.example.gps.model.data.room.WayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWayDatabase(app : Application) : WayDatabase {
        return Room.databaseBuilder(
            app,
            WayDatabase::class.java,
            "way.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}