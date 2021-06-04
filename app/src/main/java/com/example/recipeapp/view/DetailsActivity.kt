package com.example.recipeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.databinding.ActivityDetailsBinding
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.viewmodel.DetailsViewModel
import com.example.recipeapp.viewmodel.MainViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val model: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this

        val recipeUuid = intent.getStringExtra(resources.getString(R.string.uuid))
        model.setRecipe(recipeUuid)

        model.recipe.observe(this) {
            binding.detailsView.nameTextView.text = it.name
            binding.detailsView.descriptionTextView.text = it.description
            binding.detailsView.instructionTextView.text = it.instructions
            binding.detailsView.difficultyRatingBar.rating = it.difficulty.toFloat()
        }
    }
}