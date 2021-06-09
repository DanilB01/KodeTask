package com.example.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.RecipeDetails
import com.example.recipeapp.model.RecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsViewModel: ViewModel() {
    private val model = RecipeModel()

    private var _recipe: MutableLiveData<RecipeDetails> = MutableLiveData()
    val recipe: LiveData<RecipeDetails> = _recipe

    private var _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

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