package com.bangkit.berbuah.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.berbuah.R
import com.bangkit.berbuah.adapter.SearchAdapter
import com.bangkit.berbuah.databinding.FragmentSearchBinding
import com.bangkit.berbuah.databinding.FragmentSettingBinding
import com.bangkit.berbuah.interfaces.ItemClickCallback
import com.bangkit.berbuah.model.FruitItem
import com.bangkit.berbuah.ui.activities.DetailActivity
import com.bangkit.berbuah.ui.activities.LoginActivity
import com.bangkit.berbuah.viewmodel.SearchViewModel
import com.bangkit.berbuah.viewmodel.ViewModelFactory

class SettingFragment : Fragment() {

    private var binding: FragmentSettingBinding? = null
//    private lateinit var searchAdapter: SearchAdapter
//    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val mainViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
//        preferences = getSharedPreferences(LoginActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)

        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        searchFruitData()
//
//        viewModel = obtainViewModel(context as AppCompatActivity)
//
//        viewModel.isLoading.observe(viewLifecycleOwner) {
//            showLoading(it)
//        }
//
//        getDataFruit()

        onAction()
    }

    private fun onAction() {
        binding?.btnSignout?.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            showLoading(true)
            startActivity(intent)
            onDestroy()

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): SearchViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SearchViewModel::class.java]

    }


    private fun showLoading(isLoading: Boolean) {
        binding?.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}