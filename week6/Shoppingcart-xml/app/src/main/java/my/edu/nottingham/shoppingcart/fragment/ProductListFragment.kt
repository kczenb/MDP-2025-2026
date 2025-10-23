package my.edu.nottingham.shoppingcart.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import my.edu.nottingham.shoppingcart.R
import my.edu.nottingham.shoppingcart.model.Product
import my.edu.nottingham.shoppingcart.viewmodel.SharedViewModel
import kotlin.getValue


class ProductListFragment : Fragment() {

    // Get the shared ViewModel using activityViewModels
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var listViewProducts: ListView
    private lateinit var btnViewCart: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        listViewProducts = view.findViewById(R.id.listViewProducts)
        btnViewCart = view.findViewById(R.id.btnViewCart)

        setupObservers()
        setupProductList()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observe cart item count to update the button text
        sharedViewModel.cartItems.observe(viewLifecycleOwner, Observer { cartItems ->
            btnViewCart.text = "View Cart (${cartItems.size})"
        })

        // Observe navigation events
        sharedViewModel.navigateToCart.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate) {
                findNavController().navigate(R.id.action_productListFragment_to_cartFragment)
                sharedViewModel.onNavigationComplete()
            }
        })
    }

    private fun setupProductList() {
        // Create a custom adapter for the product list
        val adapter = object : ArrayAdapter<Product>(
            requireContext(),
            R.layout.item_product,
            R.id.tvProductName
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.item_product, parent, false)

                val product = getItem(position)!!
                val tvProductName = view.findViewById<android.widget.TextView>(R.id.tvProductName)
                val tvProductPrice = view.findViewById<android.widget.TextView>(R.id.tvProductPrice)
                val btnAddToCart = view.findViewById<Button>(R.id.btnAddToCart)

                tvProductName.text = product.name
                tvProductPrice.text = "$${product.price}"

                btnAddToCart.setOnClickListener {
                    sharedViewModel.addToCart(product)
                }

                return view
            }
        }
        // Set the adapter and populate with products
        sharedViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            adapter.clear()
            adapter.addAll(products)
        })

        listViewProducts.adapter = adapter
    }

    private fun setupClickListeners() {
        btnViewCart.setOnClickListener {
            sharedViewModel.navigateToCart()
        }
    }

}