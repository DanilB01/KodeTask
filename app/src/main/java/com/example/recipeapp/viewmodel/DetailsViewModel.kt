package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.RecipeDetails
import com.example.recipeapp.model.RecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class DetailsViewModel(app:Application): AndroidViewModel(app) {
    private val model = RecipeModel(getApplication())

    private var _recipe: MutableLiveData<RecipeDetails> = MutableLiveData()
    val recipe: LiveData<RecipeDetails> = _recipe

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    fun setRecipe(recipeUuid: String?) {
        if(recipeUuid == null) {
            _isError.value = true
            return
        }
        GlobalScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            try {
                _recipe.value = model.getRecipe(recipeUuid)
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }
}