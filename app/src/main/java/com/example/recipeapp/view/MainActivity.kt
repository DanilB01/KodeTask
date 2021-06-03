package com.example.recipeapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.recipeapp.R
import com.example.recipeapp.decoration.RecipeDividerItemDecoration
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.adapter.interfaces.RecipeAdapterListener
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.viewmodel.MainViewModel

class MainActivity: AppCompatActivity(), RecipeAdapterListener {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    private val recipeRecyclerAdapter = RecipeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.recipeRecyclerView.adapter = recipeRecyclerAdapter

        binding.recipeRecyclerView.addItemDecoration(
                RecipeDividerItemDecoration(resources.getDimensionPixelSize(R.dimen.recyclerItemBottomPadding))
        )

        model.recipesList.observe(this){
            recipeRecyclerAdapter.updateDataSet(it)
        }

        model.isError.observe(this) {
            binding.placeholderView.isVisible = it
        }

        model.isLoading.observe(this) {
            binding.refreshLayout.isRefreshing = it
        }

        binding.refreshLayout.setOnRefreshListener {
            model.updateData()
        }
    }

    override fun openRecipeDetails(recipeUuid: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(getString(R.string.uuid), recipeUuid)
        startActivity(intent)
    }
}