package com.yongdae.photoshelf.core.data.di

import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import com.yongdae.photoshelf.core.domain.usecase.GetFavoritePhotosUseCase
import com.yongdae.photoshelf.core.domain.usecase.GetPhotoUseCase
import com.yongdae.photoshelf.core.domain.usecase.GetPhotosUseCase
import com.yongdae.photoshelf.core.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetPhotosUseCase(
        photosRepository: PhotosRepository
    ): GetPhotosUseCase {
        return GetPhotosUseCase(photosRepository)
    }

    @Provides
    @Singleton
    fun providesGetPhotoUseCase(
        photosRepository: PhotosRepository
    ): GetPhotoUseCase {
        return GetPhotoUseCase(photosRepository)
    }

    @Provides
    @Singleton
    fun providesGetFavoritePhotosUseCase(
        photosRepository: PhotosRepository
    ): GetFavoritePhotosUseCase {
        return GetFavoritePhotosUseCase(photosRepository)
    }

    @Provides
    @Singleton
    fun providesToggleFavoriteUseCase(
        photosRepository: PhotosRepository
    ): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(photosRepository)
    }
}