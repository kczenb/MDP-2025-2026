# Shopping Cart App with Jetpack Compose

This tutorial recreates the same shopping cart application using Jetpack Compose, demonstrating modern Android UI development with a declarative approach.


## Key Compose Concepts

### State Hoisting
In Compose, we lift state up to the ViewModel rather than managing it in UI components, making our UI stateless and more testable.

### Reactive Programming
Compose automatically recomposes when state changes, creating a reactive UI that always reflects the current state.

---

## Step 1: Project Setup and Dependencies

### Dependencies

We need to add the same dependecnies as before

**`app/build.gradle.kts`**:
```kotlin
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")
}
```
---

## Step 2: Data Models (Same as Before)

### Product Data Class

**`model/Product.kt`**:
```kotlin
package com.example.shoppingcartcompose.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int = android.R.drawable.ic_menu_report_image
)
```

---

## Step 3: Shared ViewModel with Compose Support

### CartViewModel for Compose

- We are using `StateFlow` instead of `LiveData` for better Compose integration. `StateFlow` provides a stream of updates that Compose can collect.
- `asStateFlow()` makes mutable flow read-only for UI safety
- UI state is centralised in a data class for better management

**`viewmodel/CartViewModel.kt`**:
```kotlin
package my.edu.nottingham.shoppingcart_compose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import my.edu.nottingham.shoppingcart_compose.model.Product

class CartViewModel : ViewModel() {

    // State for products - using StateFlow for Compose
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    // State for cart items
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()

    // State for UI navigation
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = listOf(
            Product(1, "Laptop", 999.99),
            Product(2, "Smartphone", 699.99),
            Product(3, "Headphones", 149.99),
            Product(4, "Tablet", 399.99),
            Product(5, "Smartwatch", 199.99)
        )
    }

    // Add product to cart
    fun addToCart(product: Product) {
        _cartItems.update { currentCart ->
            currentCart + product
        }
        updateCartState()
    }

    // Remove product from cart
    fun removeFromCart(product: Product) {
        _cartItems.update { currentCart ->
            currentCart.filter { it.id != product.id }
        }
        updateCartState()
    }

    // Clear all items from cart
    fun clearCart() {
        _cartItems.update { emptyList() }
        updateCartState()
    }

    // Get total cart price
    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.price }
    }

    // Get cart items count
    fun getCartItemCount(): Int {
        return _cartItems.value.size
    }

    // Navigate to cart screen
    fun navigateToCart() {
        _uiState.update { it.copy(shouldNavigateToCart = true) }
    }

    // Reset navigation state
    fun onNavigationComplete() {
        _uiState.update { it.copy(shouldNavigateToCart = false) }
    }

    private fun updateCartState() {
        _uiState.update { currentState ->
            currentState.copy(
                cartItemCount = _cartItems.value.size,
                totalPrice = getTotalPrice()
            )
        }
    }
}

// UI State data class for managing screen state
data class CartUiState(
    val cartItemCount: Int = 0,
    val totalPrice: Double = 0.0,
    val shouldNavigateToCart: Boolean = false
)
```

---

## Step 4: Composable Screens

### Product List Screen


**Interesting concepts used here**:
- **Scaffold**: Provides basic Material Design layout structure
- **State Collection**: Using `viewModel().value` to get current state
- **LaunchedEffect**: For handling one-time events like navigation
- **LazyColumn**: Efficiently renders only visible items
- **Card**: Material Design container for product items

**Screens**
`ProductListScreen` is a list of products composable.


**`ui/screens/ProductListScreen.kt`**:
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onNavigateToCart: () -> Unit,
    viewModel: CartViewModel
) {
    // Properly collect StateFlow using collectAsState()
    val products by viewModel.products.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    // Handle navigation events
    LaunchedEffect(uiState.shouldNavigateToCart) {
        if (uiState.shouldNavigateToCart) {
            onNavigateToCart()
            viewModel.onNavigationComplete()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Our Products") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            CartFloatingActionButton(
                itemCount = uiState.cartItemCount,
                onClick = { viewModel.navigateToCart() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Product list
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onAddToCart = { viewModel.addToCart(product) }
                    )
                }
            }
        }
    }
}

```
`ProductItem` composable representing one single product item. 

```kotlin

@Composable
fun ProductItem(
    product: Product,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Optional: Show product details */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(onClick = onAddToCart) {
                Text("Add to Cart")
            }
        }
    }
}

```

`CartFloatingActionButton` composable button.

```kotlin
@Composable
fun CartFloatingActionButton(
    itemCount: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "View Cart"
        )
        Text(
            text = " $itemCount",
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
```



### Cart Screen

- **Conditional UI**: Showing empty state vs cart items
- **Key in LazyColumn**: Ensuring proper item identification for animations
- **Bottom Bar**: Fixed position for total and actions
- **State-Driven UI**: Automatically updates when cart changes

**`ui/screens/CartScreen.kt`**:
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBack: () -> Unit,
    viewModel: CartViewModel
) {
    // Properly collect StateFlow using collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            CartBottomBar(
                totalPrice = uiState.totalPrice,
                onClearCart = { viewModel.clearCart() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Now we can safely check cartItems.isEmpty()
            if (cartItems.isEmpty()) {
                EmptyCartMessage()
            } else {
                CartItemList(
                    cartItems = cartItems,
                    onRemoveItem = { product -> viewModel.removeFromCart(product) }
                )
            }
        }
    }
}
```

`CartItemList` cart Item list composable

```kotlin


@Composable
fun CartItemList(
    cartItems: List<Product>,
    onRemoveItem: (Product) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cartItems, key = { it.id }) { product ->
            CartItem(
                product = product,
                onRemove = { onRemoveItem(product) }
            )
        }
    }
}

```
`CartItem` cart item composable.


```kotlin

@Composable
fun CartItem(
    product: Product,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(
                onClick = onRemove,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text("Remove")
            }
        }
    }
}
```

`EmptyCartMessage` message composable.

```kotlin

@Composable
fun EmptyCartMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}
```

`CartBottomBar` bottom bar composable.

```kotlin

@Composable
fun CartBottomBar(
    totalPrice: Double,
    onClearCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Total price row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$${"%.2f".format(totalPrice)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Buttons row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onClearCart,
                modifier = Modifier.weight(1f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text("Clear Cart")
            }
        }
    }
}
```

---

## Step 5: Navigation Setup

### Navigation Graph

- **Type-safe**: Using sealed class for routes
- **rememberNavController**: Handles configuration changes automatically

**`navigation/NavGraph.kt`**:
```kotlin
@Composable
fun ShoppingCartNavGraph() {
    val navController = rememberNavController()

    // a shared ViewModel that can survive navigation changes
    // This ViewModel instance will be shared across both screens
    val sharedViewModel: CartViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.ProductList.route
    ) {
        composable(route = Screen.ProductList.route) {
            ProductListScreen(
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                },
                viewModel = sharedViewModel  // Pass the shared instance
            )
        }
        composable(route = Screen.Cart.route) {
            CartScreen(
                onBack = { navController.popBackStack() },
                viewModel = sharedViewModel  // Pass the same shared instance
            )
        }
    }
}

sealed class Screen(val route: String) {
    object ProductList : Screen("product_list")
    object Cart : Screen("cart")
}
```


---

## Step 6: Main Activity with Compose

### Main Activity

**`MainActivity.kt`**:
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingcartcomposeTheme {
                ShoppingCartNavGraph()
            }
        }
    }
}
```

## The main Advantages


### 1. **Automatic State Management**
```kotlin
// Compose automatically recomposes when state changes
val cartItems = viewModel.cartItems.value
// UI updates automatically when cartItems changes
```

### 2. **Composition Over Inheritance**
```kotlin
// Build complex UIs from simple components
Scaffold(
    topBar = { /* ... */ },
    content = { /* ... */ },
    bottomBar = { /* ... */ }
)
```

### 3. **No XML Layouts**
- All UI defined in Kotlin
- Type-safe and null-safe


### 4. **Simpler Navigation**
- No fragment transactions
- Type-safe routes

