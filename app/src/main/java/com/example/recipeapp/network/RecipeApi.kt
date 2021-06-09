package com.example.recipeapp.network

import com.example.recipeapp.network.dto.RecipeDetailsDto
import com.example.recipeapp.network.dto.RecipesListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes(): RecipesListDto

    @GET("recipes/{uuid}")
    suspend fun getRecipeDetails(@Path("uuid") uuid: String): RecipeDetailsDto
}