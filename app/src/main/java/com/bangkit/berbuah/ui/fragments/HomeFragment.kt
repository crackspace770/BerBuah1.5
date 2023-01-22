package com.bangkit.berbuah.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.bangkit.berbuah.ui.activities.ScanActivity
import com.bangkit.berbuah.databinding.FragmentHomeBinding
import com.bangkit.berbuah.ui.activities.DetectActivity
import com.bangkit.berbuah.ui.activities.DetectionActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()




        binding.apply {
            btnToDetect.setOnClickListener {detect()}
            //btnDetect.setOnClickListener{detect()}
        }



        }

    private fun goDetect(){
        val intent = Intent(context, DetectActivity::class.java)
        startActivity(intent)
    }

    private fun detect(){
        val intent = Intent(context, DetectionActivity::class.java)
        startActivity(intent)
    }

    }



