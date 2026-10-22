package com.yongdae.photoshelf.core.data.di

import android.content.Context
import androidx.room.Room
import com.yongdae.photoshelf.core.data.datasorce.local.dao.FavoritePhotosDao
import com.yongdae.photoshelf.core.data.datasorce.local.database.FavoritePhotosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesFavoritePhotosDatabase(
        @ApplicationContext context: Context,
    ): FavoritePhotosDatabase = Room.databaseBuilder<FavoritePhotosDatabase>(
        context,
        "favorite-photos-database"
    ).build()

    @Provides
    fun providesFavoritePhotosDao(
        database: FavoritePhotosDatabase,
    ): FavoritePhotosDao = database.favoritePhotosDao()
}
