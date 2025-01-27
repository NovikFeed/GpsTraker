package com.example.gps.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gps.model.data.local.Way
import com.example.gps.model.repositoryes.GpsRecordServiceRepository
import com.example.gps.model.repositoryes.WayDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GpsTrackerViewModel @Inject constructor(
    private val serviceRepository: GpsRecordServiceRepository,
    private val wayDataRepository: WayDataRepository,
) : ViewModel() {
    val serviceRunning = serviceRepository.serviceRunning

    private val _animatePolyline = MutableStateFlow(false)
            val animatePolyline = _animatePolyline

    private val _wayList = MutableStateFlow(emptyList<Way>())
    val wayList = _wayList

    private val _detailsWay = MutableStateFlow(emptyList<Way>())
    val detailsWay = _detailsWay

    init {
        checkWorkService()
        getWays()
    }

    private fun checkWorkService() {
        viewModelScope.launch {
            serviceRepository.checkWorkService()
        }
    }

    fun startService() {
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val uid = UUID.randomUUID().toString()
        val way = Way(uniqueKey = uid, startDate = date.format(formatter), arrayCoordinate = emptyList())
        viewModelScope.launch {
            wayDataRepository.upsertWay(way)
            serviceRepository.startRecordRoute(uid)

        }
    }

    fun stopService() {
        viewModelScope.launch {
            serviceRepository.stopRecordRoute()
        }
    }

    private fun getWays() {
        viewModelScope.launch {
            wayDataRepository.getWays().collect {
                _wayList.value = it
            }
        }
    }

    fun setDetailsWay(index: Int) {
        _detailsWay.value = listOf(_wayList.value[index])
    }

    fun setAnimation(){
        _animatePolyline.value = true
        viewModelScope.launch {
            delay(3200)
            _animatePolyline.value = false
        }
    }

}