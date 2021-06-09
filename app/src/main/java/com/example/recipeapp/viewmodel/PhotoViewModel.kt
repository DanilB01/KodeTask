package com.example.recipeapp.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.PhotoModel

class PhotoViewModel(
    private val model: PhotoModel
): ViewModel() {

    private var _isPermitted: MutableLiveData<Boolean> = MutableLiveData()
    val isPermitted: LiveData<Boolean> = _isPermitted

    private var _isLoaded: MutableLiveData<Boolean> = MutableLiveData()
    val isLoaded: LiveData<Boolean> = _isLoaded

    fun loadImage(bitmap: Bitmap) {
        _isLoaded.value = model.tryLoadImage(bitmap)
    }

    fun setPermittedStatus(status: Boolean) {
        _isPermitted.value = status
    }
}