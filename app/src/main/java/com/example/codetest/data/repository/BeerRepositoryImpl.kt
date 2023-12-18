package com.example.codetest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.codetest.data.entities.BeerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val beerPager: Pager<Int, BeerEntity>
) : BeerRepository {
    override fun getAllBeers(): Flow<PagingData<BeerEntity>> {
        return beerPager.flow.map { pagingData ->
            pagingData.map { it }
        }
    }
}