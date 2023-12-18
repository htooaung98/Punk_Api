package com.example.codetest.domain.usecase

import androidx.paging.PagingData
import com.example.codetest.data.entities.BeerEntity
import com.example.codetest.data.repository.BeerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetBeerUseCase @Inject constructor(private val repository: BeerRepository) {
    operator fun invoke() : Flow<PagingData<BeerEntity>> {
        return repository.getAllBeers().flowOn(Dispatchers.IO)
    }
}