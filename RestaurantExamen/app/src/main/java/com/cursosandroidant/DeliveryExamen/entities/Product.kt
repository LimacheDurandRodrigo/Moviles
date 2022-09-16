package com.cursosandroidant.DeliveryExamen.entities

import com.google.firebase.firestore.Exclude

/****
 * Project: Nilo Partner
 * From: com.cursosandroidant.nilopartner
 * Created by Alain Nicolás Tello on 03/06/21 at 12:18
 * All rights reserved 2021.
 *
 * All my Courses(Only on Udemy):
 * https://www.udemy.com/user/alain-nicolas-tello/
 ***/
data class Product(@get:Exclude var id: String? = null,
                   var name:String? = null,
                   var description: String? = null,
                   var imgUrl: String? = null,
                   var quantity: Int = 0,
                   var price: Double = 0.0,
                   var sellerId: String = ""){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
