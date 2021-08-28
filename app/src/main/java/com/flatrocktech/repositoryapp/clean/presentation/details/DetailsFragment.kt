package com.flatrocktech.repositoryapp.clean.presentation.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.flatrocktech.repositoryapp.R
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.FetchRepoDetailsParams
import com.flatrocktech.repositoryapp.databinding.FragmentDetailsBinding
import com.flatrocktech.repositoryapp.util.Result
import com.flatrocktech.repositoryapp.util.ext.displayToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_star -> {
                viewModel.starRepo(
                    RepoBriefEntity(
                        owner = args.owner,
                        repoName = args.repoName,
                        avatarUrl = args.avatarUrl
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkIsRepoStarred(
            repoName = args.repoName
        )

        viewModel.fetchRepoDetails(
            repoName = args.repoName,
            owner = args.owner
        )

        viewModel.repoDetailsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    displayToast(it.data.toString())
                }
                is Result.Error -> {

                }
                Result.Loading -> {

                }
            }
        })

        viewModel.isRepoStarredLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    if (it.data) displayToast("Repo is starred!")
                    else displayToast("Repo is not starred!")
                }
                is Result.Error -> displayToast("Unable to check if repo is starred")
                Result.Loading -> {
                }
            }
        })

        viewModel.starRepoStatusLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> displayToast("Added to favorites!")
                is Result.Error -> displayToast("Unable to add to favorites")
                Result.Loading -> {
                }
            }
        })

        viewModel.unstarRepoStatusLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> displayToast("Removed from favorites!")
                is Result.Error -> displayToast("Unable to remove from favorites")
                Result.Loading -> {
                }
            }
        })
    }
}