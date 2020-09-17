package com.davevarga.tmdbmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davevarga.tmdbmovies.R
import com.davevarga.tmdbmovies.R.id.action_listFragment_to_filterFragment
import com.davevarga.tmdbmovies.models.Movie
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), MovieClickListener {

    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MovieViewModel::class.java)

    }

    private val emptyList: List<Movie> = emptyList()
    private val viewModelAdapter = MovieRecyclerAdapter(emptyList, this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        filterButton.setOnClickListener { view: View ->
            view.findNavController().navigate(action_listFragment_to_filterFragment)

        }
        observeMovieModel()
    }

    private fun observeMovieModel() {
        viewModel.movieList.observe(viewLifecycleOwner, Observer { items ->
            items?.apply {
                viewModelAdapter.items = items
                recycler_view.adapter = viewModelAdapter
            }
        })

    }

    override fun onItemClick(item: Movie, position: Int) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }
}