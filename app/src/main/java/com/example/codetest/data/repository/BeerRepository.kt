package com.example.codetest.data.repository

import androidx.paging.PagingData
import com.example.codetest.data.entities.BeerEntity
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    fun getAllBeers(): Flow<PagingData<BeerEntity>>
}