package com.example.gps.navigation

sealed class Screens( val route : String) {
    object Permission : Screens("permission")
    object Home : Screens("home")
    object Details : Screens("details")
}