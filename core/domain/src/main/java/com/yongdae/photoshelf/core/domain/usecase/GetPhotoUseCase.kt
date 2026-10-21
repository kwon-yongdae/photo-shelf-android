package com.yongdae.photoshelf.core.domain.usecase

import com.yongdae.photoshelf.core.domain.model.Photo
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository

class GetPhotoUseCase(
    private val repository: PhotosRepository,
) {
    suspend operator fun invoke(
        id: String,
    ): Photo = repository.getPhoto(id)
}
