package com.example.gps.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.example.gps.R
import com.example.gps.navigation.Screens
import com.example.gps.viewModels.PermissionViewModel

@Composable
fun PermissionScreen(permissionViewModel: PermissionViewModel, navController: NavController) {
    val havePermission by permissionViewModel.havePermission.collectAsState(false)
    if (havePermission) {
        navController.popBackStack()
        permissionViewModel.stopCheckPermission()
        navController.navigate(Screens.Home.route)

    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This app doesn't make sense without permissions, please grant permissions",
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
            Image(painter = painterResource(id = R.drawable.screen), contentDescription = "screenshot")
            Button(onClick = {permissionViewModel.goToSettings()}) {
                Text(text = "To Settings")
            }
        }
    }
}