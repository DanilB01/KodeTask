package com.example.app.network


class RecipeApiRepository(private val recipesApi: RecipeApi) {

    suspend fun getRecipes() = recipesApi.getRecipes().recipes

    suspend fun getStatuses(uuid: String) = recipesApi.getRecipeDetails(uuid).recipe

}