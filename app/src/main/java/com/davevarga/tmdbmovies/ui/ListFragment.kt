package com.davevarga.tmdbmovies.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.davevarga.tmdbmovies.R
import com.davevarga.tmdbmovies.R.id.action_listFragment_to_filterFragment
import com.davevarga.tmdbmovies.models.Movie
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), MovieClickListener {

    lateinit var swipeLayout: SwipeRefreshLayout
    val args: ListFragmentArgs by navArgs()

    private val viewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            MovieViewModelFactory(requireActivity().application, args.minYear, args.maxYear)
        )
            .get(MovieViewModel::class.java)
    }


    private val movieList: List<Movie> = arrayListOf()
    private val viewModelAdapter = MovieRecyclerAdapter(movieList, this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.delete()
        hideKeyboard()
        viewModel.insert(args.minYear, args.maxYear)
        swipeLayout = swipeRefresh
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        filterButton.setOnClickListener { view: View ->
            view.findNavController().navigate(action_listFragment_to_filterFragment)
            Log.d("ListFr", viewModelAdapter.items.size.toString())

        }

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
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

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onItemClick(item: Movie, position: Int) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }

}