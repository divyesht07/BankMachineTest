package com.example.ilabankmachinetest.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ilabankmachinetest.model.Photos
import com.example.ilabankmachinetest.utils.Utils
import org.json.JSONArray

class MainViewModel(application: Application) : AndroidViewModel(application){

    private val _images = MutableLiveData<ArrayList<String>>()
    val images:LiveData<ArrayList<String>> = _images
    private val _photosList = MutableLiveData<ArrayList<Photos>>()
    val photosList:LiveData<ArrayList<Photos>> = _photosList

    init {
        var context = application.applicationContext
        getCarouselData()
        getPhotos(context)
    }

    private fun getCarouselData(){
        val imgList = ArrayList<String>()
        imgList.add("https://images.unsplash.com/photo-1512325525506-d15b8e5866e8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=938&q=80")
        imgList.add("https://images.unsplash.com/photo-1534413298607-48ba59e8a06d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80")
        imgList.add("https://images.unsplash.com/photo-1624254009481-94fb3b9cf16e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=846&q=80")
        imgList.add("https://images.unsplash.com/photo-1489893117776-b13fed1ad239?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80")
        _images.value = imgList
    }

    private fun getPhotos(context: Context){
        val photosJson = Utils.getJsonDataFromAssets(context,"photos.json")
        val list = arrayListOf<Photos>()
        val photosJsonArray = JSONArray(photosJson)
        for(item in 0 until photosJsonArray.length()){
            val photoJsonObject = photosJsonArray.getJSONObject(item)
            val photo = Photos(
                photoJsonObject.getInt("albumId"),
                photoJsonObject.getInt("id"),
                photoJsonObject.getString("title"),
                photoJsonObject.getString("url"),
                photoJsonObject.getString("thumbnailUrl")
            )
            list.add(photo)
        }
        _photosList.value = list
    }
}