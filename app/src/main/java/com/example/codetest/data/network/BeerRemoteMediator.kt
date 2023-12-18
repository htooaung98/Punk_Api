@file:OptIn(ExperimentalPagingApi::class)

package com.example.codetest.data.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.codetest.data.entities.BeerEntity
import com.example.codetest.data.entities.RemoteKeyEntity
import com.example.codetest.data.entities.toBeerEntityList
import com.example.codetest.data.persistence.BeerDatabase
import com.example.codetest.utils.DEFAULT_PAGE_PER_COUNT
import javax.inject.Inject

class BeerRemoteMediator @Inject constructor (
    private val apiService: PunkApi,
    private val database: BeerDatabase
) : RemoteMediator<Int, BeerEntity>() {

    private val REMOTE_KEY_ID = "beer"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.remoteKeyDao().getById(REMOTE_KEY_ID)
                    if (remoteKey == null) return MediatorResult.Success(endOfPaginationReached = true)

                    remoteKey.nextOffset
                }
            }

            val beerResponse = apiService.getBeers(
                page = offset,
                perPage = DEFAULT_PAGE_PER_COUNT
            )
            val results = beerResponse?.toBeerEntityList() ?: emptyList()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.beerDao().clearAll()
                    database.remoteKeyDao().deleteById(REMOTE_KEY_ID)
                }
                database.beerDao().insertAllBeers(results)

                // add next offset if current data is not empty
                if (results.isNotEmpty()) {
                    database.remoteKeyDao().insert(
                        RemoteKeyEntity(
                            id = REMOTE_KEY_ID,
                            nextOffset = offset + 1
                        )
                    )
                }
            }

            return MediatorResult.Success(endOfPaginationReached = results.size < state.config.pageSize)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}