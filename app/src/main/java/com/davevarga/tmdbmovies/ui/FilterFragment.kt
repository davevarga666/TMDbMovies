package com.davevarga.tmdbmovies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.davevarga.tmdbmovies.R
import com.davevarga.tmdbmovies.databinding.FragmentFilterBinding
import com.davevarga.tmdbmovies.models.Years
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_filter, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener { view: View ->
            val filterByYearAction = FilterFragmentDirections.actionFilterFragmentToListFragment(
                minYear = minYear_value.text.toString(),
                maxYear = maxYear_value.text.toString()
            )
            view.findNavController().navigate(filterByYearAction)

        }
    }

}