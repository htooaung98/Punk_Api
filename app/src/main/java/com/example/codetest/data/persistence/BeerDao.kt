package com.example.codetest.data.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codetest.data.entities.BeerEntity

@Dao
interface BeerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBeers(beers: List<BeerEntity>)

    @Query("select * from beer")
    fun getAllBeers() : PagingSource<Int, BeerEntity>

    @Query("delete from beer")
    suspend fun clearAll()
}