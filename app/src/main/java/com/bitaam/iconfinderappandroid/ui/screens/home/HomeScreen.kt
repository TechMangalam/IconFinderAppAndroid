package com.bitaam.iconfinderappandroid.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun HomeScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val categories = viewModel.categories
    val result = viewModel.networkResult
    var search by rememberSaveable {
        mutableStateOf("")
    }
    var filterSearch by rememberSaveable {
        mutableStateOf("")
    }
    val filtered by remember(filterSearch, categories) {
        derivedStateOf {
            if (filterSearch.isEmpty()) categories else categories.filter {
                it.name.contains(
                    filterSearch,
                    true
                )
            }
        }
    }
    LaunchedEffect(key1 = result) {
        if (result is NetworkResult.Error) {
            result.exception.localizedMessage?.let { Log.d("api", it) }
            (result.exception).printStackTrace()
        }
    }
    LaunchedEffect(search) {
        delay(450)
        filterSearch = search
    }
    Scaffold(
        topBar = {
            AppBar("Home Screen", navController)
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (result) {
                NetworkResult.Loading -> {
                    LoadingView()
                }
                NetworkResult.Data -> {
                    if (categories.isEmpty()) {
                        EmptyView()
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            item {
                                TextField(
                                    value = search,
                                    onValueChange = { search = it },
                                    placeholder = { Text(text = "Search categories")},
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
                                CategoryView(it.name) {
                                    navController.navigate(
                                        Screens.IconSetsScreen.args(
                                            it.identifier,
                                            title = it.name
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