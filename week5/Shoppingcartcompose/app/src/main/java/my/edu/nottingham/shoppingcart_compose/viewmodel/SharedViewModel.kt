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