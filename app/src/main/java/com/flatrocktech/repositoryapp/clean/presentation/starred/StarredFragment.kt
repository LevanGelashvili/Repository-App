package com.flatrocktech.repositoryapp.clean.presentation.starred

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flatrocktech.repositoryapp.R
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.databinding.FragmentStarredBinding
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.displayToast
import com.flatrocktech.repositoryapp.util.recycler.CustomItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarredFragment : Fragment() {

    private val viewModel: StarredViewModel by viewModels()

    private var _binding: FragmentStarredBinding? = null
    private val binding get() = _binding!!

    private val starredAdapter by lazy {
        StarredAdapter().apply {
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
        _binding = FragmentStarredBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            adapter = starredAdapter
            addItemDecoration(CustomItemDecoration(requireContext()))
        }

        viewModel.requestRepositories()

        viewModel.briefRepos.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    starredAdapter.submitList(it.data)
                }
                is Result.Error -> {
                    displayToast(getString(R.string.error_cached_repo_list))
                }
            }
        })
    }


    private fun navigateToDetails(repoBrief: RepoBriefEntity) {
        findNavController().navigate(
            StarredFragmentDirections.actionToDetails(
                repoBrief.owner,
                repoBrief.repoName,
                repoBrief.avatarUrl
            )
        )
    }
}