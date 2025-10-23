package my.edu.nottingham.shoppingcart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.edu.nottingham.shoppingcart.model.Product
import kotlin.collections.filter

class SharedViewModel : ViewModel() {

    // LiveData for product list
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    // LiveData for cart items
    private val _cartItems = MutableLiveData<List<Product>>()
    val cartItems: LiveData<List<Product>> get() = _cartItems

    // LiveData for navigation
    private val _navigateToCart = MutableLiveData<Boolean>()
    val navigateToCart: LiveData<Boolean> get() = _navigateToCart

    init {
        // Fake data to initialise the list
        _products.value = listOf(
            Product(1, "Laptop", 999.99),
            Product(2, "Smartphone", 699.99),
            Product(3, "Headphones", 149.99),
            Product(4, "Tablet", 399.99),
            Product(5, "Smartwatch", 199.99)
        )
        _cartItems.value = emptyList()
    }

    // Add product to cart
    fun addToCart(product: Product) {
        val currentCart = _cartItems.value ?: emptyList()
        _cartItems.value = currentCart + product
    }

    // Remove product from cart
    fun removeFromCart(product: Product) {
        val currentCart = _cartItems.value ?: emptyList()
        _cartItems.value = currentCart.filter { it.id != product.id }
    }

    // Get total cart price
    fun getTotalPrice(): Double {
        return _cartItems.value?.sumOf { it.price } ?: 0.0
    }

    // Get cart items count
    fun getCartItemCount(): Int {
        return _cartItems.value?.size ?: 0
    }

    // Navigate to cart
    fun navigateToCart() {
        _navigateToCart.value = true
    }

    // Reset navigation
    fun onNavigationComplete() {
        _navigateToCart.value = false
    }
}