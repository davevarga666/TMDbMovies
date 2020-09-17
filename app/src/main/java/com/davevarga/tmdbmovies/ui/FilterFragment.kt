package com.davevarga.tmdbmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.davevarga.tmdbmovies.R
import com.davevarga.tmdbmovies.databinding.FragmentFilterBinding
import com.davevarga.tmdbmovies.models.Years
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment : Fragment() {

    //to be replaced with a proper solution for dates
    companion object {
        var range = Years("2019", "2020")

    }

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
            range = Years(minYear.toString(), maxYear.toString())
            val filterByYearAction = FilterFragmentDirections.actionFilterFragmentToListFragment()
            view.findNavController().navigate(filterByYearAction)

        }
    }

}