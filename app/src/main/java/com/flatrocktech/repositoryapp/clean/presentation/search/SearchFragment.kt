package com.flatrocktech.repositoryapp.clean.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.databinding.FragmentSearchBinding
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ui.recycler.CustomItemDecoration
import com.flatrocktech.repositoryapp.util.ui.recycler.EndlessScrollListener
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

    private var clearPrevious = false

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

        viewModel.reposLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    searchAdapter.addRepoItems(it.data, clearPrevious)
                }
                is Result.Error -> {
                }
                Result.Loading -> {
                }
            }
        })
    }

    //TODO: Fix !!s and create base class or smth
    private fun navigateToDetails(repoBrief: RepoBriefEntity) {
        findNavController().navigate(
            SearchFragmentDirections.actionToDetails(
                repoBrief.owner!!,
                repoBrief.repoName!!,
                repoBrief.avatarUrl!!
            )
        )
    }

    private fun setupSearch() {
        binding.buttonSearch.setOnClickListener {
            clearPrevious = true
            getRepos()
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = searchAdapter
            addItemDecoration(CustomItemDecoration(requireContext()))

            addOnScrollListener(object : EndlessScrollListener() {
                override fun onLoadMore() {
                    clearPrevious = false
                    getRepos()
                }
            })
        }
    }

    private fun getRepos() {
        viewModel.requestRepositories(
            filter = binding.editText.text.toString(),
            loadMore = !clearPrevious
        )
    }
}