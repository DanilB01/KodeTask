package com.example.recipeapp.view

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.recipeapp.R
import com.example.recipeapp.decoration.RecipeItemDecoration
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.adapter.interfaces.RecipeAdapterListener
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.view.dialog.PhotoShowFragment
import com.example.recipeapp.view.dialog.SortOptionFragment
import com.example.recipeapp.view.dialog.SortOptionListener
import com.example.recipeapp.viewmodel.MainViewModel

class MainActivity: AppCompatActivity(), RecipeAdapterListener, SortOptionListener, SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    private val recipeRecyclerAdapter = RecipeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setSupportActionBar(binding.mainToolbar)

        binding.recipeRecyclerView.adapter = recipeRecyclerAdapter

        binding.recipeRecyclerView.addItemDecoration(
                RecipeItemDecoration(resources.getDimensionPixelSize(R.dimen.recyclerItemBottomPadding))
        )

        model.filteredRecipeList.observe(this){
            recipeRecyclerAdapter.updateDataSet(it)
        }

        model.isError.observe(this) {
            binding.placeholderView.isVisible = it
        }

        model.isLoading.observe(this) {
            binding.refreshLayout.isRefreshing = it
        }

        binding.refreshLayout.setOnRefreshListener {
            model.refreshData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val search = menu?.findItem(R.id.searchItem)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sortItem -> {
                SortOptionFragment(this).show(supportFragmentManager, getString(R.string.sortOption))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun openRecipeDetails(recipeUuid: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(getString(R.string.uuid), recipeUuid)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return model.filterData(query)
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return model.filterData(query)
    }

    override fun getSortOption(sortOption: Int) {
        model.sortData(sortOption)
    }
}