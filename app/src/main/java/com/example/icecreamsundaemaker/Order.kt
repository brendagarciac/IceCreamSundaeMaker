package com.example.icecreamsundaemaker

import java.io.Serializable

class Order (var date:String, var flavor:String, var size:String, var cost:String) : Serializable {
    override fun toString(): String {
        return "Date: $date \nFlavor: $flavor \nSize: $size \nCost: $cost"
    }

}