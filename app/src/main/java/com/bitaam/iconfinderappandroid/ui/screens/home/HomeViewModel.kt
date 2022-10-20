package com.bitaam.iconfinderappandroid.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.bitaam.iconfinderappandroid.data.IconFinderService
import com.bitaam.iconfinderappandroid.data.NetworkResult
import com.bitaam.iconfinderappandroid.data.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: IconFinderService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var networkResult: NetworkResult by savedStateHandle.saveable {
        mutableStateOf(NetworkResult.Loading)
    }

    var categories: List<Category> by savedStateHandle.saveable {
        mutableStateOf(emptyList())
    }

    init {
        viewModelScope.launch {
            viewModelScope.launch {
                withMutableSnapshot {
                    networkResult = NetworkResult.Loading
                }
                try {
                    val response = service.listAllCategories()
                    withMutableSnapshot {
                        categories = (response.categories)
                        networkResult = (NetworkResult.Data)
                    }
                } catch (e: Exception) {
                    withMutableSnapshot {
                        networkResult = (NetworkResult.Error(e))
                    }
                }
            }
        }

    }

}