package com.bangkit.berbuah.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.berbuah.R
import com.bangkit.berbuah.adapter.SearchAdapter
import com.bangkit.berbuah.databinding.FragmentSearchBinding
import com.bangkit.berbuah.interfaces.ItemClickCallback
import com.bangkit.berbuah.model.FruitItem
import com.bangkit.berbuah.ui.activities.DetailActivity
import com.bangkit.berbuah.viewmodel.SearchViewModel
import com.bangkit.berbuah.viewmodel.ViewModelFactory


class SearchFragment : Fragment() {

    //    private val binding get() = _binding!!
    private var binding: FragmentSearchBinding? = null
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var viewModel: SearchViewModel
//    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val mainViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
//        preferences = getSharedPreferences(LoginActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchFruitData()

        viewModel = obtainViewModel(context as AppCompatActivity)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        getDataFruit()
    }

    private fun obtainViewModel(activity: AppCompatActivity): SearchViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SearchViewModel::class.java]

    }

    private fun searchFruitData() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAdapter.fruitList.clear()
                query.let {
                    binding?.searchView?.clearFocus() // hide keyboard after searching
                    finalState()
                    viewModel.setListFruit(it.toString())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun getDataFruit() {
        binding?.apply {
            rvResult.setHasFixedSize(true)
            rvResult.layoutManager = LinearLayoutManager(context)
            searchAdapter = SearchAdapter()
            rvResult.adapter = searchAdapter

            viewModel.getFruitSearch().observe(viewLifecycleOwner) { listUser ->
                listUser.let {
                    if (it.size == 0) {
                        emptyState()
                    } else {
                        searchAdapter.setData(it)
                        finalState()
                    }
                }
            }
            searchAdapter.setOnItemClickCallback(object : ItemClickCallback {
                override fun onItemClicked(fruitItem: FruitItem) {
                    showClickedFruitItem(fruitItem)
                }
            })
        }
    }

    private fun emptyState() {
        binding?.apply {
            rvResult.visibility = View.GONE
            layoutEmptyData.let {
                it.root.visibility = View.VISIBLE
            }
        }
    }

    private fun finalState() {
        binding?.apply {
            rvResult.visibility = View.VISIBLE
            layoutEmptyData.root.visibility = View.GONE
        }
    }

    private fun showClickedFruitItem(fruitItem: FruitItem) {
        val bundle = Bundle()
        bundle.putParcelable(DetailActivity.EXTRA_DATA_FRUIT, fruitItem)
        view?.findNavController()?.navigate(R.id.action_searchFragment_to_detailActivity, bundle)
    }

//    private fun setSearchData(searchResult: List<DataItem>) {
//        val listFruit: ArrayList<FruitItem> = ArrayList()
//        for (fruit in searchResult) {
//            val userList = FruitItem(fruit.nama, fruit.photo)
//            listFruit.add(userList)
//}
//        val adapter = SearchAdapter(listFruit)
//        binding.rvResult.adapter = adapter
//        binding.searchView.setText("")

//    }

    private fun showLoading(isLoading: Boolean) {
        binding?.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}