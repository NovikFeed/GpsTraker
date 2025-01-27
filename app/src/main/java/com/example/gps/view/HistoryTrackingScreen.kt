package com.example.gps.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gps.navigation.Screens
import com.example.gps.view.components.WayComponent
import com.example.gps.viewModels.GpsTrackerViewModel

@Composable
fun HistoryTrackingScreen(
    navController: NavController,
    viewModel: GpsTrackerViewModel = hiltViewModel<GpsTrackerViewModel>()
) {
    val list by viewModel.wayList.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        itemsIndexed(list) { index, way ->
            WayComponent(
                modifier = Modifier.clickable {
                    viewModel.setDetailsWay(index)
                    navController.navigate(Screens.Details.route)
                },
                way = way
            )

        }
    }
}