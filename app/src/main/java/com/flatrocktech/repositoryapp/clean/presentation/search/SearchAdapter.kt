package com.flatrocktech.repositoryapp.clean.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.flatrocktech.repositoryapp.clean.domain.model.RepoBriefEntity
import com.flatrocktech.repositoryapp.clean.domain.usecase.remote.GetRepoBriefListUseCase.Companion.TAKE_N
import com.flatrocktech.repositoryapp.clean.presentation.base.RepoItemViewHolder
import com.flatrocktech.repositoryapp.databinding.ItemLoadingBinding
import com.flatrocktech.repositoryapp.databinding.ItemRepoBinding
import com.flatrocktech.repositoryapp.util.recycler.DefaultItemDiffCallback
import com.flatrocktech.repositoryapp.util.recycler.EndlessScrollListener

class SearchAdapter :
    ListAdapter<SearchAdapter.SearchListItem, RepoItemViewHolder>(
        DefaultItemDiffCallback()
    ), EndlessScrollListener.HasMoreCallback {

    private val repoItems = mutableListOf<RepoBriefEntity>()

    var onRepoItemClicked: ((RepoBriefEntity) -> Unit)? = null

    fun addRepoItems(
        responseList: List<RepoBriefEntity>,
        clearPrevious: Boolean = false
    ) {
        if (duplicateResponse(responseList)) {
            return
        }
        val listToDisplay = mutableListOf<SearchListItem>()
        if (clearPrevious) {
            repoItems.clear()
        }

        repoItems.addAll(responseList)
        listToDisplay.addAll(repoItems.map { SearchListItem.RepoItem(it) })

        if (responseList.size == TAKE_N) {
            listToDisplay.add(SearchListItem.Loader)
        }
        submitList(listToDisplay)
    }

    private fun duplicateResponse(list: List<RepoBriefEntity>): Boolean {
        if (list.isEmpty() || repoItems.isEmpty()) {
            return false
        }
        return repoItems.contains(list.first())
    }

    override fun hasMore() = true

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchListItem.RepoItem -> VIEW_TYPE_REPO_ITEM
            SearchListItem.Loader -> VIEW_TYPE_LOADER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        return when (viewType) {
            VIEW_TYPE_REPO_ITEM -> RepoItemViewHolder(
                ItemRepoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            VIEW_TYPE_LOADER -> RepoItemViewHolder(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> {
                throw IllegalStateException("No such view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is SearchListItem.RepoItem -> holder.bindRepo(item.repoBrief, onRepoItemClicked)
            SearchListItem.Loader -> {
            }
        }
    }

    sealed class SearchListItem {
        data class RepoItem(val repoBrief: RepoBriefEntity) : SearchListItem()
        object Loader : SearchListItem()
    }

    companion object {
        private const val VIEW_TYPE_REPO_ITEM = 0
        private const val VIEW_TYPE_LOADER = 1
    }
}