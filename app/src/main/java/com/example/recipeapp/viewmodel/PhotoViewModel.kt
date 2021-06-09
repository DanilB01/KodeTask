package com.example.recipeapp.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.PhotoModel

class PhotoViewModel(
    private val model: PhotoModel
): ViewModel() {
    private var _isLoaded: MutableLiveData<Boolean> = MutableLiveData()
    val isLoaded: LiveData<Boolean> = _isLoaded

    fun saveImage(bitmap: Bitmap) {
        _isLoaded.value = model.trySaveImage(bitmap)
    }
}