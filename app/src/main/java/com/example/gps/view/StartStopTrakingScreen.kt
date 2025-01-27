package com.example.gps.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gps.viewModels.GpsTrackerViewModel

@Composable
fun StartStopTrackingScreen(viewModel: GpsTrackerViewModel = hiltViewModel<GpsTrackerViewModel>()) {
    val serviceRunning by viewModel.serviceRunning.collectAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
        if(serviceRunning){
            Button(onClick = {viewModel.stopService()}) {
                Text(text = "Stop tracking")
            }
        }
        else {
            Button(onClick = { viewModel.startService() }) {
                Text(text = "Start tracking")
            }
        }
    }
}