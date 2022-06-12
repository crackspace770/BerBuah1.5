package com.bangkit.berbuah.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.berbuah.adapter.FavoriteAdapter
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.databinding.FragmentFavoriteBinding
import com.bangkit.berbuah.interfaces.ItemClickCallback
import com.bangkit.berbuah.model.FruitItem
import com.bangkit.berbuah.ui.activities.DetailActivity
import com.bangkit.berbuah.viewmodel.FavoriteViewModel
import com.bangkit.berbuah.viewmodel.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.rvFavorite.layoutManager = LinearLayoutManager(context)
        val root: View = binding.root
        return root
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(context as AppCompatActivity)

        getDataUserFavorite()
    }

    private fun getDataUserFavorite() {
        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(context)
            adapter = FavoriteAdapter()
            rvFavorite.adapter = adapter

            viewModel.getAllFavoriteUser().observe(viewLifecycleOwner) { listUser ->
                listUser.let {
                    if (it.isEmpty()) {
                        emptyState()
                    } else {
                        val list = mapListFavorite(listUser)
                        adapter.setData(list)
                    }
                }
            }
            adapter.setOnItemClickCallback(object : ItemClickCallback {
                override fun onItemClicked(fruitItem: FruitItem) {
                    showClickedItemFavoriteUser(fruitItem)
                }
            })
        }
    }

    private fun mapListFavorite(listFruitFavorite: List<Favorite>?): ArrayList<FruitItem> {
        val listFruitData = ArrayList<FruitItem>()
        if (listFruitFavorite != null) {
            for (fruit in listFruitFavorite) {
                val fruitFavorite = FruitItem(fruit.id, fruit.name, fruit.gambar)
                listFruitData.add(fruitFavorite)
            }
        }
        return listFruitData
    }

    private fun showClickedItemFavoriteUser(data: FruitItem) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA_FRUIT, data)
        startActivity(intent)
    }

    private fun emptyState() {
        binding.apply {
            layoutEmptyData.let {
                it.root.visibility = View.VISIBLE
            }
            rvFavorite.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}