package com.example.app.network


class RecipeApiRepository(private val recipesApi: RecipeApi) {

    suspend fun getRecipes() = recipesApi.getRecipes().recipes

    suspend fun getRecipeDetails(uuid: String) = recipesApi.getRecipeDetails(uuid).recipe

}