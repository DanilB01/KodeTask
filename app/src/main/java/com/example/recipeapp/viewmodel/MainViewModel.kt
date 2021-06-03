package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.model.MainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(app: Application): AndroidViewModel(app) {
    private val mainModel = MainModel(getApplication())

    private var _recipesList: MutableLiveData<List<Recipe>> = MutableLiveData()
    val recipesList: LiveData<List<Recipe>> = _recipesList

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    init {
        updateData()
    }

    fun updateData() {
        GlobalScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            _recipesList.value = emptyList()
            try {
                _recipesList.value = mainModel.getRecipes()
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }
}