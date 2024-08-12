package com.jer.suitmediainterntestapp.ui.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.jer.suitmediainterntestapp.R
import com.jer.suitmediainterntestapp.databinding.FragmentSecondScreenBinding


class SecondScreenFragment : Fragment() {

    private val binding by lazy { FragmentSecondScreenBinding.inflate(layoutInflater) }
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



        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()

        }

        binding.tvUsername.text = sharedPreferences.getString("name", "")
        binding.tvSelected.text = sharedPreferences.getString("fullname", "")

        binding.btnChoose.setOnClickListener {
            view.findNavController().navigate(R.id.action_secondScreenFragment_to_thirdScreenFragment2)
        }



    }
}