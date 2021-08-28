package com.flatrocktech.repositoryapp.clean.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flatrocktech.repositoryapp.clean.presentation.model.DetailsArgs
import com.flatrocktech.repositoryapp.databinding.FragmentSearchBinding
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ui.recycler.EndlessScrollListener
import com.flatrocktech.repositoryapp.util.ui.viewmodel.RequestCodes.RC_LOAD_MORE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter by lazy {
        SearchAdapter().apply {
            onRepoItemClicked = {
                navigateToDetails(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearch()
        setupRecyclerView()
        setupSwipeRefreshView()

        viewModel.reposLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    searchAdapter.addRepoItems(
                        list = it.data,
                        clearPrevious = viewModel.lastRequestCode != RC_LOAD_MORE
                    )
                }
                is Result.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is Result.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
            }
        })
    }

    private fun navigateToDetails(params: DetailsArgs) {
        findNavController().navigate(
            SearchFragmentDirections.actionToDetails(
                params.owner,
                params.repo
            )
        )
    }

    private fun setupSearch() {
        binding.buttonSearch.setOnClickListener {
            viewModel.onSearch(getFilterText())
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = searchAdapter
            addOnScrollListener(object : EndlessScrollListener() {
                override fun onLoadMore() {
                    viewModel.onLoadMore(
                        filter = getFilterText()
                    )
                }
            })
        }
    }

    private fun setupSwipeRefreshView() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh(getFilterText())
        }
    }

    private fun getFilterText(): String {
        return binding.editText.text.toString()
    }
}