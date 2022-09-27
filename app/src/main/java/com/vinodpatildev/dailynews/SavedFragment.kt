package com.vinodpatildev.dailynews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.databinding.FragmentSavedBinding
import com.vinodpatildev.dailynews.presentation.adapter.NewsAdapter
import com.vinodpatildev.dailynews.presentation.viewmodel.NewsViewModel

class SavedFragment : Fragment() {
    private lateinit var fragmentSavedBinding: FragmentSavedBinding
    private lateinit var viewModel:NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSavedBinding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            if(AnythingNull(it)){
                val bundle = Bundle().apply {
                    putSerializable("selected_article",it)
                    putString("caller_fragment","saved_fragment")
                }
                findNavController().navigate(
                    R.id.action_savedFragment_to_infoFragment,
                    bundle
                )
            }else{
                Snackbar.make(view,"Can't open int other window.", Snackbar.LENGTH_LONG).show()
            }
        }
        initNewsRecyclerView()
        displayNewsInRecyclerView()
    }

    private fun initNewsRecyclerView() {
        fragmentSavedBinding.rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

    private fun displayNewsInRecyclerView() {
        showProgressBar()
        viewModel.getSavedNewsArticles().observe(viewLifecycleOwner,Observer{
            Log.i("logcat","data observed from saved fragment")
            newsAdapter.differ.submitList(it)
            hideProgressBar()
        })
    }

    private fun AnythingNull(it: Article): Boolean {
        return it.url != null && it.author != null && it.description != null && it.urlToImage != null && it.content != null && it.publishedAt != null && it.source != null && it.title != null
    }

    private fun showProgressBar(){
        fragmentSavedBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        fragmentSavedBinding.progressBar.visibility = View.INVISIBLE
    }
}