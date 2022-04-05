package com.example.icecreamsundaemaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {
    var orders = ArrayList<Order>()
    var ordersString = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val intent = intent

        orders = intent.getSerializableExtra("ORDERS") as ArrayList<Order>

        for (order in orders){
            ordersString.add(order.toString())
        }

        //adapt strings to a format the listview wants
        val adapter = ArrayAdapter<String>(this@OrderActivity, android.R.layout.simple_list_item_1, ordersString)

        listView.adapter = adapter
    }
}