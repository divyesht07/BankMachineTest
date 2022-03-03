package com.example.ilabankmachinetest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ilabankmachinetest.R
import com.example.ilabankmachinetest.databinding.FragmentCarouselBinding
import com.example.ilabankmachinetest.utils.Utils
import com.example.ilabankmachinetest.viewmodel.MainViewModel
import kotlinx.coroutines.*

class CarouselFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentCarouselBinding

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val fragment = CarouselFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_carousel, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = requireArguments().getInt(ARG_POSITION)
        val imgUrl = viewModel.images.value?.get(position)
        imgUrl.let {
            if (it != null){
                CoroutineScope(Dispatchers.IO).launch{
                    val bitmap = Utils.loadImage(it)
                    withContext(Dispatchers.Main){
                        binding.img.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}