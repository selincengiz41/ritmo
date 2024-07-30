package com.selincengiz.ritmo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.selincengiz.ritmo.data.local.entities.TrackEntity

@Database(entities = [TrackEntity::class],version = 1,)
@TypeConverters(RitmoTypeConverter::class)
abstract class RitmoDatabase : RoomDatabase() {

    abstract val ritmoDao: RitmoDao

}