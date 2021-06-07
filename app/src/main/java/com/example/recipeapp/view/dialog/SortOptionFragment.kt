package com.example.recipeapp.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.recipeapp.R
import com.example.recipeapp.databinding.DialogSortOprionBinding

class SortOptionFragment(
        private val sortListener: SortOptionListener,
        private val selectedOption: Int
): DialogFragment() {

    private val binding by lazy { DialogSortOprionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
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

        binding.sortRadioGroup.check(selectedOption)

        binding.cancelSortButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.sortButton.setOnClickListener {
            sortListener.getSortOption(binding.sortRadioGroup.checkedRadioButtonId)
            dialog?.dismiss()
        }
    }
}