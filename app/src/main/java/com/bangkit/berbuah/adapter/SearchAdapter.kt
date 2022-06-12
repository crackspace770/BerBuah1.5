package com.bangkit.berbuah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.berbuah.databinding.SearchItemListBinding
import com.bangkit.berbuah.interfaces.ItemClickCallback
import com.bangkit.berbuah.model.FruitItem
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
                        .load(data.gambar)
                        .centerCrop()
                        .into(imgPhoto)
                    tvName.setText(data.nama)
                    tvDescription.setText(data.deskripsi)


//                    val listListStoryItemDetail =
//                        FruitItem(data.nama, data.description, data.gizi, data.manfaat, data.photo)
//                    Log.d("story:", listListStoryItemDetail.toString())

                    itemView.setOnClickListener {
                        onItemClickCallback.onItemClicked(data)
//                        val intent = Intent(itemView.context, DetailActivity::class.java)
//                        intent.putExtra(DetailActivity.EXTRA_DATA_FRUIT, data)

//                        val optionsCompat: ActivityOptionsCompat =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                itemView.context as Activity,
//                                Pair(imgPhoto, "imageListStoryItem"),
//                                Pair(tvName, "nameListStoryItem"),
//
//                                )
//                        itemView.context.startActivity(intent)
                    }
                }

            }
        }
    }

}