package com.cursosandroidant.DeliveryExamen.entities

import com.google.firebase.firestore.Exclude

/****
 * Project: Nilo
 * From: com.cursosandroidant.nilo.entities
 * Created by Alain Nicolás Tello on 10/06/21 at 10:39
 * All rights reserved 2021.
 *
 * All my Courses(Only on Udemy):
 * https://www.udemy.com/user/alain-nicolas-tello/
 ***/
data class Order(@get:Exclude var id: String = "",
                 var clientId: String = "",
                 var products: Map<String, ProductOrder> = hashMapOf(),
                 var totalPrice: Double = 0.0,
                 var status: Int = 0){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
