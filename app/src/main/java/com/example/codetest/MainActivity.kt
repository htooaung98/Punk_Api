package com.example.codetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codetest.databinding.ActivityMainBinding
import com.example.codetest.presentation.BeerAdapter
import com.example.codetest.presentation.BeerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var beerAdapter: BeerAdapter
    private lateinit var beerViewModel:BeerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()

        setAdapter()

        observePagingData()
    }

    private fun setUpViewModel(){
        beerViewModel = ViewModelProvider(this)[BeerViewModel::class.java]
    }

    private fun setAdapter(){
        beerAdapter = BeerAdapter()
        binding.rvBeer.adapter = beerAdapter
        binding.rvBeer.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun observePagingData(){
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                beerViewModel.beerPagingDataFlow.collectLatest { pagingData ->
                    beerAdapter.submitData(pagingData)
                }
            }
        }
    }


}