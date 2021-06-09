package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.adapter.interfaces.RecipeAdapterListener
import com.example.recipeapp.data.RecipeBrief
import com.example.recipeapp.databinding.ItemSimilarRecipeBinding
import com.example.recipeapp.utils.loadImage

class SimilarRecipeAdapter(
        private val recipeListener: RecipeAdapterListener
): RecyclerView.Adapter<SimilarRecipeAdapter.SimilarRecipeViewHolder>() {

    private val recipeList = mutableListOf<RecipeBrief>()

    fun updateDataSet(newRecipeList: List<RecipeBrief>){
        recipeList.clear()
        recipeList.addAll(newRecipeList)
        notifyDataSetChanged()
    }

    inner class SimilarRecipeViewHolder(private val itemBinding: ItemSimilarRecipeBinding): RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.similarRecipeCardView.setOnClickListener {
                recipeListener.openRecipeDetails(recipeList[adapterPosition].uuid)
            }
        }
        fun bind(recipe: RecipeBrief){
            itemBinding.similarRecipeNameTextView.text = recipe.name
            loadImage(recipe.image, itemBinding.similarRecipeImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarRecipeViewHolder {
        val itemBinding =
                ItemSimilarRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarRecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SimilarRecipeViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount() = recipeList.size

}