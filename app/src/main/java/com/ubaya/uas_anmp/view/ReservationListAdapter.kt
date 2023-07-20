package com.ubaya.uas_anmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.ReservationListItemBinding
import com.ubaya.uas_anmp.model.Reservation

class ReservationListAdapter (val reservationList: ArrayList<Reservation>) : RecyclerView.Adapter<ReservationListAdapter.ReservationViewHolder>(){
    class ReservationViewHolder(var view: ReservationListItemBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ReservationListItemBinding>(inflater,
            R.layout.reservation_list_item, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.view.reservation = reservationList[position]
    }

    override fun getItemCount()=reservationList.size

    fun updateReservationList(newReservationList: List<Reservation>){
        reservationList.clear()
        reservationList.addAll(newReservationList)
        notifyDataSetChanged()
    }
}