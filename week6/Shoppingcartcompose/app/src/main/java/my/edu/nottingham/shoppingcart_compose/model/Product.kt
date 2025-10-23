package my.edu.nottingham.shoppingcart_compose.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int = android.R.drawable.ic_menu_report_image
)