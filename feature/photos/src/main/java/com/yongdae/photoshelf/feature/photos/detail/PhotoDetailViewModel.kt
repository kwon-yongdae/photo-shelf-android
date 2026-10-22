package com.yongdae.photoshelf.feature.photos.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {
    private val _photo = MutableStateFlow<Photo?>(null)
    val photo: StateFlow<Photo?> = _photo

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _favoriteEnabled = MutableStateFlow(true)
    val favoriteEnabled: StateFlow<Boolean> = _favoriteEnabled

    fun onFavoriteClick() {
        viewModelScope.launch {
            photo.value?.run {
                _favoriteEnabled.value = false
                try {
                    repository.toggleFavorite(this)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _favoriteEnabled.value = true
                }
            }
        }
    }

    fun loadPhoto(id: String) {
        viewModelScope.launch {
            try {
                repository.observeIsFavorite(id).collect { favorite ->
                    _isFavorite.value = favorite
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        viewModelScope.launch {
            try {
                _photo.value = repository.getPhoto(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}