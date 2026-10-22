package com.yongdae.photoshelf.core.data.di

import com.yongdae.photoshelf.core.data.repository.DefaultPhotosRepository
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotosModule {
    @Binds
    internal abstract fun bindsPhotosRepository(
        repository: DefaultPhotosRepository,
    ): PhotosRepository
}
