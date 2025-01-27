package com.example.gps.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.gps.navigation.Screens
import com.example.gps.view.util.HorizontalPagerIndicator
import com.example.gps.viewModels.GpsTrackerViewModel
import com.example.gps.viewModels.PermissionViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    permissionViewModel: PermissionViewModel,
    gpsTrackerViewModel: GpsTrackerViewModel,
    navController: NavHostController
) {
    val permission by permissionViewModel.havePermission.collectAsState()
    val pageCount = 2
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
    if (permission) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(modifier = Modifier.weight(0.9f), state = pagerState) { page ->
                when (page) {
                    0 -> StartStopTrackingScreen(gpsTrackerViewModel)
                    1 -> HistoryTrackingScreen(navController, gpsTrackerViewModel)
                }

            }
            HorizontalPagerIndicator(
                pageCount = pageCount,
                currentPage = pagerState.currentPage,
                targetPage = pagerState.targetPage,
                currentPageOffsetFraction = pagerState.currentPageOffsetFraction
            )
        }
    } else {
        navController.popBackStack()
        navController.navigate(Screens.Permission.route)
    }

}