package my.edu.nottingham.shoppingcart.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import my.edu.nottingham.shoppingcart.R
import my.edu.nottingham.shoppingcart.model.Product
import my.edu.nottingham.shoppingcart.viewmodel.SharedViewModel



class CartFragment : Fragment() {

    // Get the same shared ViewModel instance
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var listViewCart: ListView
    private lateinit var tvEmptyCart: TextView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnBackToProducts: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        listViewCart = view.findViewById(R.id.listViewCart)
        tvEmptyCart = view.findViewById(R.id.tvEmptyCart)
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice)
        btnBackToProducts = view.findViewById(R.id.btnBackToProducts)

        setupObservers()
        setupCartList()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observe cart items and update UI
        sharedViewModel.cartItems.observe(viewLifecycleOwner, Observer { cartItems ->
            updateCartUI(cartItems)
        })

        // Observe total price
        sharedViewModel.cartItems.observe(viewLifecycleOwner, Observer {
            tvTotalPrice.text = "$${sharedViewModel.getTotalPrice()}"
        })
    }

    private fun setupCartList() {
        val adapter = object : ArrayAdapter<Product>(
            requireContext(),
            R.layout.item_cart,
            R.id.tvProductName
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.item_cart, parent, false)

                val product = getItem(position)!!
                val tvProductName = view.findViewById<android.widget.TextView>(R.id.tvProductName)
                val tvProductPrice = view.findViewById<android.widget.TextView>(R.id.tvProductPrice)
                val btnRemoveFromCart = view.findViewById<Button>(R.id.btnRemoveFromCart)

                tvProductName.text = product.name
                tvProductPrice.text = "$${product.price}"

                btnRemoveFromCart.setOnClickListener {
                    sharedViewModel.removeFromCart(product)
                }

                return view
            }
        }
        // Set the adapter and observe cart items
        sharedViewModel.cartItems.observe(viewLifecycleOwner, Observer { cartItems ->
            adapter.clear()
            adapter.addAll(cartItems)
        })

        listViewCart.adapter = adapter
    }

    private fun updateCartUI(cartItems: List<Product>) {
        if (cartItems.isEmpty()) {
            tvEmptyCart.visibility = View.VISIBLE
            listViewCart.visibility = View.GONE
        } else {
            tvEmptyCart.visibility = View.GONE
            listViewCart.visibility = View.VISIBLE
        }
    }

    private fun setupClickListeners() {
        btnBackToProducts.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}