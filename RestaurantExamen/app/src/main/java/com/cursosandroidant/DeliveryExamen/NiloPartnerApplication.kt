package com.cursosandroidant.DeliveryExamen

import android.app.Application
import com.cursosandroidant.DeliveryExamen.fcm.VolleyHelper

/****
 * Project: Nilo Partner
 * From: com.cursosandroidant.nilopartner
 * Created by Alain Nicolás Tello on 17/06/21 at 8:51
 * All rights reserved 2021.
 *
 * All my Courses(Only on Udemy):
 * https://www.udemy.com/user/alain-nicolas-tello/
 ***/
class NiloPartnerApplication : Application() {
    companion object{
        lateinit var volleyHelper: VolleyHelper
    }

    override fun onCreate() {
        super.onCreate()

        volleyHelper = VolleyHelper.getInstance(this)
    }
}