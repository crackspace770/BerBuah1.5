package com.bangkit.berbuah.interfaces

import com.bangkit.berbuah.model.FruitItem

interface ItemClickCallback {
    fun onItemClicked(fruitItem: FruitItem)
}