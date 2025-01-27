package com.example.gps.model.repositoryes

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.gps.model.services.GpsRecordService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GpsRecordServiceRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : GpsRecordServiceRepositoryInterface {

    private val _serviceRunning = MutableStateFlow(false)
    val serviceRunning: StateFlow<Boolean> get() = _serviceRunning

    override suspend fun startRecordRoute(uid: String) {
        val intent = Intent(context, GpsRecordService::class.java)
        intent.putExtra("routeId", uid)
        if (!isServiceRunning(GpsRecordService::class.java, context)) {
            context.startService(intent)
            _serviceRunning.emit(true)


        }
    }

    override suspend fun checkWorkService() {
        _serviceRunning.emit(isServiceRunning(GpsRecordService::class.java, context))

    }


    override suspend fun stopRecordRoute() {
        val intent = Intent(context, GpsRecordService::class.java)
        if (isServiceRunning(GpsRecordService::class.java, context)) {
            context.stopService(intent)
            _serviceRunning.emit(false)
        }
    }

    private fun isServiceRunning(serviceClass: Class<out Service>, context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (service.service.className == serviceClass.name) {
                return true
            }
        }
        return false
    }
}