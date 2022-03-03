package com.example.ilabankmachinetest.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.net.URL

class Utils {

    companion object{

        fun getJsonDataFromAssets(context: Context, fileName:String) : String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }

        fun loadImage(imgUrl:String) : Bitmap{
            val url = URL(imgUrl)
            return BitmapFactory.decodeStream(url.openConnection().getInputStream())
        }
    }

}