package com.example.recipeapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.adapter.RecipeImagesAdapter
import com.example.recipeapp.adapter.SimilarRecipeAdapter
import com.example.recipeapp.adapter.interfaces.RecipeImageAdapterListener
import com.example.recipeapp.databinding.ActivityDetailsBinding
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.decoration.SimilarRecipeItemDecorator
import com.example.recipeapp.viewmodel.DetailsViewModel
import com.example.recipeapp.viewmodel.MainViewModel

class DetailsActivity : AppCompatActivity(), RecipeImageAdapterListener {

    private lateinit var binding: ActivityDetailsBinding
    private val model: DetailsViewModel by viewModels()
    private val imagedAdapter = RecipeImagesAdapter(this)
    private val similarRecipeAdapter = SimilarRecipeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this

        binding.imageViewPager.adapter = imagedAdapter
        binding.detailsView.similarRecyclerView.adapter = similarRecipeAdapter
        binding.detailsView.similarRecyclerView.addItemDecoration(
                SimilarRecipeItemDecorator(resources.getDimensionPixelSize(R.dimen.similarRecipePadding))
        )

        val recipeUuid = intent.getStringExtra(resources.getString(R.string.uuid))
        model.setRecipe(recipeUuid)

        model.recipe.observe(this) {
            binding.detailsView.nameTextView.text = it.name
            binding.detailsView.descriptionTextView.text = it.description
            binding.detailsView.instructionTextView.text = it.instructions
            binding.detailsView.difficultyRatingBar.rating = it.difficulty.toFloat()
            binding.detailsView.similarLabelTextView.isVisible = it.similar.isNotEmpty()

            imagedAdapter.updateDataSet(it.images)
            similarRecipeAdapter.updateDataSet(it.similar)
        }
    }

    override fun openImage(imageURL: String) {

    }

    override fun openRecipeDetails(recipeUuid: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(getString(R.string.uuid), recipeUuid)
        startActivity(intent)
    }
}