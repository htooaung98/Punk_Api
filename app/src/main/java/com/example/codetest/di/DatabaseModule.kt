package com.example.codetest.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.codetest.data.entities.BeerEntity
import com.example.codetest.data.network.BeerRemoteMediator
import com.example.codetest.data.network.PunkApi
import com.example.codetest.data.persistence.BeerDao
import com.example.codetest.data.persistence.BeerDatabase
import com.example.codetest.data.repository.BeerRepository
import com.example.codetest.data.repository.BeerRepositoryImpl
import com.example.codetest.utils.DEFAULT_PAGE_PER_COUNT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesBeerDatabase(@ApplicationContext context: Context): BeerDatabase {
        return Room.databaseBuilder(
            context, BeerDatabase::class.java, "beer_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesBeerDao(database: BeerDatabase): BeerDao {
        return database.beerDao()
    }


    @Provides
    @Singleton
    fun providesBeerPaging(
        database: BeerDatabase, apiService: PunkApi
    ): Pager<Int, BeerEntity> {
        return Pager(config = PagingConfig(pageSize = DEFAULT_PAGE_PER_COUNT),
            remoteMediator = BeerRemoteMediator(
                database = database, apiService = apiService
            ),
            pagingSourceFactory = {
                database.beerDao().getAllBeers()
            })
    }

    @Provides
    fun providesBeerRepository(pager: Pager<Int, BeerEntity>): BeerRepository {
        return BeerRepositoryImpl(pager)
    }
}