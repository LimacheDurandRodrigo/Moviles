package com.cursosandroidant.DeliveryExamen.product

import com.cursosandroidant.DeliveryExamen.entities.Product

/****
 * Project: Nilo Partner
 * From: com.cursosandroidant.nilopartner
 * Created by Alain Nicolás Tello on 03/06/21 at 12:28
 * All rights reserved 2021.
 *
 * All my Courses(Only on Udemy):
 * https://www.udemy.com/user/alain-nicolas-tello/
 ***/
interface OnProductListener {
    fun onClick(product: Product)
    fun onLongClick(product: Product)
}