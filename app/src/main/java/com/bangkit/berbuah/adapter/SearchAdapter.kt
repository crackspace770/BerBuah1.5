package com.bangkit.berbuah.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.berbuah.databinding.SearchItemListBinding
import com.bangkit.berbuah.model.FruitItem
import com.bumptech.glide.Glide
import com.bangkit.berbuah.ui.activities.DetailActivity
import com.bangkit.berbuah.utils.SearchDiffUtil

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var fruitList = ArrayList<FruitItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(fruitList[position])
    }

    override fun getItemCount(): Int = fruitList.size

    fun setData(items: ArrayList<FruitItem>) {
        val diffUtils = SearchDiffUtil(fruitList, items)
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
                        .load(data.gambar)
                        .centerCrop()
                        .into(imgPhoto)
                    tvName.setText(data.nama)
                    tvDescription.setText(data.deskripsi)


//                    val listListStoryItemDetail =
//                        FruitItem(data.nama, data.description, data.gizi, data.manfaat, data.photo)
//                    Log.d("story:", listListStoryItemDetail.toString())

                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_STORY, data)

//                        val optionsCompat: ActivityOptionsCompat =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                itemView.context as Activity,
//                                Pair(imgPhoto, "imageListStoryItem"),
//                                Pair(tvName, "nameListStoryItem"),
//
//                                )
//                        itemView.context.startActivity(intent, optionsCompat.toBundle())
                    }
                }

            }
        }
    }

}