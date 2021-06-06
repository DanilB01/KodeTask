package com.example.recipeapp.model

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.example.recipeapp.R
import java.io.File
import java.io.OutputStream
import java.util.*

class PhotoModel(
        private val app: Context
) {
    fun tryLoadImage(bitmap: Bitmap): Boolean{
        val imageName = "${Date().time}.jpg"

        val outputStream = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            getOldStream(imageName)
        } else {
            getNewStream(imageName)
        }

        outputStream?.let{
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            return true
        }
        return false
    }

    private fun getOldStream(imageName: String): OutputStream {
        val imageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File(imageDir, imageName).outputStream()
    }

    private fun getNewStream(imageName: String): OutputStream? =
            with(app.contentResolver){
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                    put(MediaStore.MediaColumns.MIME_TYPE, app.resources.getString(R.string.mimeTypeImage))
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                )?.let { uri ->
                    openOutputStream(uri)
                }
            }
}