package com.example.post_manager_app.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.post_manager_app.R
import com.example.post_manager_app.databinding.ActivityMainBinding
import com.example.post_manager_app.network.JsonPlaceholderApi
import com.example.post_manager_app.repository.FakePostRepository
import com.example.post_manager_app.repository.IPostRepository
import com.example.post_manager_app.repository.PostRepository
import com.example.post_manager_app.viewmodel.PostViewModel
import com.example.post_manager_app.viewmodel.ViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        observeViewModel()
        viewModel.loadPosts() // Initiate the API call
    }

    private fun setupRecyclerView() {
        adapter = PostAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        // 1. Create the Retrofit API interface
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(JsonPlaceholderApi::class.java)

        // 2. Create the Repository, passing the API
        //val repository: IPostRepository = PostRepository(api)
        val repository: IPostRepository = FakePostRepository()

        // 3. Create the ViewModel using a Factory, passing the Repository
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
    }

    private fun observeViewModel() {
        // Observe posts and update the list when data changes
        viewModel.posts.observe(this) { posts ->
            adapter.setData(posts)
        }
        // Observe errors and show a message if needed
        viewModel.errorMessage.observe(this) { error ->
            binding.errorTextView.text = error
            binding.errorTextView.visibility = if (error.isNotEmpty()) View.VISIBLE else View.GONE
        }
        // Observe loading state and show/hide the progress bar
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}