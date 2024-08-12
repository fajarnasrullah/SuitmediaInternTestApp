package com.jer.suitmediainterntestapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.jer.suitmediainterntestapp.data.remote.response.Data
import com.jer.suitmediainterntestapp.databinding.ItemUserBinding
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val context: Context): ListAdapter<Data, UserAdapter.UserViewHolder>(
    DIFF_CALLBACK) {

    inner class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Data) {
            binding.firstName.text = user.firstName
            binding.lastName.text = user.lastName
            binding.email.text = user.email
            val image: CircleImageView = binding.ivList

            Glide.with(itemView.context)
                .load(user.avatar)
                .into(image)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

        }
    }

}