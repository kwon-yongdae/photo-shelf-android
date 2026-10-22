package com.yongdae.photoshelf.feature.photos.favorites

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosViewModel @Inject constructor(
    private val repository: PhotosRepository,
) : ViewModel() {

    private val _state = mutableStateOf<List<Photo>>(emptyList())
    val state: State<List<Photo>> = _state

    fun loadPage() {
        viewModelScope.launch {
            repository.observeFavoritePhotos().collect {
                _state.value = it
            }
        }
    }
}