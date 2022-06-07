package com.bangkit.berbuah.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.berbuah.model.DataItem

class SearchDiffUtil(
    private val oldList: List<DataItem>,
    private val newList: List<DataItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}