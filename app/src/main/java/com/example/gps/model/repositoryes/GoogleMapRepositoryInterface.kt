package com.example.gps.model.repositoryes

import android.location.Address
import com.google.android.gms.maps.model.LatLng

interface GoogleMapRepositoryInterface {
    suspend fun getAddressFromLatLng(latLng : LatLng) : Address
}