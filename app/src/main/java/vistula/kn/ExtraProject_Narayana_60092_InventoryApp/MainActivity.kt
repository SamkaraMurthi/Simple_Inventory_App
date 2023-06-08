package vistula.kn.ExtraProject_Narayana_60092_InventoryApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private val itemList: ArrayList<String> = ArrayList()
    private val priceList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeArrays()

        val ibGameKn : ImageButton = findViewById(R.id.ibGameKn)
        val btnInsertKn : Button = findViewById(R.id.btnInsertKn)
        val btnSearchKn : Button = findViewById(R.id.btnSearchKn)
        val btnTrcKn : Button = findViewById(R.id.btnTrcKn)


        ibGameKn.setOnClickListener {

        }

        btnInsertKn.setOnClickListener {
            Intent(this, InputItemKadek::class.java).also {
                startActivity(it)
            }
        }
        btnSearchKn.setOnClickListener {
            val intent = Intent(this, ShowDataKadek::class.java)
            intent.putStringArrayListExtra("itemList", itemList)
            intent.putStringArrayListExtra("priceList", priceList)
            startActivity(intent)
        }
        btnTrcKn.setOnClickListener {
            val intent = Intent(this, CartItemActivity::class.java)
            startActivity(intent)
        }

    }
    private fun initializeArrays() {
        try {
            val inputStream = openFileInput("item_list.txt")
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                val parts = line.split(",")
                if (parts.size >= 2) {
                    val item = parts[0]
                    val price = parts[1]
                    itemList.add(item)
                    priceList.add(price)
                }
                line = bufferedReader.readLine()
            }
            bufferedReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
