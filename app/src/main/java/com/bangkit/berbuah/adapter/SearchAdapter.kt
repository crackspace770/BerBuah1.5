package com.bangkit.berbuah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.berbuah.databinding.SearchItemListBinding
import com.bangkit.berbuah.interfaces.ItemClickCallback
import com.bangkit.berbuah.model.FruitItem
import com.bangkit.berbuah.utils.Constant
import com.bumptech.glide.Glide
import com.bangkit.berbuah.utils.MyDiffUtil

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: ItemClickCallback
    var fruitList = ArrayList<FruitItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(fruitList[position])
    }

    override fun getItemCount(): Int = fruitList.size

    fun setOnItemClickCallback(onItemClickCallback: ItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<FruitItem>) {
        val diffUtils = MyDiffUtil(fruitList, items)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        fruitList.clear()
        fruitList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: SearchItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FruitItem) {
            binding.apply {
                binding.apply {
                    Glide.with(itemView.context)
                        .load("${Constant.IMAGE_BASE_URL}${data.gambar}")
                        .centerCrop()
                        .into(imgPhoto)
                    tvName.setText(data.nama)
                    tvDescription.setText(data.deskripsi)

                    itemView.setOnClickListener {
                        onItemClickCallback.onItemClicked(data)
                    }
                }

            }
        }
    }

}