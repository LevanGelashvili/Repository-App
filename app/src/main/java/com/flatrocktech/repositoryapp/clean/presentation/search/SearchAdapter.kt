package com.flatrocktech.repositoryapp.clean.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.flatrocktech.repositoryapp.clean.domain.model.entity.RepoEntity
import com.flatrocktech.repositoryapp.clean.presentation.search.SearchViewModel.Companion.TAKE_N
import com.flatrocktech.repositoryapp.databinding.ItemLoadingBinding
import com.flatrocktech.repositoryapp.databinding.ItemRepoBinding
import com.flatrocktech.repositoryapp.util.ui.recycler.DefaultItemDiffCallback
import com.flatrocktech.repositoryapp.util.ui.recycler.EndlessScrollListener

class SearchAdapter :
    ListAdapter<SearchAdapter.SearchListItem, SearchAdapter.SearchItemViewHolder>(
        DefaultItemDiffCallback()
    ), EndlessScrollListener.HasMoreCallback {

    private val repoItems = mutableListOf<SearchListItem.RepoItem>()

    fun addRepoItems(
        list: List<RepoEntity>,
        clearPrevious: Boolean = false
    ) {
        val listToDisplay = mutableListOf<SearchListItem>()
        if (clearPrevious) {
            repoItems.clear()
        }

        repoItems.addAll(list.map { SearchListItem.RepoItem(it) })
        listToDisplay.addAll(repoItems)

        if (list.size == TAKE_N) {
            listToDisplay.add(SearchListItem.Loader)
        }
        submitList(listToDisplay)
    }

    override fun hasMore() = true

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchListItem.RepoItem -> VIEW_TYPE_REPO_ITEM
            SearchListItem.Loader -> VIEW_TYPE_LOADER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return when (viewType) {
            VIEW_TYPE_REPO_ITEM -> SearchItemViewHolder(
                ItemRepoBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            VIEW_TYPE_LOADER -> SearchItemViewHolder(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> {
                throw IllegalStateException("No such view type")
            }
        }
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is SearchListItem.RepoItem -> holder.bindRepo(item.repo)
            SearchListItem.Loader -> holder.bindLoader()
        }
    }

    inner class SearchItemViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindRepo(repo: RepoEntity) {
            with(binding as ItemRepoBinding) {
                textRepo.text = repo.title
            }
        }

        fun bindLoader() {
            // Do nothing
        }
    }

    sealed class SearchListItem {
        data class RepoItem(val repo: RepoEntity) : SearchListItem()
        object Loader : SearchListItem()
    }

    companion object {
        private const val VIEW_TYPE_REPO_ITEM = 0
        private const val VIEW_TYPE_LOADER = 1
    }
}