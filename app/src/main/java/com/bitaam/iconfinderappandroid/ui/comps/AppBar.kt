package com.bitaam.iconfinderappandroid.ui.comps

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.bitaam.iconfinderappandroid.R

@Composable
fun AppBar(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24), contentDescription = "Back")
                }
            }
        } else null
    )

}