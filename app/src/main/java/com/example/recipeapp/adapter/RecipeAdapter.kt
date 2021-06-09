package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.databinding.ItemRecipeBinding
import com.example.recipeapp.adapter.interfaces.RecipeAdapterListener
import com.example.recipeapp.utils.loadImage

class RecipeAdapter(
    private val recipeListener: RecipeAdapterListener
): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val recipeList = mutableListOf<Recipe>()

    fun updateDataSet(newRecipeList: List<Recipe>){
        recipeList.clear()
        recipeList.addAll(newRecipeList)
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(private val itemBinding: ItemRecipeBinding): RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.recipeCardView.setOnClickListener {
                recipeListener.openRecipeDetails(recipeList[adapterPosition].uuid)
            }
        }
        fun bind(recipe: Recipe){
            itemBinding.recipeItemNameTextView.text = recipe.name
            itemBinding.recipeItemDescriptionTextView.isVisible = !recipe.description.isNullOrEmpty()
            itemBinding.recipeItemDescriptionTextView.text = recipe.description

            val imageURL = recipe.images.firstOrNull()
            if(imageURL != null) {
                loadImage(imageURL, itemBinding.recipeImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding =
                ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount() = recipeList.size

}