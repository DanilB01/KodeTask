package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.adapter.interfaces.RecipeAdapterListener
import com.example.recipeapp.data.RecipeBrief
import com.example.recipeapp.databinding.ItemRecipeBinding
import com.example.recipeapp.databinding.ItemSimilarRecipeBinding

class SimilarRecipeAdapter(
        private val recipeListener: RecipeAdapterListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val recipeList = mutableListOf<RecipeBrief>()

    fun updateDataSet(newRecipeList: List<RecipeBrief>){
        recipeList.clear()
        recipeList.addAll(newRecipeList)
        notifyDataSetChanged()
    }

    class ViewHolder(private val itemBinding: ItemSimilarRecipeBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(recipe: RecipeBrief, recipeListener: RecipeAdapterListener){
            itemBinding.similarRecipeCardView.cardElevation = 4f
            itemBinding.similarRecipeNameTextView.text = recipe.name
                Glide
                        .with(itemBinding.root)
                        .load(recipe.image)
                        .into(itemBinding.similarRecipeImageView)
            itemBinding.similarRecipeCardView.setOnClickListener {
                recipeListener.openRecipeDetails(recipe.uuid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
                ItemSimilarRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(recipeList[position], recipeListener)
    }

    override fun getItemCount() = recipeList.size

}