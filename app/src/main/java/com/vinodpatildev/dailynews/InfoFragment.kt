package com.vinodpatildev.dailynews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.vinodpatildev.dailynews.databinding.FragmentInfoBinding
import com.vinodpatildev.dailynews.presentation.viewmodel.NewsViewModel

class InfoFragment : Fragment() {

    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentInfoBinding = FragmentInfoBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        val args: InfoFragmentArgs by navArgs()
        val article = args.selectedArticle
        val callerFrag = args.callerFragment
        fragmentInfoBinding.wvNews.apply {
            webViewClient = WebViewClient()
            if (article.url != null) {
                loadUrl(article.url)
            }
        }


        if (callerFrag.isNotEmpty()) {
            if (callerFrag == "news_fragment") {
                fragmentInfoBinding.fabSaveDelete.setImageResource(R.drawable.ic_sd_storage)
                fragmentInfoBinding.fabSaveDelete.setOnClickListener {
                    viewModel.saveNewsArticle(article)
                    Snackbar.make(fragmentInfoBinding.root,"News article is saved successfully.",Snackbar.LENGTH_LONG).show()
                }

            } else if (args.callerFragment == "saved_fragment") {
                fragmentInfoBinding.fabSaveDelete.setImageResource(R.drawable.ic_delete)
                fragmentInfoBinding.fabSaveDelete.setOnClickListener {
                    viewModel.deleteNewsArticle(article)
                    Snackbar.make(fragmentInfoBinding.root,"News article is deleted successfully.",Snackbar.LENGTH_LONG).show()
                    findNavController().navigate(
                        R.id.action_infoFragment_to_savedFragment
                    )
                }
            }
        }

    }


}