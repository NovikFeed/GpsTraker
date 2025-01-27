package com.example.gps.model.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.gps.R
import com.example.gps.model.repositoryes.GoogleMapRepository
import com.example.gps.model.repositoryes.WayDataRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class GpsRecordService : Service() {
    @Inject
    lateinit var wayDataRepository: WayDataRepository
    @Inject
    lateinit var googleMapRepository: GoogleMapRepository
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var uniqueKey: String
    private var coordinateList = mutableListOf<LatLng>()
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private var binder = GpsRecordServiceBinder()

    inner class GpsRecordServiceBinder : Binder() {
        fun getService(): GpsRecordService = this@GpsRecordService
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotification()
        startLocationUpdates()


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val key = intent?.getStringExtra("routeId")
        if (!key.isNullOrEmpty()) {
            uniqueKey = key
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification() {
        val chanel = NotificationChannel(
            "GPS_CHANNEL",
            "GPS",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(chanel)
        val notification = NotificationCompat.Builder(this, "GPS_CHANNEL")
            .setContentTitle("Record your route")
            .setSmallIcon(R.drawable.notification_icon)
            .build()
        startForeground(1, notification)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2500)
            .setMinUpdateIntervalMillis(1000)
            .setMaxUpdateDelayMillis(4000)
            .setMinUpdateDistanceMeters(10f)
            .setGranularity(Granularity.GRANULARITY_FINE)
            .build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let {
                    for (location in it.locations) {
                        coordinateList.add(LatLng(location.latitude, location.longitude))
                    }
                    saveCoordinate()
                }
                super.onLocationResult(locationResult)
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun saveCoordinate() {
        serviceScope.launch {
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            wayDataRepository.updateWay(uniqueKey, coordinateList.toString(), date.format(formatter).toString())
        }


    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        saveCoordinate()
        coordinateList.clear()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        serviceScope.cancel()
    }
}