package com.isranascimento.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.isranascimento.databasedtos.hotels.HotelDatabaseEntity
import com.isranascimento.databasedtos.hotels.HotelsAmenityDatabaseEntity
import com.isranascimento.databasedtos.hotels.HotelsGalleryItemDatabaseEntity

@Database(
    entities = [
        HotelDatabaseEntity::class,
        HotelsAmenityDatabaseEntity::class,
        HotelsGalleryItemDatabaseEntity::class
   ],
    version = 1
)
abstract class HotelsRoomDatabase: RoomDatabase() {
    abstract fun hotelsDao(): HotelsDAO

    companion object {
        @Volatile
        private var INSTANCE: HotelsRoomDatabase? = null

        fun getDatabase(context: Context): HotelsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HotelsRoomDatabase::class.java,
                    "recent_search_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}