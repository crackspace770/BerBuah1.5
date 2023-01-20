package com.bangkit.berbuah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.berbuah.databinding.ItemRowFruitBinding
import com.bangkit.berbuah.interfaces.ItemClickCallback
import com.bangkit.berbuah.model.FruitItem
import com.bangkit.berbuah.utils.Constant
import com.bangkit.berbuah.utils.MyDiffUtil
import com.bangkit.berbuah.utils.Utils.loadImageUrl

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: ItemClickCallback
    private val fruitFavorite = ArrayList<FruitItem>()

    fun setOnItemClickCallback(onItemClickCallback: ItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<FruitItem>) {
        val diffUtils = MyDiffUtil(fruitFavorite, items)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        fruitFavorite.clear()
        fruitFavorite.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private var binding: ItemRowFruitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fruitFavorite: FruitItem) {
            binding.apply {
                imgItemPhoto.loadImageUrl("${Constant.IMAGE_BASE_URL}${fruitFavorite.gambar.toString()}")
                tvName.text = fruitFavorite.nama
                tvDescription.text = fruitFavorite.deskripsi

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(fruitFavorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowFruitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(fruitFavorite[position])
    }

    override fun getItemCount(): Int = fruitFavorite.size
}