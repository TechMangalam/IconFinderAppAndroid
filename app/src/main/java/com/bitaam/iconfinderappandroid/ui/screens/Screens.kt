package com.bitaam.iconfinderappandroid.ui.screens

sealed class Screens(val route: String) {
    object HomeScreen : Screens("/")
    object IconSetsScreen : Screens("/icon-sets/") {
        fun template() = "$route/{id}?title={title}"
        fun args(id: String, title: String) = "$route/$id?title=$title"
    }
    object IconsScreen : Screens("/icons/") {
        fun template() = "$route/{id}?title={title}"
        fun args(id: Int, title: String) = "$route/$id?title=$title"
    }
    object DownloadScreen : Screens("/view/") {
        fun template() = "$route/{id}?title={title}"
        fun args(id: Int, title: String) = "$route/$id?title=$title"
    }
}