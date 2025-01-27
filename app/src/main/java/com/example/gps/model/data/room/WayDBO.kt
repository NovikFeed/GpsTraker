package com.example.gps.model.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WayDBO(
    @PrimaryKey
    val uniqueKey : String,
    val startDate : String,
    val endDate : String,
    val arrayCoordinate : String
) {
}