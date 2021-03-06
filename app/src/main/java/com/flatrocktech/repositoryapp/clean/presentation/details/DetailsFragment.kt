package com.flatrocktech.repositoryapp.clean.presentation.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.flatrocktech.repositoryapp.R
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.model.RepoDetailsEntity
import com.flatrocktech.repositoryapp.databinding.FragmentDetailsBinding
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.displayToast
import com.flatrocktech.repositoryapp.util.helper.BrowserHelper
import com.flatrocktech.repositoryapp.util.helper.DateHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val detailsAdapter by lazy { DetailsAdapter() }

    private lateinit var menuStateManager: DetailsMenuStateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_top_menu, menu)
        menuStateManager = DetailsMenuStateManager(requireContext(), menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
        } else {
            when (menuStateManager.starState) {
                StarState.STARRED -> {
                    viewModel.unstarRepo(
                        repoName = args.repoName,
                        owner = args.owner
                    )
                }
                StarState.NOT_STARRED -> {
                    viewModel.starRepo(
                        RepoBriefEntity(
                            owner = args.owner,
                            repoName = args.repoName,
                            avatarUrl = args.avatarUrl
                        )
                    )
                }
            }
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = detailsAdapter

        viewModel.checkIsRepoStarred(
            repoName = args.repoName,
            owner = args.owner
        )

        viewModel.fetchRepoDetails(
            repoName = args.repoName,
            owner = args.owner
        )

        observeRepoDetails()
        observeIsStarredResult()
        observeStarResult()
        observeUnstarResult()
    }

    private fun observeRepoDetails() {
        viewModel.repoDetailsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    detailsAdapter.submitList(it.data.toDetailsRowModelList())
                    updateDetailHeaders(it.data.url)
                }
                is Result.Error -> {
                    displayToast(getString(R.string.error_fetched_details))
                }
            }
        })
    }

    private fun updateDetailHeaders(url: String?) {
        with(binding) {
            textRepo.text = args.repoName
            textOwner.text = getString(R.string.title_details_owner, args.owner)
            if (url != null) {
                buttonShare.isEnabled = true
                buttonShare.setOnClickListener {
                    BrowserHelper.openUrl(requireContext(), url)
                }
            }
        }
    }

    private fun observeIsStarredResult() {
        viewModel.isRepoStarred.observe(viewLifecycleOwner, { isStarred ->
            when (isStarred) {
                is Result.Success -> {
                    if (isStarred.data) {
                        menuStateManager.starState = StarState.STARRED
                    } else {
                        menuStateManager.starState = StarState.NOT_STARRED
                    }
                }
                is Result.Error -> {
                    displayToast(getString(R.string.error_cached_repo_check_star))
                    menuStateManager.disableStar()
                }
            }
        })
    }

    private fun observeStarResult() {
        viewModel.starRepo.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    menuStateManager.starState = StarState.STARRED
                }
                is Result.Error -> {
                    displayToast(getString(R.string.error_cached_repo_action_star))
                    menuStateManager.disableStar()
                }
            }
        })
    }

    private fun observeUnstarResult() {
        viewModel.unstarRepo.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    menuStateManager.starState = StarState.NOT_STARRED
                }
                is Result.Error -> {
                    displayToast(getString(R.string.error_cached_repo_action_unstar))
                    menuStateManager.disableStar()
                }
            }
        })
    }

    private fun RepoDetailsEntity.toDetailsRowModelList(): MutableList<DetailsRowModel> {
        return mutableListOf(
            DetailsRowModel(R.string.title_details_description, description),
            DetailsRowModel(R.string.title_details_created_at, DateHelper.formatDate(createdAt)),
            DetailsRowModel(R.string.title_details_language_used, languageUsed),
            DetailsRowModel(
                R.string.title_details_subscriber_count,
                (subscriberCount ?: 0).toString()
            )
        )
    }
}