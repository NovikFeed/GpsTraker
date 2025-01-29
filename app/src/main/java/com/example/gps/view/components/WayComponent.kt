package com.example.gps.view.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gps.model.data.local.Way
import kotlin.random.Random

@Composable
fun WayComponent(way: Way, modifier: Modifier){
    val backGroundColor = getRandomColor()
    Column(modifier = modifier.fillMaxSize().padding(8.dp).background(Color(backGroundColor))) {
        Text(modifier = Modifier.padding(5.dp), text = "Маршрут від ${way.startDate}")
        Column (modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally){
            if(way.startAddress.getAddressLine(0) != null && way.destinationAddress.getAddressLine(0) != null){
            Text(text = way.startAddress.getAddressLine(0).replace(Regex(",\\s*[^,]+\\s*,\\s*\\d+$"), ""))
            Text(text = "↓", fontSize = 20.sp, fontWeight = FontWeight.W900, textAlign = TextAlign.Center)
            Text(text = way.destinationAddress.getAddressLine(0).replace(Regex(",\\s*[^,]+\\s*,\\s*\\d+$"), ""))}
        }


    }
}

private fun getRandomColor(): Int {
    val alpha = 200
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
}