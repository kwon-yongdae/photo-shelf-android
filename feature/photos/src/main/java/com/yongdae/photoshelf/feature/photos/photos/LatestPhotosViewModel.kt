package com.yongdae.photoshelf.feature.photos.photos

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestPhotosViewModel @Inject constructor(
    private val repository: PhotosRepository,
) : ViewModel() {

    val favoriteIds: Flow<Set<String>> = repository.observeFavoriteIds()
    private val _state = mutableStateOf<List<Photo>>(emptyList())
    val state: State<List<Photo>> = _state

    private var isLoading = false

    private var nextPage = 1

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
        viewModelScope.launch {
            try {
                repository.toggleFavorite(photo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val PER_PAGE = 10
    }
}