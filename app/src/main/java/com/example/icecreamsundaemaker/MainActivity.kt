package com.example.icecreamsundaemaker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {

    lateinit var fmt: NumberFormat

    //dropdowns
    var spinnerFlavors = ArrayList<String>()
    var spinnerSizes = ArrayList<String>()

    //array list of order items
    var ordersList = ArrayList<Order>()

    var sizeCost = 2.99
    var toppingsCost = 0.0
    var fudgeCost = 0.15

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fmt = NumberFormat.getCurrencyInstance() //format the currency according to whatever language is on the phone


        //spinners are to show the dropdown with the required options
        spinnerFlavors = arrayListOf("Vanilla", "Chocolate", "Strawberry")
        var spinnerFlavorsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerFlavors)
        flavor_spinner.adapter = spinnerFlavorsAdapter

        spinnerSizes = arrayListOf("Small", "Medium", "Large")
        var spinnerSizesAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerSizes)
        size_spinner.adapter = spinnerSizesAdapter

        //changes to seekbar are to be reflected in the progress textview
        fudge_seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progress_textView.text = "$p1 oz"
                fudgeCost = when (p1) { //update cost of fudge
                    0 -> {
                        0.0
                    }
                    1 -> {
                        0.15
                    }
                    2 -> {
                        0.25
                    }
                    else -> {
                        0.30
                    }
                }
                updateCost()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        size_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                sizeCost = when (p2) {
                    0 -> { //small
                        2.99
                    }
                    1 -> { //medium
                        3.99
                    }
                    else -> { //large
                        4.99
                    }
                }
                updateCost()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        works_button.setOnClickListener {
            handleAll()
        }

        reset_button.setOnClickListener {
            handleReset()
        }

        order_button.setOnClickListener {
            Toast.makeText(this@MainActivity, "Your sundae is on the way. Enjoy!", Toast.LENGTH_LONG).show()

            //instantiate an order item & add it to the array list when an order is made
            val currentDate = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formattedDate = currentDate.format(formatter)

            var flavor:String = flavor_spinner.selectedItem.toString()

            var size:String = size_spinner.selectedItem.toString()

            var cost:String = price_textView.text.toString()

            ordersList.add(Order(formattedDate, flavor, size, cost))
            handleReset() //reset all values to default after order has beeen made
        }
    }

    //add up to cost as toppings are selected and subtract as they are deselected
    fun handleCheckBox(view: View){
        when(view.id){
            R.id.peanuts_checkBox ->{
                if(peanuts_checkBox.isChecked)
                    toppingsCost += 0.15
                else
                    toppingsCost -= 0.15
            } R.id.mm_checkBox ->{
                if(mm_checkBox.isChecked)
                    toppingsCost += 0.25
                else
                    toppingsCost -= 0.25
            } R.id.almonds_checkBox ->{
                if(almonds_checkBox.isChecked)
                    toppingsCost += 0.15
                else
                    toppingsCost -= 0.15
            } R.id.brownies_checkBox ->{
                if(brownies_checkBox.isChecked)
                    toppingsCost += 0.20
                else
                    toppingsCost -= 0.20
            } R.id.strawberries_checkBox ->{
                if(strawberries_checkBox.isChecked)
                    toppingsCost += 0.20
                else
                    toppingsCost -= 0.20
            } R.id.oreos_checkBox ->{
                if(oreos_checkBox.isChecked)
                    toppingsCost += 0.20
                else
                    toppingsCost -= 0.20
            } R.id.gummies_checkBox ->{
                if(gummies_checkBox.isChecked)
                    toppingsCost += 0.20
                else
                    toppingsCost -= 0.20
            } R.id.marshmallows_checkBox ->{
                if(marshmallows_checkBox.isChecked)
                    toppingsCost += 0.15
                else
                    toppingsCost -= 0.15
            }
        }
        updateCost()
    }

    private fun handleAll(){
        //large sundae with everything on it
        size_spinner.setSelection(2)
        fudge_seekBar.progress = 3
        peanuts_checkBox.isChecked = true
        mm_checkBox.isChecked = true
        almonds_checkBox.isChecked = true
        brownies_checkBox.isChecked = true
        strawberries_checkBox.isChecked = true
        oreos_checkBox.isChecked = true
        gummies_checkBox.isChecked = true
        marshmallows_checkBox.isChecked = true
        sizeCost = 4.99
        toppingsCost = 1.5
        fudgeCost = 0.30
        updateCost()
    }

    private fun handleReset(){
        //set to default: small size, vanilla ice cream, 1 oz of hot fudge, and no toppings.
        flavor_spinner.setSelection(0)
        size_spinner.setSelection(0)
        fudge_seekBar.progress = 1
        peanuts_checkBox.isChecked = false
        mm_checkBox.isChecked = false
        almonds_checkBox.isChecked = false
        brownies_checkBox.isChecked = false
        strawberries_checkBox.isChecked = false
        oreos_checkBox.isChecked = false
        gummies_checkBox.isChecked = false
        marshmallows_checkBox.isChecked = false
        sizeCost = 2.99
        toppingsCost = 0.0
        fudgeCost = 0.15
        updateCost()
    }

    fun updateCost() {
        var price = sizeCost + toppingsCost + fudgeCost
        price_textView.text = fmt.format(price)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_about_id){ //TAKE TO ABOUT ACTIVITY
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        } else{
            val intent = Intent(this@MainActivity, OrderActivity::class.java)
            intent.putExtra("ORDERS", ordersList)
            startActivity(intent)
        }
        return true
    }
}