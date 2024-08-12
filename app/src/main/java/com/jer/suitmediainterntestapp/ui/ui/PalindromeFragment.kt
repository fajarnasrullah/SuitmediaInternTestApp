package com.jer.suitmediainterntestapp.ui.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.jer.suitmediainterntestapp.R
import com.jer.suitmediainterntestapp.databinding.FragmentPalindromeBinding


class PalindromeFragment : Fragment() {

    private val binding by lazy { FragmentPalindromeBinding.inflate(layoutInflater) }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("GetPrefs", Context.MODE_PRIVATE)


        binding.btnNext.setOnClickListener {
            val username = binding.edtUsername.text.toString()

            when {
                username.isEmpty() -> binding.edtUsername.error = "Field tidak boleh kosong!"
                else -> {
                    sharedPreferences.edit().putString("name", username).apply()
                    view.findNavController().navigate(R.id.action_palindromeFragment_to_secondScreenFragment)
                }

            }
        }

        binding.btnCheck.setOnClickListener {
            val palindrome = binding.edtPalindrome.text.toString()

            when {
                palindrome.isEmpty() -> binding.edtPalindrome.error = "Field tidak boleh kosong!"
                else -> {
                    val palindromeWords = palindrome.replace(" ", "")
                    var startWord = 0
                    var endWord = palindromeWords.length - 1
                    var isPalindrome = true

                    while (startWord < endWord) {
                        if (palindromeWords[startWord] != palindromeWords[endWord]) {
                            isPalindrome = false
                            break
                        }
                        startWord++
                        endWord--
                    }

                    if (isPalindrome) {
                        showToast("isPalindrome")
                    } else {
                        showToast("not palindrome")
                    }
                }
            }
        }

    }



    fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }




}