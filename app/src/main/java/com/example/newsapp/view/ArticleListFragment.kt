package com.example.newsapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleListBinding
import com.example.newsapp.model.adapters.ArticlesAdapter
import com.example.newsapp.model.api.NewsArticle
import com.example.newsapp.model.network.NetworkResult
import com.example.newsapp.viewmodel.ArticleViewModel
import com.example.newsapp.viewmodel.ViewModelFactory


class ArticleListFragment : Fragment(), ArticlesAdapter.OnItemClickListener {

    private lateinit var dataBinding: FragmentArticleListBinding
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var articles: MutableList<NewsArticle>
    private lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initViewModel()
        viewModel.getNewsArticles()
    }

    private fun initAdapter() {
        articles = mutableListOf()
        articlesAdapter = ArticlesAdapter(articles)
        articlesAdapter.setOnItemClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        viewModel =
            ViewModelProvider(requireActivity(), ViewModelFactory())[ArticleViewModel::class.java]
        viewModel.articles.observe(this) { articles ->
            when (articles) {
                is NetworkResult.Success -> {
                    dataBinding.pbLoading.visibility = View.INVISIBLE
                    this.articles.addAll(articles.data!!)
                    this.articles.sortBy {
                        it.publishedAt
                    }
                    this.articles.reverse()
                    articlesAdapter.notifyDataSetChanged()
                }

                is NetworkResult.Loading -> {
                    dataBinding.pbLoading.visibility = View.VISIBLE
                }

                is NetworkResult.Failure -> {
                    dataBinding.pbLoading.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, container, false)

        initRecyclerView()

        return dataBinding.root
    }

    private fun initRecyclerView() {
        dataBinding.rvArticles.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.rvArticles.adapter = articlesAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.action_menu, menu)
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.itemSort -> {
                        articles.reverse()
                        articlesAdapter.notifyDataSetChanged()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onClick(position: Int) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(articles[position].url))
        startActivity(browserIntent)
    }
}