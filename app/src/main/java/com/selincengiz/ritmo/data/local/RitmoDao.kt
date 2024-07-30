package com.selincengiz.ritmo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.selincengiz.ritmo.data.local.entities.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RitmoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: TrackEntity)

    @Delete
    suspend fun delete(track: TrackEntity)

    @Query("SELECT * FROM Tracks")
    fun getTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM Tracks WHERE id=:id")
    suspend fun getTrack(id: String): TrackEntity?

}