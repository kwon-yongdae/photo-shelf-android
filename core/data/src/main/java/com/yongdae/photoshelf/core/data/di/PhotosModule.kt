package com.yongdae.photoshelf.core.data.di

import com.yongdae.photoshelf.core.data.datasorce.PhotosDataSource
import com.yongdae.photoshelf.core.data.datasorce.local.LocalPhotosDataSource
import com.yongdae.photoshelf.core.data.datasorce.remote.RemotePhotosDataSource
import com.yongdae.photoshelf.core.data.repository.DefaultPhotosRepository
import com.yongdae.photoshelf.core.domain.repository.PhotosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotosModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class LocalDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class RemoteDataSource

    @Binds
    @LocalDataSource
    internal abstract fun bindsLocalPhotosDataSource(
        repository: LocalPhotosDataSource,
    ): PhotosDataSource

    @Binds
    @RemoteDataSource
    internal abstract fun bindsRemotePhotosDataSource(
        repository: RemotePhotosDataSource,
    ): PhotosDataSource

    @Binds
    internal abstract fun bindsPhotosRepository(
        repository: DefaultPhotosRepository,
    ): PhotosRepository
}
