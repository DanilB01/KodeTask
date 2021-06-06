package com.example.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.R
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.model.RecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {
    private val model = RecipeModel()

    private var _recipesList: MutableLiveData<List<Recipe>> = MutableLiveData()

    private var _filteredRecipeList: MutableLiveData<List<Recipe>> = MutableLiveData()
    val filteredRecipeList: LiveData<List<Recipe>> = _filteredRecipeList

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    init {
        refreshData()
    }

    fun refreshData() {
        GlobalScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            _recipesList.value = emptyList()
            _filteredRecipeList.value = emptyList()
            try {
                _recipesList.value = model.getRecipes()
                _filteredRecipeList.value = _recipesList.value
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }

    fun filterData(query: String?): Boolean{
        return if(query != null) {
            _filteredRecipeList.value = _recipesList.value?.filter {
                it.name.contains(query, true) ||
                        it.instructions.contains(query, true) ||
                        it.description?.contains(query, true) == true
            }
            true
        } else {
            false
        }
    }

    fun sortData(sortOption: Int) {
        when(sortOption){
            R.id.sortByNameRadioButton -> {
                _filteredRecipeList.value = _filteredRecipeList.value?.sortedBy { it.name }
            }
            R.id.sortByDateRadioButton -> {
                _filteredRecipeList.value = _filteredRecipeList.value?.sortedBy { it.lastUpdated }
            }
        }
    }
}