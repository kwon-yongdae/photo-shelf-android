package com.yongdae.photoshelf.core.data.datasorce.remote

import com.yongdae.photoshelf.core.data.datasorce.remote.model.toModel
import com.yongdae.photoshelf.core.data.datasorce.remote.service.PhotosService
import com.yongdae.photoshelf.core.domain.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class RemotePhotosDataSource @Inject constructor(
    private val photosService: PhotosService
) {
    suspend fun getPhotos(page: Int?, perPage: Int?): List<Photo> {
        return photosService.getPhotos(page, perPage).map { it.toModel() }
    }

    suspend fun getPhoto(id: String): Photo {
        return photosService.getPhoto(id).toModel()
    }

    suspend fun downloadPhoto(id: String): ByteArray {
        val imageUrl = photosService.trackPhotoDownload(id).url
            ?: throw IOException("Download url is empty: $id")

        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(imageUrl)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Image download failed: HTTP ${response.code}")
                }

                val body = response.body
                    ?: throw IOException("Image response body is empty")

                body.bytes()
            }
        }
    }

    private companion object {
        val client: OkHttpClient by lazy { OkHttpClient.Builder().build() }
    }
}
