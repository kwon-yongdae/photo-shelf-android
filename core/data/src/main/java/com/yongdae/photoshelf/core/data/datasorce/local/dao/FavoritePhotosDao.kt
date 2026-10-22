package com.yongdae.photoshelf.core.data.datasorce.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.yongdae.photoshelf.core.data.datasorce.local.entity.PhotoEntity
@Dao
interface FavoritePhotosDao {
    @Query("SELECT * FROM favorite_photos WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): PhotoEntity
}
