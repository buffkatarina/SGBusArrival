package com.buffkatarina.busarrival.ui.fragments.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun DbDialog(dbProgress: Boolean, backgroundColor: Color = Color(0xFFCCCCCC),) {
    //Only show dialog when db is not done building
    if (!dbProgress) {
       Box(
           Modifier
               .clip(RectangleShape)
               .fillMaxWidth()
               .background(backgroundColor)
       ) {
           Text("Loading database")
       }
    }

}