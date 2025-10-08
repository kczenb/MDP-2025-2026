package com.example.post_manager_app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.post_manager_app.data.Post
import com.example.post_manager_app.databinding.ItemPostBinding

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var postList = emptyList<Post>()

    inner class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            // Assign the post object to the layout variable
            binding.post = post
            // This forces the data binding to execute immediately
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate using the generated ItemPostBinding class
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = postList.size

    fun setData(posts: List<Post>) {
        this.postList = posts
        notifyDataSetChanged()
    }
}