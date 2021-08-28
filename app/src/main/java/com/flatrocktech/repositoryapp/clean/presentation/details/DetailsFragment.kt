package com.flatrocktech.repositoryapp.clean.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.flatrocktech.repositoryapp.clean.domain.usecase.GetRepoDetailsParams
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.requestRepoDetails(
            params = GetRepoDetailsParams(
                owner = args.owner,
                repoName = args.repoName
            )
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
    }
}