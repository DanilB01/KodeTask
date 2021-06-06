package com.example.recipeapp.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.model.PhotoModel

class PhotoViewModel(application: Application): AndroidViewModel(application) {

    private val model = PhotoModel(application.applicationContext)

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