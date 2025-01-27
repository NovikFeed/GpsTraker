package com.example.gps.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gps.navigation.Screens
import com.example.gps.view.components.WayDetails
import com.example.gps.viewModels.GpsTrackerViewModel
import com.example.gps.viewModels.PermissionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Composable
fun MainContent() {
    val permissionViewModel = hiltViewModel<PermissionViewModel>()
    val gpsTrackerViewModel = hiltViewModel<GpsTrackerViewModel>()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Permission.route) {
        composable(Screens.Permission.route) {
            PermissionScreen(permissionViewModel, navController)
        }
        composable(Screens.Home.route) {
            HomeScreen(permissionViewModel,gpsTrackerViewModel, navController)
        }
        composable(Screens.Details.route){
            WayDetails(gpsTrackerViewModel)
        }
    }
}