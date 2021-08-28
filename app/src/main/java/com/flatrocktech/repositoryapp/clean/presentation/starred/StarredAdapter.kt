package com.flatrocktech.repositoryapp.clean.presentation.starred

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.databinding.ItemRepoBinding
import com.flatrocktech.repositoryapp.util.ui.recycler.DefaultItemDiffCallback

class StarredAdapter :
    ListAdapter<RepoBriefEntity, StarredAdapter.StarredItemViewHolder>(
        DefaultItemDiffCallback()
    ) {

    var onRepoItemClicked: ((RepoBriefEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarredItemViewHolder {
        return StarredItemViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: StarredItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StarredItemViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: RepoBriefEntity) {
            binding.root.setOnClickListener {
                onRepoItemClicked?.invoke(repo)
            }
            with(binding) {
                textRepo.text = repo.repoName
                textOwner.text = repo.owner
                Glide.with(root)
                    .load(repo.avatarUrl)
                    .into(binding.imageAvatar)
            }
        }
    }
}