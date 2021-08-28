package com.flatrocktech.repositoryapp.clean.presentation.starred

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.presentation.search.SearchAdapter
import com.flatrocktech.repositoryapp.clean.presentation.search.SearchFragmentDirections
import com.flatrocktech.repositoryapp.databinding.FragmentStarredBinding
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.data
import com.flatrocktech.repositoryapp.util.ext.displayToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

        binding.recyclerView.adapter = starredAdapter

        viewModel.requestRepositories()

        viewModel.reposLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    starredAdapter.submitList(it.data)
                    Timber.d("Here %s", it.data.toString())
                }
                is Result.Error -> {
                    Timber.d("Here %s", it.exception.toString())
                }
                Result.Loading -> {
                }
            }
        })
    }

    //TODO: Fix !!s and create base class or smth
    private fun navigateToDetails(repoBrief: RepoBriefEntity) {
        findNavController().navigate(
            StarredFragmentDirections.actionToDetails(
                repoBrief.owner!!,
                repoBrief.repoName!!,
                repoBrief.avatarUrl!!
            )
        )
    }
}