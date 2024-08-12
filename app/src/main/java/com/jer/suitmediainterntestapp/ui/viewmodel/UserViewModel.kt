package com.jer.suitmediainterntestapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson

import com.jer.suitmediainterntestapp.data.remote.response.Data
import com.jer.suitmediainterntestapp.data.remote.response.ErrorResponse
import com.jer.suitmediainterntestapp.data.remote.response.UsersResponse
import com.jer.suitmediainterntestapp.data.remote.retrofit.ApiConfig
import com.jer.suitmediainterntestapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserViewModel(): ViewModel() {

    private val _listUsers = MutableLiveData<List<Data>>()
    val listUser: LiveData<List<Data>> = _listUsers

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var currentPage = 1
    var isLoading = false
    var isLastPage = false


    fun getAllUsers(page: Int, per_page: Int) {

//        if (isLoading || isLastPage) return

        isLoading = true
        ApiConfig.getApiService(ApiService::class.java).getUser(page, per_page).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(p0: Call<UsersResponse>, p1: Response<UsersResponse>) {
                isLoading = false
                if (p1.isSuccessful) {
                    val newUsers = p1.body()?.data ?: emptyList()
                    _listUsers.value = newUsers
                    isLastPage = newUsers.isEmpty()
//                        val currentUsers = _listUsers.value ?: emptyList()
//                        _listUsers.value = currentUsers + newUsers
//                        isLastPage = newUsers.size < per_page
//                        currentPage++



                } else {
                    Log.e("UsersViewModel", "Failure: ${p1.message()}")
                }

                isLoading = false
            }

            override fun onFailure(p0: Call<UsersResponse>, p1: Throwable) {
                Log.e("UsersViewModel", "Failure: ${p1.message}")
                _error.value = "Failure ${p1.message}"
                isLoading = false
            }

        })

    }

    fun refreshUsers(per_page: Int) {
        currentPage = 1
        getAllUsers(currentPage, per_page)
    }

    fun loadMoreUsers(per_page: Int) {
        if (!isLoading && !isLastPage) {
            currentPage++
            getAllUsers(currentPage, per_page)
        }
    }





}