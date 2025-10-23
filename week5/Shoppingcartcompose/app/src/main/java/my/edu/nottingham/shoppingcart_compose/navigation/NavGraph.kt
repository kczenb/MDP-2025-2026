package my.edu.nottingham.shoppingcart_compose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import my.edu.nottingham.shoppingcart_compose.screens.CartScreen
import my.edu.nottingham.shoppingcart_compose.screens.ProductListScreen
import my.edu.nottingham.shoppingcart_compose.viewmodel.CartViewModel

@Composable
fun ShoppingCartNavGraph() {
    val navController = rememberNavController()

    // Create a shared ViewModel that survives navigation changes
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