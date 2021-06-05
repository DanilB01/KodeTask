package com.example.recipeapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipeImagesAdapter
import com.example.recipeapp.adapter.SimilarRecipeAdapter
import com.example.recipeapp.adapter.interfaces.RecipeImageAdapterListener
import com.example.recipeapp.databinding.ActivityDetailsBinding
import com.example.recipeapp.decoration.SimilarRecipeItemDecorator
import com.example.recipeapp.viewmodel.DetailsViewModel
import com.example.recipeapp.utils.formatText

class DetailsActivity : AppCompatActivity(), RecipeImageAdapterListener {

    private lateinit var binding: ActivityDetailsBinding
    private val model: DetailsViewModel by viewModels()
    private val imagesAdapter = RecipeImagesAdapter(this)
    private val similarRecipeAdapter = SimilarRecipeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this

        binding.imageViewPager.adapter = imagesAdapter
        binding.detailsView.similarRecyclerView.adapter = similarRecipeAdapter
        binding.detailsView.similarRecyclerView.addItemDecoration(
                SimilarRecipeItemDecorator(resources.getDimensionPixelSize(R.dimen.similarRecipePadding))
        )
        binding.dotsIndicator.setViewPager2(binding.imageViewPager)



        val recipeUuid = intent.getStringExtra(resources.getString(R.string.uuid))
        model.setRecipe(recipeUuid)

        model.recipe.observe(this) {
            binding.detailsView.nameTextView.text = it.name

            binding.detailsView.descriptionLabelTextView.isVisible = !it.description.isNullOrEmpty()
            binding.detailsView.descriptionTextView.isVisible = !it.description.isNullOrEmpty()
            binding.detailsView.descriptionTextView.text = it.description

            binding.detailsView.instructionTextView.text = it.instructions.formatText()
            binding.detailsView.difficultyRatingBar.rating = it.difficulty.toFloat()
            binding.detailsView.similarLabelTextView.isVisible = it.similar.isNotEmpty()

            imagesAdapter.updateDataSet(it.images)
            similarRecipeAdapter.updateDataSet(it.similar)
        }

        binding.imageViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.photoNumberChip.text = buildString {
                    append(position + 1)
                    append(resources.getString(R.string.slashSign))
                    append(imagesAdapter.itemCount)
                }
            }
        })
    }

    override fun openImage(imageURL: String) {
        PhotoShowFragment(imageURL).show(supportFragmentManager, resources.getString(R.string.photoShow))
    }

    override fun openRecipeDetails(recipeUuid: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(getString(R.string.uuid), recipeUuid)
        startActivity(intent)
    }
}