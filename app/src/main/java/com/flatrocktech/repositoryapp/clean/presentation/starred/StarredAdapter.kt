package com.flatrocktech.repositoryapp.clean.presentation.starred

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.presentation.base.RepoItemViewHolder
import com.flatrocktech.repositoryapp.databinding.ItemRepoBinding
import com.flatrocktech.repositoryapp.util.recycler.DefaultItemDiffCallback

class StarredAdapter :
    ListAdapter<RepoBriefEntity, RepoItemViewHolder>(
        DefaultItemDiffCallback()
    ) {

    var onRepoItemClicked: ((RepoBriefEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        return RepoItemViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bindRepo(getItem(position), onRepoItemClicked)
    }
}