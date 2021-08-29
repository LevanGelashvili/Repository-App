package com.flatrocktech.repositoryapp.clean.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.databinding.ItemRepoBinding

class RepoItemViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindRepo(repo: RepoBriefEntity, onRepoItemClicked: ((RepoBriefEntity) -> Unit)?) {
        binding.root.setOnClickListener {
            onRepoItemClicked?.invoke(repo)
        }
        with(binding as ItemRepoBinding) {
            textRepo.text = repo.repoName
            textOwner.text = repo.owner
            Glide.with(root)
                .load(repo.avatarUrl)
                .circleCrop()
                .into(binding.imageAvatar)
        }
    }
}