package com.example.recipeapp.view

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.LayoutPhotoShowingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class PhotoShowFragment(
    private val imageURL: String
): DialogFragment() {

    companion object {
        private const val PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    private lateinit var binding: LayoutPhotoShowingBinding
    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
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

        binding.backImageButton.setOnClickListener{
            dialog?.dismiss()
        }

        binding.downloadImageButton.setOnClickListener {
            if(!checkPermission())
                requestPermissions.launch(PERMISSION)
            else {
                loadImage()
            }
        }
    }

    private fun checkPermission() = ActivityCompat.checkSelfPermission(requireActivity().applicationContext, PERMISSION) == PackageManager.PERMISSION_GRANTED

    private fun loadImage() {
        val bitmap = binding.photoView.drawable.toBitmap()
        val contentValues = ContentValues().apply {
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                Date().time
            )
            put(
                MediaStore.MediaColumns.MIME_TYPE,
                getString(R.string.mimeTypeImage)
            )
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES
            )
        }
        requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )?.let {uri ->
            requireContext().contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            Toast.makeText(activity, getString(R.string.imageSavedMessage), Toast.LENGTH_SHORT).show()
        }
    }
}