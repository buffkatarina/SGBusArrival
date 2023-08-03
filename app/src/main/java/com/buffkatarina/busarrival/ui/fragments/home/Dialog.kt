package com.buffkatarina.busarrival.ui.fragments.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.buffkatarina.busarrival.R


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Dialog(dbProgress: Boolean, setDialogState: (bool: Boolean) -> Unit) {
    //Only show dialog when db is not done building
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            Modifier
                .clip(RoundedCornerShape(10.dp))
                .wrapContentSize()
                .background(colorResource(id = R.color.grey_100)),
            properties = DialogProperties(dismissOnBackPress = false,
                dismissOnClickOutside = false,
                SecureFlagPolicy.SecureOff),
            content = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)

                ) {
                    Text(text = stringResource(id = R.string.latest),
                        fontSize = 20.sp)

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 10.dp)
                    ) {

                        if (!dbProgress) { //when data base is not done building
                            Text(text = stringResource(id = R.string.building_database),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.grey_50))
                            Spacer(Modifier.weight(1f))
                            CircularProgressIndicator(
                                color = Color.Magenta,
                                modifier = Modifier
                                    .height(20.dp)
                                    .width(20.dp))
                        } else {
                            Text(text = stringResource(id = R.string.building_database_complete),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.grey_50),
                                modifier = Modifier.wrapContentSize(align = Alignment.TopCenter))
                            Spacer(Modifier.weight(1f))
                            Text(text = stringResource(id = R.string.ok),
                                fontSize = 15.sp,
                                color = Color.Magenta,
                                modifier = Modifier.clickable {
                                    openDialog.value = false
                                    setDialogState(true)
                                }
                            )

                        }
                    }
                }
            }
        )
    }
}




