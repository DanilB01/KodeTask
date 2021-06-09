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
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.DialogPhotoShowingBinding
import com.example.recipeapp.model.PhotoModel
import com.example.recipeapp.viewmodel.PhotoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoShowFragment: DialogFragment() {

    private val binding by lazy { DialogPhotoShowingBinding.inflate(layoutInflater) }
    private val viewModel: PhotoViewModel by viewModel()
    private val imageURL by lazy { arguments?.getString(requireActivity().getString(R.string.imageURL)) }

    private val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        viewModel.setPermittedStatus(result)
        if (result) {
            loadImage()
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

        viewModel.isPermitted.observe(this){
            if(!it) {
                Toast.makeText(requireActivity(), getString(R.string.forbiddenMessage), Toast.LENGTH_SHORT).show()
            }
        }

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
                requestPermissions.launch(permission)
            else {
                loadImage()
            }
        }
    }

    private fun loadImage() {
        val imageBitmap = binding.photoView.drawable.toBitmap()
        viewModel.loadImage(imageBitmap)
    }

    private fun checkPermission() =
            ActivityCompat.checkSelfPermission(
                    requireActivity().applicationContext,
                    permission
            ) == PackageManager.PERMISSION_GRANTED
}