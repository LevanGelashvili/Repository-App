package com.flatrocktech.repositoryapp.util.ui.recycler

import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {

    interface HasMoreCallback {
        fun hasMore(): Boolean
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0 && !recyclerView.canScrollVertically(1) && hasMore(recyclerView)) {
            onLoadMore()
        }
    }

    protected open fun hasMore(recyclerView: RecyclerView): Boolean {
        return (recyclerView.adapter as? HasMoreCallback)?.hasMore() ?: false
    }

    protected abstract fun onLoadMore()
}