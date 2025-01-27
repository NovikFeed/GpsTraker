package com.example.gps.model.repositoryes

import android.util.Log
import com.example.gps.model.data.local.Way
import com.example.gps.model.data.room.WayDatabase
import com.example.gps.model.data.util.toWay
import com.example.gps.model.data.util.toWayDBO
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class WayDataRepository @Inject constructor(
    private val database: WayDatabase,
    private val googleMapRepository: GoogleMapRepository
) : WayDataRepositoryInterface {
    override suspend fun upsertWay(way: Way) {
        database.wayDAO.upsertWay(way.toWayDBO())
    }

    override suspend fun getWays(): Flow<List<Way>> {
        return database.wayDAO.getWays().map {
            it.map { it.toWay() }.filter { it.arrayCoordinate.isNotEmpty() }
                .map { it.copy(startAddress = googleMapRepository.getAddressFromLatLng(it.arrayCoordinate[0]),
                    destinationAddress = googleMapRepository.getAddressFromLatLng(it.arrayCoordinate[it.arrayCoordinate.size-1]),
                    range = getRangeFromCoordinationList(it.arrayCoordinate),
                ) }.map{it.copy(avgSpeed = getAvgSpeed(it.startDate, it.endDate, it.range))}
        }
    }


    override suspend fun updateWay(key: String, coordinateList: String, endDate : String) {
        database.wayDAO.updateWay(key, coordinateList, endDate)
    }

    override suspend fun updateAddresses(
        key: String,
        startAddress: String,
        destinationAddress: String
    ) {

    }
    private fun getRangeFromCoordinationList(coordinates : List<LatLng>): String{
        val earthRadius = 6371.0
        var totalDistance = 0.0

        for (i in 0 until coordinates.size - 1) {
            val start = coordinates[i]
            val end = coordinates[i + 1]

            val lat1 = Math.toRadians(start.latitude)
            val lon1 = Math.toRadians(start.longitude)
            val lat2 = Math.toRadians(end.latitude)
            val lon2 = Math.toRadians(end.longitude)

            val dLat = lat2 - lat1
            val dLon = lon2 - lon1
            val a = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))
            val distance = earthRadius * c

            totalDistance += distance
        }

        return totalDistance.toString()
    }
    private fun getAvgSpeed(startDate : String, endDate: String, range : String) : String{
        if(startDate != "" && endDate != "" && range != ""){
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val start = LocalDateTime.parse(startDate, formatter)
        val end = LocalDateTime.parse(endDate, formatter)
        val minute = ChronoUnit.MINUTES.between(start, end)
        val distance = range.toDouble()
        return  (distance / ((minute.toDouble())/60)).toInt().toString()
        }
        return  ""
    }
}
