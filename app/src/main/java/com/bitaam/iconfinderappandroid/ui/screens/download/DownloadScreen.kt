package com.bitaam.iconfinderappandroid.ui.screens.download

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaam.iconfinderappandroid.FileUtils.write
import com.bitaam.iconfinderappandroid.data.NetworkResult
import com.bitaam.iconfinderappandroid.ui.comps.AppBar
import com.bitaam.iconfinderappandroid.ui.comps.EmptyView
import com.bitaam.iconfinderappandroid.ui.comps.ErrorView
import com.bitaam.iconfinderappandroid.ui.comps.LoadingView
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun DownloadScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<DownloadViewModel>()
    val icon = viewModel.icon
    val result = viewModel.networkResult
    LaunchedEffect(key1 = result) {
        if (result is NetworkResult.Error) {
            (result.exception).printStackTrace()
        }
    }
    val scope = rememberCoroutineScope()
    val launchPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {

    }
    Scaffold(
        topBar = {
            AppBar("Download", navController)
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (result) {
                NetworkResult.Loading -> {
                    LoadingView()
                }
                NetworkResult.Data -> {
                    if (icon.icon_id == 0) {
                        EmptyView()
                    } else {
                        val context = LocalContext.current
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(icon.raster_sizes, key = { it }) {
                                DownloadView(icon.is_premium,it) { format, size ->
                                    scope.launch {
                                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P || ContextCompat.checkSelfPermission(
                                                context,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            ) == PackageManager.PERMISSION_GRANTED
                                        ) {
                                            val responseBody = viewModel.getFile(format, size)
                                            if (responseBody == null) {
                                                Toast.makeText(context, "Something happened", Toast.LENGTH_SHORT).show()
                                            } else {
                                                try {
                                                    with(context) {
                                                        write(context.contentResolver, UUID.randomUUID().toString(), format, responseBody)
                                                    }
                                                    Toast.makeText(context, "Saved to disk", Toast.LENGTH_SHORT).show()
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                    Toast.makeText(context, "Failed to save!!!", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            launchPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        }
                                    }
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