package com.cursosandroidant.DeliveryExamen.entities

import com.google.firebase.database.Exclude

/****
 * Project: Nilo
 * From: com.cursosandroidant.nilo.entities
 * Created by Alain Nicolás Tello on 14/06/21 at 9:52
 * All rights reserved 2021.
 *
 * All my Courses(Only on Udemy):
 * https://www.udemy.com/user/alain-nicolas-tello/
 ***/
data class Message(@get:Exclude var id: String = "",
                   var message: String = "",
                   var sender: String = "",
                   @get:Exclude var myUid: String = ""){

    @Exclude
    fun isSentByMe(): Boolean = sender.equals(myUid)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
