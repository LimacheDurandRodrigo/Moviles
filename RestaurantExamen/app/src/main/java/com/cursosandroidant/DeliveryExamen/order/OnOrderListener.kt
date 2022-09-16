package com.cursosandroidant.DeliveryExamen.order

import com.cursosandroidant.DeliveryExamen.entities.Order

/****
 * Project: Nilo
 * From: com.cursosandroidant.nilo.order
 * Created by Alain Nicolás Tello on 10/06/21 at 10:55
 * All rights reserved 2021.
 *
 * All my Courses(Only on Udemy):
 * https://www.udemy.com/user/alain-nicolas-tello/
 ***/
interface OnOrderListener {
    fun onStartChat(order: Order)
    fun onStatusChange(order: Order)
}