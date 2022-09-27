package com.vinodpatildev.dailynews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.data.util.Resource
import com.vinodpatildev.dailynews.databinding.FragmentNewsBinding
import com.vinodpatildev.dailynews.presentation.adapter.NewsAdapter
import com.vinodpatildev.dailynews.presentation.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private lateinit var newsAdapter:NewsAdapter
    private val country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            if(AnythingNull(it)){
                val bundle = Bundle().apply {
                    putSerializable("selected_article",it)
                    putString("caller_fragment","news_fragment")
                }
                findNavController().navigate(
                    R.id.action_newsFragment_to_infoFragment,
                    bundle
                )
            }else{
                Snackbar.make(view,"Can't open int other window.",Snackbar.LENGTH_LONG).show()
            }
        }
        initNewsRecyclerView()
        displayNewsInRecyclerView()
        setSearchView()
    }

    private fun AnythingNull(it: Article): Boolean {
        return it.url != null && it.author != null && it.description != null && it.urlToImage != null && it.content != null && it.publishedAt != null && it.source != null && it.title != null && it.source.id != null && it.source.name != null
    }

    private fun setSearchView() {
        fragmentNewsBinding.svNews.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(searchQuery: String?): Boolean {
                    viewModel.getSearchedNewsHeadlines(country, searchQuery!!, page)
                    displaySearchedNewsInRecyclerView()
                    return false
                }

                override fun onQueryTextChange(searchQuery: String?): Boolean {
//                    viewModel.getSearchedNewsHeadlines(country,searchQuery!!, page)
//                    displaySearchedNewsInRecyclerView()
                    return false
                }

            }
        )
    }

    private fun initNewsRecyclerView() {
        fragmentNewsBinding.rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    private fun displaySearchedNewsInRecyclerView() {
        viewModel.searchedNewsHeadlines.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    Log.i("success","success")
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        if(it.totalResults%20 == 0) {
                            pages = it.totalResults / 20
                        }else{
                            pages = it.totalResults/20+1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Loading->{
                    Log.i("success","loading")
                    showProgressBar()
                }
                is Resource.Error->{
                    Log.i("success","error")
                    hideProgressBar()
                    response.message?.let{
                        Toast.makeText(activity,"Error Occured:$it",Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    private fun displayNewsInRecyclerView() {
        viewModel.getNewsHeadlines(country,page)

        viewModel.newsHeadlines.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        if(it.totalResults%20 == 0) {
                            pages = it.totalResults / 20
                        }else{
                            pages = it.totalResults/20+1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let{
                        Toast.makeText(activity,"Error Occured:$it",Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    private fun showProgressBar(){
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if(shouldPaginate){
                page++
                viewModel.getNewsHeadlines(country,page)
                isScrolling = false
            }
        }
    }
}