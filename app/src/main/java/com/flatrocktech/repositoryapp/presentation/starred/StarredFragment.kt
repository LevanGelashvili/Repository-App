package com.flatrocktech.repositoryapp.presentation.starred

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flatrocktech.repositoryapp.databinding.FragmentStarredBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarredFragment : Fragment() {

    private var _binding: FragmentStarredBinding? = null
    private val binding get() = _binding!!

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
}