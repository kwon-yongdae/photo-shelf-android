package com.yongdae.photoshelf.core.data.datasorce.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yongdae.photoshelf.core.data.datasorce.local.dao.FavoritePhotosDao
import com.yongdae.photoshelf.core.data.datasorce.local.entity.PhotoEntity


@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritePhotosDatabase: RoomDatabase() {
    abstract fun favoritePhotosDao(): FavoritePhotosDao
}
