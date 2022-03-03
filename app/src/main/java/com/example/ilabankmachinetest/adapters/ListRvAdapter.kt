package com.example.ilabankmachinetest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.ilabankmachinetest.databinding.ListCellLayoutBinding
import com.example.ilabankmachinetest.model.Photos
import com.example.ilabankmachinetest.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class ListRvAdapter(private var photosList:ArrayList<Photos>) : RecyclerView.Adapter<ListRvAdapter.ViewHolder>(),Filterable{

    var filteredPhotosList = ArrayList<Photos>()

    init {
        filteredPhotosList = photosList
    }

    class ViewHolder(private val binding:ListCellLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Photos){
            binding.apply {

                photos = item

                CoroutineScope(Dispatchers.IO).launch{
                    val bitmap = Utils.loadImage(item.url)

                    withContext(Dispatchers.Main){
                        binding.img.setImageBitmap(bitmap)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListCellLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =filteredPhotosList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = filteredPhotosList.size

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(char: CharSequence?): FilterResults {
                val charSearch = char.toString()
                if(charSearch.isEmpty()){
                    filteredPhotosList = photosList
                }else{
                    val resultList = ArrayList<Photos>()
                    for(item in photosList){
                        if (item.title.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(item)
                        }
                    }
                    filteredPhotosList =resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredPhotosList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(char: CharSequence?, results: FilterResults?) {
                filteredPhotosList = results?.values as ArrayList<Photos>
                notifyDataSetChanged()
            }
        }
    }
}