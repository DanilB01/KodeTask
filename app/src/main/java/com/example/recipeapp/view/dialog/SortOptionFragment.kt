package com.example.recipeapp.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.recipeapp.R
import com.example.recipeapp.databinding.DialogSortOprionBinding

class SortOptionFragment(
        private val sortListener: SortOptionListener
): DialogFragment() {

    private lateinit var binding: DialogSortOprionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.dialog_sort_oprion, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelSortButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.sortButton.setOnClickListener {
            sortListener.getSortOption(binding.sortRadioGroup.checkedRadioButtonId)
            dialog?.dismiss()
        }
    }
}