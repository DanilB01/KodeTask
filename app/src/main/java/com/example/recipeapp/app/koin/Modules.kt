package com.example.recipeapp.app.koin

import com.example.recipeapp.model.PhotoModel
import com.example.recipeapp.model.RecipeModel
import com.example.recipeapp.network.Network
import com.example.recipeapp.network.RecipeApiRepository
import com.example.recipeapp.viewmodel.DetailsViewModel
import com.example.recipeapp.viewmodel.MainViewModel
import com.example.recipeapp.viewmodel.PhotoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { Network.getInterceptor() }
    single { Network.getHttpClient(interceptor = get()) }
    single { Network.getRetrofit(client = get()) }
}

val apiModule = module {
    single { Network.getApi(retrofit = get())}
    single { RecipeApiRepository(recipesApi = get()) }
}

val businessLogicModule = module {
    factory { RecipeModel(api = get()) }
    factory { PhotoModel(androidContext()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(model = get()) }
    viewModel { DetailsViewModel(model = get()) }
    viewModel { PhotoViewModel(model = get()) }
}