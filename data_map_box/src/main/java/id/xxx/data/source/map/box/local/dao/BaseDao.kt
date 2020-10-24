package id.xxx.data.source.map.box.local.dao

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