package com.bitaam.iconfinderappandroid.ui.screens.iconsets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaam.iconfinderappandroid.R
import com.bitaam.iconfinderappandroid.data.NetworkResult
import com.bitaam.iconfinderappandroid.ui.comps.AppBar
import com.bitaam.iconfinderappandroid.ui.comps.EmptyView
import com.bitaam.iconfinderappandroid.ui.comps.ErrorView
import com.bitaam.iconfinderappandroid.ui.comps.LoadingView
import com.bitaam.iconfinderappandroid.ui.screens.Screens
import kotlinx.coroutines.delay

@Composable
fun IconSetsScreen(
    navController: NavController,
    title: String,
) {
    val viewModel = hiltViewModel<IconSetsViewModel>()
    val iconsets = viewModel.iconsets
    val result = viewModel.networkResult
    var search by rememberSaveable {
        mutableStateOf("")
    }
    var filterSearch by rememberSaveable {
        mutableStateOf("")
    }
    val filtered by remember(filterSearch, iconsets) {
        derivedStateOf {
            if (filterSearch.isEmpty()) iconsets else iconsets.filter {
                it.name.contains(
                    filterSearch,
                    true
                )
            }
        }
    }
    LaunchedEffect(key1 = result) {
        if (result is NetworkResult.Error) {
            (result.exception).printStackTrace()
        }
    }
    LaunchedEffect(search) {
        delay(450)
        filterSearch = search
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
                    if (iconsets.isEmpty()) {
                        EmptyView()
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            item {
                                TextField(
                                    value = search,
                                    onValueChange = { search = it },
                                    placeholder = { Text(text = "Search iconsets")},
                                    modifier = Modifier.fillMaxWidth(),
                                    leadingIcon = {
                                      Icon(painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = null)
                                    },
                                    trailingIcon = {
                                        IconButton(onClick = {search = ""}) {
                                            Icon(painterResource(id = R.drawable.ic_baseline_close_24), contentDescription = "Clear")
                                        }
                                    }
                                )
                            }
                            items(filtered, key = { it.identifier }) {
                                IconsetView(it) {
                                    navController.navigate(
                                        Screens.IconsScreen.args(
                                            it.iconset_id,
                                            "$title > ${it.name}",
                                        )
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