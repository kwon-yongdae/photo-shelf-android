package com.yongdae.photoshelf.feature.photos.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val repository: PhotosRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id: String = requireNotNull(savedStateHandle["id"])

    private val _photo = MutableStateFlow<Photo?>(null)
    val photo: StateFlow<Photo?> = _photo

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val favoriteClicks = MutableSharedFlow<Photo>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            try {
                repository.observeIsFavorite(id).collect { favorite ->
                    _isFavorite.value = favorite
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModelScope.launch {
            try {
                _photo.value = repository.getPhoto(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModelScope.launch {
            favoriteClicks.collectLatest { photo ->
                _isFavorite.value = !isFavorite.value
                runCatching {
                    repository.toggleFavorite(photo)
                }.onFailure {
                    _isFavorite.value = !isFavorite.value
                }
            }
        }
    }

    fun onFavoriteClick() {
        photo.value?.let(favoriteClicks::tryEmit)
    }
}