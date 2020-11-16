package id.xxx.data.source.firebase.firestore.history.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<Entity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg varargEntity: Entity): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Entity): Long

    @Delete
    suspend fun delete(entity: Entity): Int

    @Update
    suspend fun update(entity: Entity): Int
}