package com.yongdae.photoshelf.feature.photos.photos

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestPhotosViewModel @Inject constructor(
    private val repository: PhotosRepository,
) : ViewModel() {

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds

    private val _state = mutableStateOf<List<Photo>>(emptyList())
    val state: State<List<Photo>> = _state

    private var isLoading = false

    private var nextPage = 1

    private val favoriteClicks = MutableSharedFlow<Photo>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            repository.observeFavoriteIds().collect { ids ->
                _favoriteIds.value = ids
            }
        }

        viewModelScope.launch {
            favoriteClicks.collectLatest { photo ->
                _favoriteIds.toggle(photo.id)
                runCatching {
                    repository.toggleFavorite(photo)
                }.onFailure {
                    _favoriteIds.toggle(photo.id)
                }
            }
        }
    }

    fun loadNextPage() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val fetched = repository.getPhotos(nextPage, PER_PAGE)
                val ids = _state.value.mapTo(mutableSetOf()) { it.id }
                _state.value += fetched.filter { ids.add(it.id) }
                nextPage++
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun onFavoriteClick(photo: Photo) {
        favoriteClicks.tryEmit(photo)
    }

    private fun MutableStateFlow<Set<String>>.toggle(id: String) =
        update { if (id in it) it - id else it + id }

    companion object {
        private const val PER_PAGE = 10
    }
}