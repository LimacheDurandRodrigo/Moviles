package com.cursosandroidant.DeliveryExamen.chat

import com.cursosandroidant.DeliveryExamen.entities.Message

/****
 * Project: Nilo
 * From: com.cursosandroidant.nilo.chat
 * Created by Alain Nicolás Tello on 14/06/21 at 9:58
 * All rights reserved 2021.
 *
 * All my Courses(Only on Udemy):
 * https://www.udemy.com/user/alain-nicolas-tello/
 ***/
interface OnChatListener {
    fun deleteMessage(message: Message)
}