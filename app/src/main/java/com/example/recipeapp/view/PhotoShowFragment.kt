package com.example.recipeapp.view

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.LayoutPhotoShowingBinding
import com.example.recipeapp.viewmodel.MainViewModel
import com.example.recipeapp.viewmodel.PhotoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.OutputStream
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class PhotoShowFragment(
    private val imageURL: String
): DialogFragment() {

    private lateinit var binding: LayoutPhotoShowingBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(PhotoViewModel::class.java)
    }
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