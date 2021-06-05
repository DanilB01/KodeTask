package com.example.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.model.RecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {
    private val model = RecipeModel()

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
                _recipesList.value = model.getRecipes()
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }
}