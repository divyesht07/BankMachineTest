package com.example.ilabankmachinetest.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ilabankmachinetest.views.CarouselFragment
import com.example.ilabankmachinetest.views.MainActivity

class CarouselVpAdapter(activity: MainActivity, private val itemsCount:Int) :
FragmentStateAdapter(activity){

    override fun getItemCount(): Int {
       return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return CarouselFragment.getInstance(position)

    }
}