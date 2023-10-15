package com.apex.codeassesment.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.UserListItemViewBinding
import com.bumptech.glide.Glide


class UserListAdapter(
    private val context: Context,
    private val userList: MutableList<User>? = null,
    private val userClicked: (User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = UserListItemViewBinding.bind(view)

        fun bind(user: User) {
            binding.mainEmail.text = user.email
            binding.mainName.text = "${user.name?.first ?: ""} ${user.name?.last ?: ""}".trim()
        }
    }

    fun updateList(newList: MutableList<User>) {
        userList?.clear()
        userList?.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_list_item_view,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return if (userList.isNullOrEmpty()) return 0
        else return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        userList?.get(position)?.let { user ->
            holder.bind(
                user
            )

            holder.binding.parent.setOnClickListener {
                userClicked.invoke(user)
            }
            Glide
                .with(context)
                .load("${user.picture?.medium}")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.binding.mainImage)
        }
    }

}