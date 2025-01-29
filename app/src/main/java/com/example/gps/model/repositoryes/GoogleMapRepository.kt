package com.example.gps.model.repositoryes

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class GoogleMapRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : GoogleMapRepositoryInterface {
    override suspend fun getAddressFromLatLng(latLng: LatLng): Address {
        return getAddressFromGeocoder(context, latLng)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getAddressFromGeocoder(
        context: Context, latLng: LatLng)  : Address{
        return withContext(Dispatchers.IO){
        val geocoder = Geocoder(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { continuation ->
                geocoder.getFromLocation(latLng.latitude,
                    latLng.longitude,
                    1
                ) { addresses ->
                    val addressFromGeocoder = addresses[0]
                    continuation.resume(addressFromGeocoder)
                }
            }
        } else {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses.isNullOrEmpty()){
            Address(Locale("en"))}
            else{
                addresses[0]
            }
        }
            }
    }
}