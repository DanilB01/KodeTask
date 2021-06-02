package com.example.recipeapp.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.viewmodel.MainViewModel

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        model.isError.observe(this) {
            binding.placeholderView.isVisible = it
        }

        model.isLoading.observe(this) {
            binding.refreshLayout.isRefreshing = it
        }

        binding.refreshLayout.setOnRefreshListener {
            model.updateData()
        }
    }
}