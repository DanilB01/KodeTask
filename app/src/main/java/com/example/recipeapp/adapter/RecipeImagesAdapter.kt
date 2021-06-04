package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.adapter.interfaces.RecipeAdapterListener
import com.example.recipeapp.adapter.interfaces.RecipeImageAdapterListener
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.databinding.ItemRecipeBinding
import com.example.recipeapp.databinding.ItemRecipeImageBinding

class RecipeImagesAdapter(
        private val imageListener: RecipeImageAdapterListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val imageUrlList = mutableListOf<String>()

    fun updateDataSet(newUrlList: List<String>) {
        imageUrlList.clear()
        imageUrlList.addAll(newUrlList)
        notifyDataSetChanged()
    }

    class ViewHolder(
            private val itemBinding: ItemRecipeImageBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
                imageURL: String,
                imageListener: RecipeImageAdapterListener
        ) {
            Glide
                    .with(itemBinding.root)
                    .load(imageURL)
                    .into(itemBinding.imageView)
            itemBinding.imageView.setOnClickListener {
                imageListener.openImage(imageURL)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
                ItemRecipeImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(imageUrlList[position], imageListener)
    }

    override fun getItemCount() = imageUrlList.size
}