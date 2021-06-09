package com.example.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.R
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.model.RecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

class MainViewModel(
    private val model: RecipeModel
): ViewModel() {

    private var sortOption = 0
    private var query = ""

    private var _recipesList: MutableLiveData<List<Recipe>> = MutableLiveData()

    private var _filteredRecipeList: MutableLiveData<List<Recipe>> = MutableLiveData()
    val filteredRecipeList: LiveData<List<Recipe>> = _filteredRecipeList

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private var _isNotFound: MutableLiveData<Boolean> = MutableLiveData()
    val isNotFound: LiveData<Boolean> = _isNotFound

    init {
        refreshData()
    }

    private fun filterData() {
        _isError.value = false
        _filteredRecipeList.value = _recipesList.value?.filter {
            it.name.contains(query, true) ||
                    it.instructions.contains(query, true) ||
                    it.description?.contains(query, true) == true
        }
        _isNotFound.value = _filteredRecipeList.value.isNullOrEmpty()
        sortData()
    }

    private fun sortData() {
        when(sortOption){
            R.id.sortByNameRadioButton -> {
                _filteredRecipeList.value = _filteredRecipeList.value?.sortedBy { it.name }
            }
            R.id.sortByDateRadioButton -> {
                _filteredRecipeList.value = _filteredRecipeList.value?.sortedByDescending { it.lastUpdated }
            }
        }
    }

    fun getSelectedSortOption() = sortOption

    fun getFilterQuery() = query

    fun refreshData() {
        viewModelScope.launch(Dispatchers.Main) {
            _isNotFound.value = false
            _isLoading.value = true
            _recipesList.value = emptyList()
            _filteredRecipeList.value = emptyList()
            try {
                _recipesList.value = model.getRecipes()
                _filteredRecipeList.value = _recipesList.value
                filterData()
                sortData()
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }

    fun setSortOption(newSortOption: Int) {
        sortOption = newSortOption
        sortData()
    }

    fun setDataFilter(newQuery: String?): Boolean{
        return if(newQuery != null) {
            query = newQuery
            filterData()
            true
        } else {
            false
        }
    }
}