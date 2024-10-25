package com.dicoding.asclepius.view.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.FragmentNewsBinding
import com.dicoding.asclepius.utils.ViewModelFactory
import com.dicoding.asclepius.view.ClasificationsViewModel
import com.dicoding.asclepius.view.adapter.NewsAdapter
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel by viewModels<ClasificationsViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvNews.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvNews.addItemDecoration(dividerItemDecoration)

        lifecycleScope.launch {
            newsViewModel.getNews()

            newsViewModel.news.observe(viewLifecycleOwner) { news ->
                val newsAdapter = NewsAdapter()
                newsAdapter.submitList(news)
                binding.rvNews.adapter = newsAdapter
                Log.d("NewsFragment", "onViewCreated: $news")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}