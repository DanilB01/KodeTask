package com.example.recipeapp.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipeImagesAdapter
import com.example.recipeapp.adapter.SimilarRecipeAdapter
import com.example.recipeapp.adapter.interfaces.RecipeImageAdapterListener
import com.example.recipeapp.data.RecipeDetails
import com.example.recipeapp.databinding.ActivityDetailsBinding
import com.example.recipeapp.decoration.SimilarRecipeItemDecorator
import com.example.recipeapp.viewmodel.DetailsViewModel
import com.example.recipeapp.utils.formatText
import com.example.recipeapp.view.dialog.PhotoShowFragment

class DetailsActivity : AppCompatActivity(), RecipeImageAdapterListener {

    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private val model: DetailsViewModel by viewModels()
    private val imagesAdapter = RecipeImagesAdapter(this)
    private val similarRecipeAdapter = SimilarRecipeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recipeUuid = intent.getStringExtra(resources.getString(R.string.uuid))
        model.setRecipe(recipeUuid)

        setUpToolbar()
        setUpDynamicLists()
        setUpAppBar()

        model.recipe.observe(this) {
            onRecipeChanged(it)
        }

        model.isError.observe(this) {
            if(it)
                showErrorToast()
        }

        model.isLoading.observe(this) {
            binding.recipeDetailsProgressBar.isVisible = it
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

    private fun setUpAppBar() {
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val layoutParams = binding.appBarLayout.layoutParams
            layoutParams.height = resources.getDimensionPixelSize(R.dimen.appBarLayoutHeightLandscape)
            binding.appBarLayout.layoutParams = layoutParams
        }
    }

    private fun setUpToolbar() {
        binding.detailsToolbar.title = ""
        setSupportActionBar(binding.detailsToolbar)
        binding.detailsToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setUpDynamicLists() {
        binding.imageViewPager.adapter = imagesAdapter
        binding.detailsView.similarRecyclerView.adapter = similarRecipeAdapter
        binding.detailsView.similarRecyclerView.addItemDecoration(
                SimilarRecipeItemDecorator(resources.getDimensionPixelSize(R.dimen.recipeItemBordersPadding), resources.getDimensionPixelSize(R.dimen.recipeItemDividerPadding))
        )
        binding.dotsIndicator.setViewPager2(binding.imageViewPager)
    }

    private fun onRecipeChanged(recipe: RecipeDetails) {
        binding.detailsView.nameTextView.text = recipe.name

        binding.detailsView.descriptionLabelTextView.isVisible = !recipe.description.isNullOrEmpty()
        binding.detailsView.descriptionTextView.isVisible = !recipe.description.isNullOrEmpty()
        binding.detailsView.descriptionTextView.text = recipe.description

        binding.detailsView.instructionTextView.text = recipe.instructions.formatText()
        binding.detailsView.difficultyRatingBar.rating = recipe.difficulty.toFloat()
        binding.detailsView.similarLabelTextView.isVisible = recipe.similar.isNotEmpty()

        imagesAdapter.updateDataSet(recipe.images)
        similarRecipeAdapter.updateDataSet(recipe.similar)
    }

    override fun openImage(imageURL: String) {
        val photoFragment = PhotoShowFragment()
        val args = Bundle()
        args.putString(getString(R.string.imageURL), imageURL)
        photoFragment.arguments = args
        photoFragment.show(supportFragmentManager, resources.getString(R.string.photoShow))
    }

    override fun openRecipeDetails(recipeUuid: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(getString(R.string.uuid), recipeUuid)
        startActivity(intent)
    }

    private fun showErrorToast() {
        Toast.makeText(this, getString(R.string.errorMessage), Toast.LENGTH_LONG).show()
    }
}