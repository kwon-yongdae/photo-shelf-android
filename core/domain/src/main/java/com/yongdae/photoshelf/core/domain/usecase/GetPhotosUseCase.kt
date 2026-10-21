package com.yongdae.photoshelf.core.domain.usecase

import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository

class GetPhotosUseCase(
    private val repository: PhotosRepository,
) {
    suspend operator fun invoke(
        page: Int,
        perPage: Int,
    ): List<Photo> = repository.getPhotos(page, perPage)
}
