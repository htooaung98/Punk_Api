package com.example.codetest.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codetest.data.entities.BeerEntity
import com.example.codetest.data.entities.RemoteKeyEntity

@Database(entities = [BeerEntity::class, RemoteKeyEntity::class], version = 2, exportSchema = false)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}