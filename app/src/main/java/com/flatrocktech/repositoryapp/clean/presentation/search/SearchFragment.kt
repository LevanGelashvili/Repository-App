package com.flatrocktech.repositoryapp.clean.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flatrocktech.repositoryapp.R
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.databinding.FragmentSearchBinding
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.displayToast
import com.flatrocktech.repositoryapp.util.recycler.CustomItemDecoration
import com.flatrocktech.repositoryapp.util.recycler.EndlessScrollListener
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

        binding.buttonSearch.setOnClickListener {
            if (getFilterText().isNotBlank()) {
                clearPrevious = true
                getRepos()
            }
        }

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

        viewModel.briefRepos.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    searchAdapter.addRepoItems(it.data, clearPrevious)
                    if (it.data.isEmpty() && clearPrevious) {
                        displayToast(
                            getString(
                                R.string.empty_fetched_repo_list,
                                getFilterText()
                            )
                        )
                    }
                }
                is Result.Error -> {
                    searchAdapter.addRepoItems(listOf(), clearPrevious = true)
                    displayToast(
                        getString(
                            R.string.error_fetched_repo_list,
                            getFilterText()
                        )
                    )
                }
            }
        })
    }

    private fun navigateToDetails(repoBrief: RepoBriefEntity) {
        findNavController().navigate(
            SearchFragmentDirections.actionToDetails(
                repoBrief.owner,
                repoBrief.repoName,
                repoBrief.avatarUrl
            )
        )
    }

    private fun getRepos() {
        viewModel.requestRepositories(
            filter = getFilterText(),
            loadMore = !clearPrevious
        )
    }

    private fun getFilterText(): String {
        return binding.editText.text.toString()
    }
}