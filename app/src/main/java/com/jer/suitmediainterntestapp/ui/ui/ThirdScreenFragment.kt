 package com.jer.suitmediainterntestapp.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jer.suitmediainterntestapp.R

import com.jer.suitmediainterntestapp.data.remote.response.Data
import com.jer.suitmediainterntestapp.data.remote.retrofit.ApiService
import com.jer.suitmediainterntestapp.databinding.FragmentThirdScreenBinding
import com.jer.suitmediainterntestapp.ui.adapter.UserAdapter

import com.jer.suitmediainterntestapp.ui.viewmodel.UserViewModel


 class ThirdScreenFragment : Fragment() {

    private val binding by lazy { FragmentThirdScreenBinding.inflate(layoutInflater) }
//     private lateinit var viewModel: UserViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_third_screen, container, false)
        return binding.root
    }


     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
//         viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

         viewModel.error.observe(viewLifecycleOwner) { error ->
             showToast(error)
         }

         binding.btnBack.setOnClickListener {
             view.findNavController().popBackStack()

         }

         val layoutManager = LinearLayoutManager(requireContext())
         binding.rvUsers.layoutManager = layoutManager

         viewModel.listUser.observe(viewLifecycleOwner) {user ->
             setListData(user)
         }

//         ini contoh coba dulu:
         val page = 1
         val per_page = 10

         viewModel.getAllUsers(page, per_page)


     }

     fun setListData(user: List<Data>) {
         val adapter = UserAdapter(requireContext())
         adapter.submitList(user)
         binding.rvUsers.adapter = adapter
     }

     fun showToast(message: String) {
         Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
     }

}