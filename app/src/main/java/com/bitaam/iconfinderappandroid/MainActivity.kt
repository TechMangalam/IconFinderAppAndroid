package com.bitaam.iconfinderappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bitaam.iconfinderappandroid.ui.screens.Screens
import com.bitaam.iconfinderappandroid.ui.screens.download.DownloadScreen
import com.bitaam.iconfinderappandroid.ui.screens.home.HomeScreen
import com.bitaam.iconfinderappandroid.ui.screens.icons.IconsScreen
import com.bitaam.iconfinderappandroid.ui.screens.iconsets.IconSetsScreen
import com.sanjaydevtech.sampleiconfinder.ui.theme.SampleIconFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleIconFinderTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Screens.HomeScreen.route) {
                    composable(Screens.HomeScreen.route) {
                        HomeScreen(navController)
                    }
                    composable(
                        Screens.IconSetsScreen.template(),
                        arguments = listOf(
                            navArgument("id") { type = NavType.StringType },
                            navArgument("title") {
                                type = NavType.StringType
                                defaultValue = "Iconsets Screen"
                            },
                        ),
                    ) {
                        IconSetsScreen(navController, it.arguments?.getString("title") ?: "")
                    }
                    composable(
                        Screens.IconsScreen.template(),
                        arguments = listOf(
                            navArgument("id") { type = NavType.IntType },
                            navArgument("title") {
                                type = NavType.StringType
                                defaultValue = "Icons Screen"
                            },
                        ),
                    ) {
                        IconsScreen(navController,it.arguments?.getString("title") ?: "")
                    }
                    composable(Screens.DownloadScreen.template(),
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            },
                            navArgument("title") {
                                type = NavType.StringType
                                defaultValue = "Download Screen"
                            },
                        ),
                    ) {
                        DownloadScreen(navController)
                    }
                }
            }
        }
    }
}
