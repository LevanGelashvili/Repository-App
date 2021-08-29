package com.flatrocktech.repositoryapp.util.ui.recycler

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface OnLoadMoreListener {
    fun onLoadMore()
}

/*
    Taken from: https://johncodeos.com/how-to-add-load-more-infinite-scrolling-in-android-using-kotlin/
 */
class RecyclerViewLoadMoreScroll(
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    fun setLoaded() {
        isLoading = false
    }

    fun getLoaded(): Boolean {
        return isLoading
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return

        totalItemCount = layoutManager.itemCount
        lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            mOnLoadMoreListener.onLoadMore()
            isLoading = true
        }
    }
}