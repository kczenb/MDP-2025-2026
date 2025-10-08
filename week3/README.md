# Model View Viewmodel Lab Session

In this session we will focus on a implementing a simple application that follows the MVVM architecture. This example uses a free, public API (JSONPlaceholder.com) to show a list of posts, demonstrating a clean architecture with abstraction layers that make the code highly testable and adaptable.

### Project Overview: Post Manager App

The app will fetch and display a list of posts from a REST API using the MVVM architecture. The key layers and their responsibilities are outlined below.

- Data: holds structured data from the API.
- API: A Retrofit Interface used to make the API calls.
- Repository: It is an abstraction that hides data source details and makes the ViewModel testable and data sources swappable.
- ViewModel: UI logic holder and exposes data to the UI via `LiveData`.
- UI: XML Layouts. It displays data and observes ViewModel.

### One possible Implementation

#### Step 1: Project Configuration (`app/build.gradle.kts`)

Apart from the simple examples we have seen in the class, every time we start developing a serious application we need to check if we need to add any additional dependencies or libraries. It is important to check first if we can make use of existing libraries rather than develop everything ourselves. In this project we need to add the necessary dependencies to our app-level `build.gradle.kts` file. These libraries provide the tools for networking and lifecycle management. 

```kotlin
dependencies {
    
    // Lifecycle & ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")

    // Retrofit for http calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
```
Since our application needs to make the API calls, we need to enable Internet access permission in our `AndroidManifest.xml`.
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
<uses-permission android:name="android.permission.INTERNET" />
...
</manifest>
```

#### Step 2: The Data Model (`data/Post.kt`)

Let's create a data class that represents the structure of the data we expect to receive from the API. The property names should match the keys in the JSON response for automatic parsing by Gson.

We will be using this api https://jsonplaceholder.typicode.com/posts, which returns a list of post with this format:

```json
[
  {
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
  },
  {
    "userId": 1,
    "id": 2,
    "title": "qui est esse",
    "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
  }]
```
So, our data class could like like:
```kotlin
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
```

#### Step 3: The API Interface (`network/JsonPlaceholderApi.kt`)

Now, it is time to define the Retrofit interface. [Retrofit](https://square.github.io/retrofit/) is an HTTP client library to handle API calls. This interface defines the API endpoints using Retrofit annotations. Using a `suspend` function allows Retrofit to handle threading seamlessly.

```kotlin
import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}
```

#### Step 4: The Repository Abstraction (`repository/IPostRepository.kt`)

The repository interface acts as a contract. This is the key to abstraction, allowing us to easily create a fake (Mock) repository for testing or switch to a different data source later without changing the ViewModel.

```kotlin
interface IPostRepository {
    suspend fun getPosts(): List<Post>
}
```

#### Step 5: The Concrete Repository Implementation (`repository/PostRepository.kt`)

This class implements the repository interface using the actual Retrofit service. It handles the network call and error processing, separating these concerns from the ViewModel.

```kotlin
class PostRepository(private val api: JsonPlaceholderApi) : IPostRepository {

    override suspend fun getPosts(): List<Post> {
        // Make the API call
        val response = api.getPosts()
        if (response.isSuccessful) {
            // Return the data if the call is successful
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch posts: ${response.code()}")
        }
    }
}
```

#### Step 6: The ViewModel (`viewmodel/PostViewModel.kt`)

The ViewModel uses Kotlin coroutines (thread) to perform the network request off the main thread. It exposes data to the UI using `LiveData` and manages different UI states (loading, success, error).

```kotlin
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostViewModel(private val repository: IPostRepository) : ViewModel() {

    // The LiveData for the list of posts
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    // The LiveData for error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // The LiveData for loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // load posts
    fun loadPosts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Call the repository on a background thread 
                val postsList = repository.getPosts()
                _posts.value = postsList
                _errorMessage.value = "" 
            } catch (e: Exception) {
                // Update error message if the call fails
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                // Loading is complete 
                _isLoading.value = false
            }
        }
    }
}
```

#### Step 7: The UI Layer

**A. Activity Layout (`res/layout/activity_main.xml`)**
This layout uses a `RecyclerView` to display the list and a `ProgressBar` to show the loading state.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:visibility="gone"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        tools:listitem="@layout/item_post" />

    <TextView
        android:id="@+id/errorTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:visibility="gone"
        android:textColor="@android:color/holo_red_dark"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

**B. Item Layout for RecyclerView (`res/layout/item_post.xml`)**
This layout defines how each individual post item looks in the list.

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="post"
            type="com.example.post_manager_app.data.Post" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:id="@+id/textTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{post.title}"
            android:textSize="18sp"
            android:textStyle="bold"
            tools:text="This is a post title"/>

        <TextView
            android:id="@+id/textBody"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="@{post.body}"
            android:textSize="14sp"
            tools:text="This is the body of the post."/>

    </LinearLayout>
</layout>
```

**C. Adapter for RecyclerView (`ui/PostAdapter.kt`)**
This adapter binds the list of `Post` objects to the `RecyclerView`.

```kotlin
class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var postList = emptyList<Post>()

    inner class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(post: Post) {
            // Assign the post object to the layout variable
            binding.post = post
            // Force the data binding to execute immediately
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
```

**D. Main Activity (`ui/MainActivity.kt`)**
The Activity sets up the UI, creates the dependencies, and observes the ViewModel's LiveData to update the UI accordingly.

```kotlin
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
        viewModel.loadPosts() // Calls the API
    }

    private fun setupRecyclerView() {
        adapter = PostAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        // Initialize the Retrofit API interface
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(JsonPlaceholderApi::class.java)

        // The PostRepository is initialized
        val repository: IPostRepository = PostRepository(api)

        // Initialize the ViewModel using a Factory and passing the Repository
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
    }

    // Observe the different states
    private fun observeViewModel() {
        viewModel.posts.observe(this) { posts ->
            adapter.setData(posts)
        }
        viewModel.errorMessage.observe(this) { error ->
            binding.errorTextView.text = error
            binding.errorTextView.visibility = if (error.isNotEmpty()) View.VISIBLE else View.GONE
        }
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
```

**E. ViewModel Factory (`viewmodel/ViewModelFactory.kt`)**
This factory is required to create a `PostViewModel` with its custom constructor (which requires a `Repository` parameter).

```kotlin
class ViewModelFactory(private val repository: IPostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

### What are the benefits of this architecture?

This architecture provides clear benefits.

#### **1. Testing: It makes the system easy to test**
We can easily test the `PostViewModel` in isolation by creating a **Fake Repository (Mock)** that implements the `IPostRepository` interface. This eliminates the need for a real network connection during tests.

```kotlin
class FakePostRepository : IPostRepository {
    override suspend fun getPosts(): List<Post> {
        // Return a fake list of posts for testing
        return listOf(
            Post(1, 1, "Test Title 1", "Test Body 1"),
            Post(1, 2, "Test Title 2", "Test Body 2")
        )
    }
}

// In our test class (we will see in few lectures), we can then do:
class PostViewModelTest {
    @Test
    fun `loadPosts should update live data`() = runTest {
        // Here we need to create the fake repository and the ViewModel under test
        val fakeRepository = FakePostRepository()
        val viewModel = PostViewModel(fakeRepository)

        // Then we can call the method we want to test
        viewModel.loadPosts()

        // Lastly we can verify the expected outcome
        val posts = viewModel.posts.getOrAwaitValue()
        assertThat(posts.size, `is`(2))
    }
}
```

#### **2. Swapping Components: Flexibility in Action**
The use of interfaces makes your codebase incredibly flexible.

-   **Changing the API Source:** If we need to fetch posts from a different API (e.g., "https://localnews.com/posts"), we only need to:
    1.  Update the base URL and endpoint in a new implementation of `JsonPlaceholderApi`.
    2.  The `PostRepository` and `PostViewModel` require **no changes** because they depend on the abstract interface, not the concrete implementation.

-   **Changing the UI:** The UI (Activity/Fragment) only observes the ViewModel. We can completely change the UI (e.g., from a list to a grid) without touching any of the business logic in the ViewModel, Repository, or Data layers.
