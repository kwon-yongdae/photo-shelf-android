package com.yongdae.photoshelf.core.data.datasorce.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yongdae.photoshelf.core.data.datasorce.local.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePhotosDao {
    @Query("SELECT * FROM favorite_photos WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): PhotoEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_photos WHERE id = :id)")
    fun observeIsFavorite(id: String): Flow<Boolean>

    @Query("SELECT id FROM favorite_photos")
    fun observeFavoriteIds(): Flow<List<String>>

    @Query("SELECT * FROM favorite_photos")
    fun observeAll(): Flow<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: PhotoEntity)

    @Query("DELETE FROM favorite_photos WHERE id = :id")
    suspend fun deleteById(id: String)
}
