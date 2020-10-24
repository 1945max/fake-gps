package id.xxx.data.source.fake.gps.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<Entity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insert(vararg varargEntity: Entity): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Entity): Long

    @Delete
    fun delete(entity: Entity): Int

    @Update
    suspend fun update(entity: Entity): Int
}