package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.databinding.ItemRecipeBinding
import com.example.recipeapp.adapter.interfaces.RecipeAdapterListener

class RecipeAdapter(private val activity: RecipeAdapterListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val recipeList = mutableListOf<Recipe>()

    fun updateDataSet(newRecipeList: List<Recipe>){
        recipeList.clear()
        recipeList.addAll(newRecipeList)
        notifyDataSetChanged()
    }

    class ViewHolder(private val itemBinding: ItemRecipeBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(recipe: Recipe, activity: RecipeAdapterListener){
            itemBinding.recipeItemNameTextView.text = recipe.name
            itemBinding.recipeItemDescriptionTextView.text = recipe.description
            itemBinding.recipeCardView.setOnClickListener {
                activity.openRecipeDetails(recipe.uuid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
                ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(recipeList[position], activity)
    }

    override fun getItemCount() = recipeList.size

}