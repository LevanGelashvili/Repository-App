package com.flatrocktech.repositoryapp.util.recycler

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

object DefaultItemDiffCallback : DiffUtil.ItemCallback<Any>() {

    @Suppress("UNCHECKED_CAST")
    operator fun <T> invoke(): DiffUtil.ItemCallback<T> =
        this as DiffUtil.ItemCallback<T>

    override fun areItemsTheSame(
        oldItem: Any,
        newItem: Any
    ) = oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Any,
        newItem: Any
    ) = oldItem == newItem
}