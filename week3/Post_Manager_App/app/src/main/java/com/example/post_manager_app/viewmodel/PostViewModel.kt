package com.example.post_manager_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.post_manager_app.data.Post
import com.example.post_manager_app.repository.IPostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: IPostRepository) : ViewModel() {

    // LiveData for the list of posts
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    // LiveData for error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // LiveData for loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Function to load posts, triggered by the UI
    fun loadPosts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Call the repository on a background thread (Dispatchers.IO is implicit in Retrofit suspend fun)
                val postsList = repository.getPosts()
                _posts.value = postsList
                _errorMessage.value = "" // Clear any previous errors
            } catch (e: Exception) {
                // Update error message if the call fails
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                // Loading is complete regardless of success or failure
                _isLoading.value = false
            }
        }
    }
}