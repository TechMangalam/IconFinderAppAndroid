package com.bitaam.iconfinderappandroid.ui.screens.icons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaam.iconfinderappandroid.data.NetworkResult
import com.bitaam.iconfinderappandroid.ui.comps.AppBar
import com.bitaam.iconfinderappandroid.ui.comps.EmptyView
import com.bitaam.iconfinderappandroid.ui.comps.ErrorView
import com.bitaam.iconfinderappandroid.ui.comps.LoadingView
import com.bitaam.iconfinderappandroid.ui.screens.Screens

@Composable
fun IconsScreen(
    navController: NavController,
    title: String,
) {
    val viewModel = hiltViewModel<IconsViewModel>()
    val icons = viewModel.icons
    val result = viewModel.networkResult
    LaunchedEffect(key1 = result) {
        if (result is NetworkResult.Error) {
            (result.exception).printStackTrace()
        }
    }
    Scaffold(
        topBar = {
            AppBar(title, navController)
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (result) {
                NetworkResult.Loading -> {
                    LoadingView()
                }
                NetworkResult.Data -> {
                    if (icons.isEmpty()) {
                        EmptyView()
                    } else {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(3)
                        ) {
                            items(icons, key = { it.icon_id }) {
                                IconsView(it) {
                                    navController.navigate(
                                        Screens.DownloadScreen.args(
                                            it.icon_id,
                                            "$title - Download",
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
                else -> {
                    ErrorView()
                }
            }
        }
    }
}