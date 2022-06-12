package com.bangkit.berbuah.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.berbuah.databinding.FragmentProfilBinding
import com.bangkit.berbuah.ui.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilFragment : Fragment() {

    private var binding: FragmentProfilBinding? = null
    private lateinit var auth: FirebaseAuth
    private var databaseReference :  DatabaseReference? = null
    private var database: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadProfile()
    }

    private fun loadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        binding?.textViewEmail?.text = user?.email

        userreference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding?.textViewUsername?.text = snapshot.child("username").value.toString()
                binding?.textViewName?.text = snapshot.child("name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        binding?.btnSignout?.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            showLoading(true)
            startActivity(intent)
            onDestroy()
        }
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