package vistula.kn.ExtraProject_Narayana_60092_InventoryApp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.*
import java.util.ArrayList

class InputItemKadek : AppCompatActivity() {
    private val itemList: ArrayList<String> = ArrayList()
    private val priceList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_item_kadek)

        val etItemInputKn: EditText = findViewById(R.id.etItemInputKn)
        val etPriceKn: EditText = findViewById(R.id.etPriceKn)
        val btnSaveKn: Button = findViewById(R.id.btnSaveKn)
        val btnBack1Kn: Button = findViewById(R.id.btnBack1Kn)
        val tvSaventcKn: TextView = findViewById(R.id.tvSaventcKn)
        val tvTotalDataKn: TextView = findViewById(R.id.tvTotalDataKn)

        loadListFromFile() // Load the data from the file

        val akhirKn = "Total Data is ${itemList.size}"
        tvTotalDataKn.text = akhirKn

        btnSaveKn.setOnClickListener {
            val itemInput = etItemInputKn.text.toString()
            val priceInput = etPriceKn.text.toString()

            if (itemInput.isEmpty() || priceInput.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
                tvSaventcKn.text = "Don't Input blank Field"
            } else {
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
                itemList.add(itemInput)
                priceList.add(priceInput)
                etItemInputKn.text.clear()
                tvSaventcKn.text = "Your Data Saved, Thank You"
                etPriceKn.text.clear()
                tvTotalDataKn.text = "Total Data is ${itemList.size}"
                saveListToFile()

            }
        }

        btnBack1Kn.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            finish()
            startActivity(intent)
        }
    }

    private fun saveListToFile() {
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput("item_list.txt", Context.MODE_PRIVATE)
            for (i in itemList.indices) {
                val item = itemList[i]
                val price = priceList[i]
                val line = "$item,$price"
                fileOutputStream.write(line.toByteArray())
                fileOutputStream.write('\n'.toInt())
            }
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadListFromFile() {
        val fileInputStream: FileInputStream
        try {
            fileInputStream = openFileInput("item_list.txt")
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                val parts = line.split(",").toTypedArray()
                if (parts.size == 2) {
                    itemList.add(parts[0])
                    priceList.add(parts[1])
                }
                line = bufferedReader.readLine()
            }
            fileInputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}