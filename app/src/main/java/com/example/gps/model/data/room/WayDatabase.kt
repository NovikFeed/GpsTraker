package com.example.gps.model.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =[WayDBO::class],
    version = 4
)
abstract class WayDatabase : RoomDatabase() {
    abstract val wayDAO: WayDAO
}