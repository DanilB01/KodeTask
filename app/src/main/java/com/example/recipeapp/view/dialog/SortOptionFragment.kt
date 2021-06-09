package com.example.recipeapp.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.recipeapp.R
import com.example.recipeapp.databinding.DialogSortOprionBinding

class SortOptionFragment: DialogFragment() {

    private val binding by lazy { DialogSortOprionBinding.inflate(layoutInflater) }
    private val selectedOption by lazy { arguments?.getInt(requireActivity().getString(R.string.selectedOption)) }

    private val sortListener by lazy { activity as? SortOptionListener }

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

        selectedOption?.let{
            binding.sortRadioGroup.check(it)
        }

        binding.cancelSortButton.setOnClickListener {
            dismiss()
        }

        binding.sortButton.setOnClickListener {
            sortListener?.getSortOption(binding.sortRadioGroup.checkedRadioButtonId)
            dialog?.dismiss()
        }
    }
}