package com.example.ilabankmachinetest.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ilabankmachinetest.R
import com.example.ilabankmachinetest.adapters.CarouselVpAdapter
import com.example.ilabankmachinetest.adapters.ListRvAdapter
import com.example.ilabankmachinetest.databinding.ActivityMainBinding
import com.example.ilabankmachinetest.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var viewModel:MainViewModel
    lateinit var binding:ActivityMainBinding
    lateinit var adapter: ListRvAdapter
    lateinit var carouselVpAdapter:CarouselVpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        carouselVpAdapter = CarouselVpAdapter(this,4)
        binding.carouselVp.adapter = carouselVpAdapter
        TabLayoutMediator(binding.tabLayout,binding.carouselVp){tab,position->
        }.attach()
        observers()

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun observers(){
        viewModel.photosList.observe(this, Observer {
            adapter = ListRvAdapter(it)
            binding.listRv.adapter = adapter
        })
    }

}