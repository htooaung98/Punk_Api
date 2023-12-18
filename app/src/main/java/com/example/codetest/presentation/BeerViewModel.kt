package com.example.codetest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.codetest.data.DataSource
import com.example.codetest.data.entities.BeerEntity
import com.example.codetest.domain.usecase.GetBeerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(private val beerUseCase: GetBeerUseCase) : ViewModel() {
    var beerPagingDataFlow: Flow<PagingData<BeerEntity>> = beerUseCase().cachedIn(viewModelScope)
}
