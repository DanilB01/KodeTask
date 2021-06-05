package com.example.recipeapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.LayoutPhotoShowingBinding

class PhotoShowFragment(
    private val imageURL: String
): DialogFragment() {

    private lateinit var binding: LayoutPhotoShowingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.layout_photo_showing, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(this)
            .load(imageURL)
            .into(binding.photoView)

        binding.backImageButton.setOnClickListener{
            dialog?.dismiss()
        }

        binding.downloadImageButton.setOnClickListener {

        }
    }
}