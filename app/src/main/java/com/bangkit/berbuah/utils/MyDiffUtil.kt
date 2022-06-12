package com.bangkit.berbuah.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.berbuah.model.FruitItem

class MyDiffUtil(
    private val oldList: List<FruitItem>,
    private val newList: List<FruitItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].nama == newList[newItemPosition].nama

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}