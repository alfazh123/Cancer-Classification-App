package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.ClasificationEntity

@Dao
interface ClasificationDao {
    @Query("SELECT * FROM clasification_entity")
    fun getAllClasification(): LiveData<List<ClasificationEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertClasification(clasificationEntity: ClasificationEntity)

    @Query("DELETE FROM clasification_entity WHERE clasification_id = :clasificationId")
    fun deleteClasification(clasificationId: Int)

    @Query("DELETE FROM clasification_entity")
    fun deleteAllClasification()

    @Query("SELECT COUNT(*) FROM clasification_entity")
    fun getCount(): Int
}