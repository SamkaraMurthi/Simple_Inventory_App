package vistula.kn.ExtraProject_Narayana_60092_InventoryApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class CartItemActivity : AppCompatActivity() {
    private lateinit var cartItemsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_item)

        cartItemsTextView = findViewById(R.id.cartItemsTextView)

        // Load and display cart items
        val cartItems = loadCartItems()
        displayCartItems(cartItems)
    }

    private fun loadCartItems(): List<String> {
        val cartItems: MutableList<String> = mutableListOf()

        try {
            val file = File(filesDir, "cartNara.txt")
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)

            var line: String? = bufferedReader.readLine()
            while (line != null) {
                cartItems.add(line)
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to load cart items.", Toast.LENGTH_SHORT).show()
        }

        return cartItems
    }

    private fun displayCartItems(cartItems: List<String>) {
        val formattedCartItems = cartItems.joinToString("\n")
        cartItemsTextView.text = formattedCartItems
    }

}