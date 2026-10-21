package com.yongdae.photoshelf.core.data.datasorce.remote.service.retrofit

import com.yongdae.photoshelf.core.data.datasorce.remote.service.PhotosService
import com.yongdae.photoshelf.core.data.datasorce.remote.model.PhotoDto
import com.yongdae.photoshelf.core.data.datasorce.remote.model.PhotoStatisticsDto
import com.yongdae.photoshelf.core.data.datasorce.remote.model.PhotoUrlDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RetrofitPhotosService : PhotosService {

    @GET(value = "photos")
    override suspend fun getPhotos(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?,
    ): List<PhotoDto>

    @GET(value = "photos/{id}")
    override suspend fun getPhoto(
        @Path("id") id: String
    ): PhotoDto

    @GET(value = "photos/{id}/statistics")
    override suspend fun getPhotoStatistics(
        id: String,
        resolution: String?,
        quantity: Int?
    ): PhotoStatisticsDto

    @GET(value = "photos/{id}/download")
    override suspend fun trackPhotoDownload(
        @Path("id") id: String
    ): PhotoUrlDto
}
