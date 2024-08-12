 package com.jer.suitmediainterntestapp.ui.ui

import android.content.Context
import android.content.SharedPreferences
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
import androidx.recyclerview.widget.RecyclerView
import com.jer.suitmediainterntestapp.R

import com.jer.suitmediainterntestapp.data.remote.response.Data
import com.jer.suitmediainterntestapp.data.remote.retrofit.ApiService
import com.jer.suitmediainterntestapp.databinding.FragmentThirdScreenBinding
import com.jer.suitmediainterntestapp.ui.adapter.UserAdapter

import com.jer.suitmediainterntestapp.ui.viewmodel.UserViewModel


 class ThirdScreenFragment : Fragment() {

    private val binding by lazy { FragmentThirdScreenBinding.inflate(layoutInflater) }
//     private lateinit var viewModel: UserViewModel
     private lateinit var adapter: UserAdapter

     private lateinit var sharedPreferences: SharedPreferences

     private val per_page = 13



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

         sharedPreferences = requireActivity().getSharedPreferences("GetPrefs", Context.MODE_PRIVATE)

         viewModel.error.observe(viewLifecycleOwner) { error ->
             showToast(error)
         }

         binding.btnBack.setOnClickListener {
             view.findNavController().popBackStack()

         }

         val layoutManager = LinearLayoutManager(requireContext())
         binding.rvUsers.layoutManager = layoutManager

         adapter = UserAdapter(requireContext()) { user ->
             saveSelectedUserName("${user.firstName} ${user.lastName}")
             view.findNavController().navigate(R.id.action_thirdScreenFragment2_to_secondScreenFragment)
         }

         binding.rvUsers.adapter = adapter

         viewModel.listUser.observe(viewLifecycleOwner) {user ->

             adapter.submitList(user)
             updateEmptyState(user.isEmpty())
         }



         binding.swipeRefreshLayout.setOnRefreshListener {
             viewModel.refreshUsers(per_page)
             binding.swipeRefreshLayout.isRefreshing = false
         }


         viewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
             showLoading(isLoading)
             binding.rvUsers.setOnScrollListener(object : RecyclerView.OnScrollListener() {
                 override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                     super.onScrolled(recyclerView, dx, dy)
                     val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                     val totalItemCount = layoutManager.itemCount
                     val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                     if (!isLoading && !viewModel.isLastPage && lastVisibleItem >= totalItemCount - 1) {
                         viewModel.loadMoreUsers(per_page)
                     }
                 }
             })
         }

         viewModel.error.observe(viewLifecycleOwner) {
             showToast(it)
         }


         viewModel.getAllUsers(1, per_page)


     }



     fun showToast(message: String) {
         Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
     }

     private fun updateEmptyState(isEmpty: Boolean) {
         if (isEmpty) {
             binding.tvDataKosong.visibility = View.VISIBLE
         } else {
             binding.tvDataKosong.visibility = View.GONE
         }
     }

     private fun saveSelectedUserName(name: String) {
         val editor = sharedPreferences.edit()
         editor.putString("fullname", name)
         editor.apply()
     }

     private fun showLoading(isLoading: Boolean) {
         if (isLoading) {
             binding.progressBar.visibility = View.VISIBLE
         } else {
             binding.progressBar.visibility = View.GONE
         }
     }

}