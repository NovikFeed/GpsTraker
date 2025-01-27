package com.example.gps.model.data.local

import android.location.Address
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

data class Way(
    val uniqueKey : String = "",
    val startDate : String= "",
    val endDate : String = "",
    val range : String = "",
    val avgSpeed : String = "",
    val startAddress : Address = Address(Locale("en")),
    val destinationAddress : Address = Address(Locale("en")),
    val arrayCoordinate : List<LatLng> = emptyList()
) {
}