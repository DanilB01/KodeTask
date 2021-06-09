package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.adapter.interfaces.RecipeImageAdapterListener
import com.example.recipeapp.databinding.ItemRecipeImageBinding
import com.example.recipeapp.utils.loadImage

class RecipeImagesAdapter(
        private val imageListener: RecipeImageAdapterListener
): RecyclerView.Adapter<RecipeImagesAdapter.RecipeImageViewHolder>() {

    private val imageUrlList = mutableListOf<String>()

    fun updateDataSet(newUrlList: List<String>) {
        imageUrlList.clear()
        imageUrlList.addAll(newUrlList)
        notifyDataSetChanged()
    }

    inner class RecipeImageViewHolder(
            private val itemBinding: ItemRecipeImageBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.imageView.setOnClickListener {
                imageListener.openImage(imageUrlList[adapterPosition])
            }
        }
        fun bind(imageURL: String) {
            loadImage(imageURL, itemBinding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeImageViewHolder {
        val itemBinding =
                ItemRecipeImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeImageViewHolder, position: Int) {
        holder.bind(imageUrlList[position])
    }

    override fun getItemCount() = imageUrlList.size
}