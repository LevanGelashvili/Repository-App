package com.flatrocktech.repositoryapp.clean.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flatrocktech.repositoryapp.databinding.ItemDetailsBinding
import com.flatrocktech.repositoryapp.util.Constants.CONTENT_NA
import com.flatrocktech.repositoryapp.util.ui.recycler.DefaultItemDiffCallback

data class DetailsRowModel(
    val titleId: Int,
    val value: String?
)

class DetailsAdapter :
    ListAdapter<DetailsRowModel, DetailsAdapter.DetailsAdapterItemViewHolder>(
        DefaultItemDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsAdapterItemViewHolder {
        return DetailsAdapterItemViewHolder(
            ItemDetailsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailsAdapterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DetailsAdapterItemViewHolder(private val binding: ItemDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rowModel: DetailsRowModel) {
            with(binding) {
                textTitle.text = root.context.getString(rowModel.titleId)
                textValue.text = rowModel.value ?: CONTENT_NA
            }
        }
    }
}