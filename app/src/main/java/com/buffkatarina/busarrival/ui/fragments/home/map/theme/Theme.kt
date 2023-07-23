//package com.buffkatarina.busarrival.ui.fragments.home.map.theme
//
//import android.os.Build
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//
//
//@Composable
//fun OsmAndroidComposeTheme(
//    d// Dynamic color is available on Android 12+
//    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
//    val colorScheme = when {
//        dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
//        dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
//        darkTheme -> darkColorScheme()
//        else -> lightColorScheme()
//    }
//
//    MaterialTheme(
//    colorScheme = colorScheme,
//    typography = typography,
//    shapes = shapes
//) {