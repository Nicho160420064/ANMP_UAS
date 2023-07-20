package com.ubaya.uas_anmp.view

import android.view.View
import com.ubaya.uas_anmp.model.Account

interface ButtonAddNewReservationClickListener{
    fun onButtonAddNewReservationClick(v: View)
}

interface ButtonToAddReservationClickListener{
    fun onButtonToAddReservationClick(v: View)
}

interface ButtonDetailTenantClickListener{
    fun onButtonDetailTenantClick(v: View)
}

interface ButtonDetailPromoClickListener{
    fun onButtonDetailPromoClick(v: View)
}

interface ButtonWriteReviewClickListener{
    fun onButtonWriteReviewClick(v: View)
}

interface ButtonAddReviewClickListener{
    fun onButtonAddReviewClick(v: View)
}

interface DateTimeClickListener{
    fun onDateClick(v: View)
    fun onTimeClick(v: View)
}

interface EditAccountClickListener{
    fun onButoonEditAccountClickListener(v: View, obj: Account)
}
