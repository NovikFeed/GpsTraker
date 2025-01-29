package com.example.gps.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gps.viewModels.GpsTrackerViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.random.Random

@Composable
fun WayDetails(viewModel: GpsTrackerViewModel = hiltViewModel()) {
    val way by viewModel.detailsWay.collectAsState()
    val animatePolyline by viewModel.animatePolyline.collectAsState()
    val centralPoint = way[0].arrayCoordinate[(way[0].arrayCoordinate.size) / 2]
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val duration = ChronoUnit.MINUTES.between(LocalDateTime.parse(way[0].startDate, formatter), LocalDateTime.parse(way[0].endDate, formatter))
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centralPoint, 12f)
    }
    Scaffold(modifier = Modifier.background(MaterialTheme.colorScheme.background),topBar = { Text(text = "Маршрут від ${way[0].startDate}", fontSize = 24.sp) }, content =
    {
        Column(modifier = Modifier.padding(it)) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                cameraPositionState = cameraPosition
            ) {
                AnimatedOrNotPolyline(way[0].arrayCoordinate, isAnimate = animatePolyline)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                RowInTable(
                    t1 = "Унікальний номер",
                    t2 = way[0].uniqueKey,
                    color = Color.Gray
                )
                RowInTable(
                    t1 = "Дата початку",
                    t2 = way[0].startDate,
                )
                RowInTable(
                    t1 = "Дата закінчення",
                    t2 = way[0].endDate,
                    color = Color.Gray
                )
                RowInTable(
                    t1 = "Тривалість",
                    t2 = if(duration.toInt() > 60) "${(duration/60)} год." else "${(duration)} хв."
                )
                RowInTable(
                    t1 = "Відстань",
                    t2 = "%.2f".format(way[0].range.toBigDecimal()) + " км",
                    color = Color.Gray
                )
                RowInTable(
                    t1 = "Середня швидкість",
                    t2 = way[0].avgSpeed + " км/год"
                )
                Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Center){
                    Button(onClick = {viewModel.setAnimation()}) {
                        Text("Start animation", fontSize = 16.sp)
                    }
                }
            }
        }
    })
}
@Composable
fun RowInTable(
    t1 : String,
    t2 : String,
    color : Color = Color.White
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color)
        .border(1.dp, Color.Black, shape = RectangleShape)
        .padding(start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = t1)
        Text(text = t2)
    }
}
@Composable
fun AnimatedOrNotPolyline(points: List<LatLng>, durationMillis: Long = 3000L, isAnimate : Boolean) {
    if(isAnimate) {
        var visibility = false
        Marker(state = rememberMarkerState(position = points[0]), title = "start position", icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        val animatedPoints = remember { mutableStateListOf<LatLng>() }
        LaunchedEffect(points) {
            for (i in points.indices) {
                animatedPoints.add(points[i])
                delay(durationMillis / points.size)
            }
            visibility = true
        }
        Polyline(
            points = animatedPoints.toList(),
            color = randomColor(),
            width = 5f
        )
        Marker(state = rememberMarkerState(position = points[points.size-1]), title = "end position", visible = visibility)

    }
    else{
        Marker(state = rememberMarkerState(position = points[0]), title = "start position", icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        Polyline(
            points = points,
            color = Color.Black,
            width = 5f
        )
        Marker(state = rememberMarkerState(position = points[points.size-1]), title = "end position")

    }
}
private fun randomColor(): Color {
    return Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat(),
        alpha = 1f
    )
}