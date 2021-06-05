package com.example.recipeapp.network


class RecipeApiRepository(private val recipesApi: RecipeApi) {

    suspend fun getRecipes() = recipesApi.getRecipes().recipes

    suspend fun getRecipeDetails(uuid: String) = recipesApi.getRecipeDetails(uuid).recipe

}