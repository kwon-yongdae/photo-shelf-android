package com.yongdae.photoshelf.core.domain.usecase

import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository

class ToggleFavoriteUseCase(
    private val repository: PhotosRepository,
) {
    suspend operator fun invoke(
        photo: Photo,
    ) = repository.toggleFavorite(photo)
}
