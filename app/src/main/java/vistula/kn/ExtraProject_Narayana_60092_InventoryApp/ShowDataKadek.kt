package vistula.kn.ExtraProject_Narayana_60092_InventoryApp

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException

class ShowDataKadek : AppCompatActivity() {
    private val itemList: ArrayList<String> = ArrayList()
    private val itemTotal: ArrayList<String> = ArrayList()
    private val priceList: ArrayList<String> = ArrayList()
    private val filteredItemList: ArrayList<String> = ArrayList()
    private val filteredPriceList: ArrayList<String> = ArrayList()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data_kadek)

        val btnFindKn: Button = findViewById(R.id.btnFindKn)
        val etSearchKn: EditText = findViewById(R.id.etSearchKn)
        val btnRefreshKn: Button = findViewById(R.id.btnRefreshKn)

        btnRefreshKn.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            finish()
            startActivity(intent)
        }

        itemList.addAll(intent.getStringArrayListExtra("itemList") ?: ArrayList())
        priceList.addAll(intent.getStringArrayListExtra("priceList") ?: ArrayList())

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = DataItemAdapter(applicationContext, itemList, priceList)
        recyclerView.adapter = adapter

        btnFindKn.setOnClickListener {
            val query = etSearchKn.text.toString()
            filterItems(query)
        }
    }

    private fun filterItems(query: String) {
        filteredItemList.clear()
        filteredPriceList.clear()

        for (i in itemList.indices) {
            val item = itemList[i]
            val price = priceList[i]

            if (item.contains(query, ignoreCase = true)) {
                filteredItemList.add(item)
                filteredPriceList.add(price)
            }
        }

        val adapter = DataItemAdapter(applicationContext, filteredItemList, filteredPriceList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    inner class DataItemAdapter(
        private val context: Context,
        private val itemList: List<String>,
        private val priceList: List<String>
    ) : RecyclerView.Adapter<DataItemAdapter.DataItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItemViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
            return DataItemViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: DataItemViewHolder, position: Int) {
            val item = itemList[position]
            val price = priceList[position]
            holder.bind(item, price)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        inner class DataItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvItemPrice: TextView = itemView.findViewById(R.id.tvItemPrice)

            init {
                itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = itemList[position]
                        val price = priceList[position]
                        addToCart(item, price)
                    }
                }
            }

            fun bind(item: String, price: String) {
                val itemPriceText = "$item, $price PLN"
                tvItemPrice.text = itemPriceText
            }

            private fun addToCart(item: String, price: String) {
                val cartData = "$item, $price PLN"


                try {
                    val file = File(context.filesDir, "cartNara.txt")
                    file.appendText(cartData + "\n")
                    Toast.makeText(context, "Item added to cart.", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Failed to add item to cart.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}