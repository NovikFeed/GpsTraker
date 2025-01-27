package com.example.gps.model.data.util

import android.location.Address
import com.example.gps.model.data.local.Way
import com.example.gps.model.data.room.WayDBO
import com.example.gps.model.repositoryes.GoogleMapRepository
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

fun WayDBO.toWay(): Way {
    return Way(
        uniqueKey = uniqueKey,
        startDate = startDate,
        endDate = endDate,
        startAddress = Address(Locale("en")),
        destinationAddress = Address(Locale("en")),
        arrayCoordinate = arrayCoordinate.toCoordinateList()
    )
}

fun Way.toWayDBO(): WayDBO {
    return WayDBO(
        uniqueKey = uniqueKey,
        startDate = startDate,
        endDate = endDate,
        arrayCoordinate = arrayCoordinate.toString()
    )
}

fun String.toCoordinateList(): List<LatLng> {
    val regex = """lat/lng: \(([-+]?\d*\.\d+|\d+),\s*([-+]?\d*\.\d+|\d+)\)""".toRegex()
    val matches = regex.findAll(this)

    return matches.map {
        val lat = it.groupValues[1].toDouble()
        val lng = it.groupValues[2].toDouble()
        LatLng(lat, lng)
    }.toList()
}