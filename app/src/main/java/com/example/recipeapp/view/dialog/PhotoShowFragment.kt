package com.example.recipeapp.view.dialog

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.DialogPhotoShowingBinding
import com.example.recipeapp.utils.loadImageWithPlaceholder
import com.example.recipeapp.viewmodel.PhotoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoShowFragment: DialogFragment() {

    private val binding by lazy { DialogPhotoShowingBinding.inflate(layoutInflater) }
    private val viewModel: PhotoViewModel by viewModel()
    private val imageURL by lazy { arguments?.getString(requireActivity().getString(R.string.imageURL)) }

    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            saveImage()
        } else {
            Toast.makeText(requireActivity(), getString(R.string.forbiddenMessage), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(this)
            .load(imageURL)
            .placeholder(R.drawable.pic_error)
            .into(binding.photoView)

        loadImageWithPlaceholder(imageURL, binding.photoView)

        viewModel.isLoaded.observe(this){
            when(it) {
                true -> Toast.makeText(requireActivity(), getString(R.string.imageSavedMessage), Toast.LENGTH_SHORT).show()
                false -> Toast.makeText(requireActivity(), getString(R.string.savingErrorMessage), Toast.LENGTH_SHORT).show()
            }
        }

        binding.backImageButton.setOnClickListener{
            dialog?.dismiss()
        }

        binding.downloadImageButton.setOnClickListener {
            if(!checkPermission())
                requestPermissions.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            else {
                saveImage()
            }
        }
    }

    private fun saveImage() {
        val imageBitmap = binding.photoView.drawable.toBitmap()
        viewModel.saveImage(imageBitmap)
    }

    private fun checkPermission() =
            ActivityCompat.checkSelfPermission(
                    requireActivity().applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
}