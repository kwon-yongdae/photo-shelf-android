package com.yongdae.photoshelf.core.data.datasorce.remote.service

import com.yongdae.photoshelf.core.data.datasorce.remote.model.PhotoDto
import com.yongdae.photoshelf.core.data.datasorce.remote.model.PhotoStatisticsDto
import com.yongdae.photoshelf.core.data.datasorce.remote.model.PhotoUrlDto

interface PhotosService {
    suspend fun getPhotos(page: Int? = 1, perPage: Int? = 10): List<PhotoDto>

    suspend fun getPhoto(id: String): PhotoDto

    suspend fun getPhotoStatistics(
        id: String,
        resolution: String? = "days",
        quantity: Int? = 30
    ): PhotoStatisticsDto

    suspend fun trackPhotoDownload(id: String): PhotoUrlDto
}